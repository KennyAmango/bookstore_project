package com.example.bookstore_project.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.bookstore_project.service.ifs.BookStoreService;
import com.example.bookstore_project.vo.BookRankRes;
import com.example.bookstore_project.vo.BookStoreRes;
import com.example.bookstore_project.vo.BookStoreListRes;
import com.example.bookstore_project.vo.orderBookReq;
import com.example.bookstore_project.constans.BookStoreRtnCode;
import com.example.bookstore_project.entity.BookStore;
import com.example.bookstore_project.repository.BookStoreDao;

@Service
public class BookStoreServiceImpl implements BookStoreService {

	@Autowired
	private BookStoreDao bookStoredao;

	//
	private BookStoreListRes shopGetBook(BookStore books) {

		BookStoreRes book = new BookStoreRes();
		BookStoreListRes bookInfo = new BookStoreListRes();

		book.setIsbn(books.getIsbn());
		book.setName(books.getName());
		book.setWriter(books.getWriter());
		book.setPrice(books.getPrice());
		book.setStorage(books.getStorage());
		book.setSales(books.getSales());

		return bookInfo;
	}

	private BookStoreListRes cusGetBook(BookStore books) {

		BookStoreRes book = new BookStoreRes();
		BookStoreListRes bookInfo = new BookStoreListRes();

		book.setIsbn(books.getIsbn());
		book.setName(books.getName());
		book.setWriter(books.getWriter());
		book.setPrice(books.getPrice());
		return bookInfo;
	}

	private BookStoreListRes shopGetBooks(List<BookStore> books) {

		BookStoreListRes booksinfo = new BookStoreListRes();
		List<BookStoreRes> list = new ArrayList<>();

		for (BookStore book : books) {

			BookStoreRes bookInfo = new BookStoreRes();

			bookInfo.setIsbn(book.getIsbn());
			bookInfo.setName(book.getName());
			bookInfo.setWriter(book.getWriter());
			bookInfo.setPrice(book.getPrice());
			bookInfo.setStorage(book.getStorage());
			bookInfo.setSales(book.getSales());

			list.add(bookInfo);
		}
		booksinfo.setBooksInfo(list);
		return booksinfo;
	}

	private BookStoreListRes cusGetBooks(List<BookStore> books) {
		List<BookStoreRes> list = new ArrayList<>();
		BookStoreListRes booksinfo = new BookStoreListRes();

		for (BookStore book : books) {

			BookStoreRes bookInfo = new BookStoreRes();

			bookInfo.setIsbn(book.getIsbn());
			bookInfo.setName(book.getName());
			bookInfo.setWriter(book.getWriter());
			bookInfo.setPrice(book.getPrice());

			list.add(bookInfo);
		}
		booksinfo.setBooksInfo(list);
		return booksinfo;
	}

	@Override
	public BookStoreRes create(String isbn, String name, String category, String writer, Integer price,
			Integer storage) {
		// 如果在資料庫沒有找到相對應的isbn,就創建新的BookStore物件,將輸入值設定在這個新物件上
		if (!bookStoredao.existsById(isbn)) {

			BookStore book = new BookStore(isbn, name, category, writer, price, storage);
			// 存進資料庫後,回傳res
			bookStoredao.save(book);

			BookStoreRes res = new BookStoreRes(book, BookStoreRtnCode.CREATE_SUCCESSFUL.getMessage());
			return res;
		}
		// 若有找到則回傳null給Controller判斷
		return null;
	}

	@Override
	public BookStoreRes updateByIsbn(String isbn, String name, String category, String writer, Integer price,
			Integer storage) {

		// 判斷輸入的isbn是否有值,若沒有值則回傳isbn不能為空
		if (!StringUtils.hasText(isbn)) {
			return new BookStoreRes(BookStoreRtnCode.ISBN_REQUIRED.getMessage());
		}

		// 判斷輸入的isbn在資料庫是否存在,若沒有則回傳無此isbn
		Optional<BookStore> bookInfo = bookStoredao.findById(isbn);
		if (!bookInfo.isPresent()) {
			return new BookStoreRes(BookStoreRtnCode.NO_FOUND_ISBN.getMessage());
		}

		// 從資料庫找出符合輸入isbn的書籍資料,並用book接起來
		BookStore book = bookInfo.get();

		// 判斷所有輸入的欄位,若那個欄位有輸入要更改的值,則讓更改的值存到book裡
		// 若那個欄位沒有輸入值,則不做更動,保持原樣
		if (StringUtils.hasText(name)) {
			book.setName(name);
		}
		if (StringUtils.hasText(category)) {
			book.setCategory(category);
		}
		if (StringUtils.hasText(writer)) {
			book.setWriter(writer);
		}
		if (price != null) {
			book.setPrice(price);
		}
		if (storage != null) {
			book.setStorage(storage);
		}

		// 將更動後的book存回資料庫裡
		bookStoredao.save(book);

		// 回傳改動後的書籍資料以及成功訊息
		return new BookStoreRes(book, BookStoreRtnCode.UPDATE_SUCCESSFUL.getMessage());
	}

	@Override
	public BookStoreRes deleteByIsbn(String isbn) {

		// 判斷輸入的isbn是否有值
		if (!StringUtils.hasText(isbn)) {
			return new BookStoreRes(BookStoreRtnCode.ISBN_REQUIRED.getMessage());
		}

		// 判斷輸入的isbn存不存在於資料庫
		if (!bookStoredao.existsById(isbn)) {
			return new BookStoreRes(BookStoreRtnCode.ISBN_REQUIRED.getMessage() + isbn);
		}

		// 刪除資料庫裡符合輸入isbn的書籍
		bookStoredao.deleteById(isbn);

		// 回傳刪除成功的訊息
		return new BookStoreRes("ISBN:" + isbn + BookStoreRtnCode.Delete_SUCCESSFUL.getMessage());
	}

	@Override
	public BookStoreListRes findByCategory(String category) {

		// 判斷輸入的書籍類別是否有值,沒有則回傳錯誤訊息
		if (!StringUtils.hasText(category)) {
			return new BookStoreListRes(BookStoreRtnCode.CATEGORY_REQUIRED.getMessage());
		}

		// 將輸入的書籍類別用逗號做分割,切格完後個別存入categorylist裡
		String[] categorylist = category.split(",");
		
		Set<String> categorySet = new HashSet<>();
		// 將存在categorylist裡的書籍類別foreach出來存進Set陣列裡,去除重複
		for (String item : categorylist) {
			categorySet.add(item.trim());
		}

		// 先把資料庫所有書找出來
		List<BookStore> books = bookStoredao.findAll();

		Set<BookStore> bookSet = new HashSet<>();

		// 將輸入的分類與資料庫中的所有書進行比對,把資料庫含有item分類的書存入bookSet,去除重複的書籍
		for (BookStore book : books) {
			for (String item : categorySet) {
				// 用java內建的功能判斷有沒有類似的字串
				if (book.getCategory().contains(item)) {
					bookSet.add(book);
					
				}
			}
		}

		// 將去除完重複的所有書籍存進res裡,並回傳res
		BookStoreListRes res = new BookStoreListRes();
		res.setBookSet(bookSet);
		return res;
	}

	@Override
	public List<BookRankRes> bookrank() {

		// 使用JPA法從資料庫中把銷售量前五的書籍資料取出並由大到小排序
		List<BookStore> booklist = bookStoredao.findTop5ByOrderBySalesDesc();

		// 創建一個BookRankRes的陣列,將要回傳的書籍資訊存入
		List<BookRankRes> booksinfo = new ArrayList<>();

		// foreach從資料庫取出排行前五的書籍資訊
		for (BookStore book : booklist) {

			// 在迴圈中創建一個BookRankRes的新物件,讓每次進入迴圈都能設定新的res,才不會把資料覆蓋
			BookRankRes res = new BookRankRes();

			res.setIsbn(book.getIsbn());
			res.setName(book.getName());
			res.setWriter(book.getWriter());
			res.setPrice(book.getPrice());

			// 將設定好的書籍資訊存入booksinfo
			booksinfo.add(res);
		}
		return booksinfo;
	}

	@Override
	public BookStoreListRes buyBooks(orderBookReq orderList) {

		// 創建最後要回傳的總價格
		int price;
		int totalprice = 0;

		// 創建回傳各種錯誤訊息的messageList
		Set<String> messageSet = new HashSet<>();
		// 創建儲存要購買的書籍資料的陣列
		List<BookStoreRes> booksList = new ArrayList<>();

		// 將輸入的陣列foreach,取得輸入的isbn及購買數量
		for (orderBookReq entry : orderList.getOrderList()) {

			// 判斷輸入的isbn是否有值,若沒有則回傳錯誤訊息
			if (!StringUtils.hasText(entry.getIsbn())) {
				messageSet.add("編號:" + entry.getIsbn() + BookStoreRtnCode.ISBN_REQUIRED.getMessage());

			}
			// 判斷是否有輸入購買數量,或購買數量是否等於0及負數,若不符合規則要求則回傳錯誤訊息
			if (entry.getNum() == null || entry.getNum() <= 0) {
				messageSet.add("編號:" + entry.getIsbn() + BookStoreRtnCode.NUM_REQUIRED.getMessage());

			}

			// 找出資料庫中的所有書籍
			List<BookStore> booksInfo = bookStoredao.findAll();

			// 用來記錄進入迴圈的次數
			int x = 0;

			// 將資料庫的所有書籍foreach,取得書籍裡的資料
			for (BookStore item : booksInfo) {

				// 這裡的x值代表迴圈的次數
				x++;

				// 判斷輸入的isbn與資料庫符不符合,符合的話就進行購買的動作
				if (entry.getIsbn().equalsIgnoreCase(item.getIsbn())) {

					// 若庫存大於等於購買數量才進行購買的動作
					if (item.getStorage() >= entry.getNum()) {

						// 設定購買後的庫存以及銷售量
						item.setSales(item.getSales() + entry.getNum());
						item.setStorage(item.getStorage() - entry.getNum());

						// 將設定好的庫存及銷售量存回資料庫
						BookStore bookstore = bookStoredao.save(item);

						// 計算購買的書籍單價及總價
						price = bookstore.getPrice() * entry.getNum();
						totalprice += price;

						// 創建新的BookStoreRes,把要回傳的項目存進新物件裡
						BookStoreRes bookInfo = new BookStoreRes();

						bookInfo.setIsbn(bookstore.getIsbn());
						bookInfo.setName(bookstore.getName());
						bookInfo.setPrice(bookstore.getPrice());
						bookInfo.setNum(entry.getNum());
						bookInfo.setBuyprice(price);

						// 將回傳資訊加入bookList
						booksList.add(bookInfo);

						// 完成購買動作直接進入下一個購買項目,才不會進入313行的加入錯誤訊息
						break;
					}
					// 若庫存小於購買數量則不執行購買動作,並將錯誤訊息存入mesaageList裡
					else {
						messageSet.add("編號:" + entry.getIsbn() + BookStoreRtnCode.STORAGE_ERRO.getMessage() + " 目前庫存:"
								+ item.getStorage());
					}
				}
				// 若輸入的isbn對比完資料庫中的所有書後沒有符合的,就將錯誤訊息加入messageSet裡
				else if (!entry.getIsbn().equalsIgnoreCase(item.getIsbn()) && x == booksInfo.size()) {
					messageSet.add("編號:" + entry.getIsbn() + BookStoreRtnCode.NO_FOUND_ISBN.getMessage());
				}
			}
		}
		// 創建BookStoreListRes的新物件,用來回傳所有要顯示的資訊
		BookStoreListRes resbooks = new BookStoreListRes();

		// 將總價格,購買的書籍資訊,訊息加入resbooks
		resbooks.setTotalprice(totalprice);
		resbooks.setBooksInfo(booksList);
		resbooks.setMessageSet(messageSet);

		return resbooks;
	}

	@Override
	public BookStoreListRes searchBooks(String code, String isbn, String name, String writer) {

		// 判斷輸入的查詢項目有沒有值,若都沒有則回傳錯誤訊息
		if (!StringUtils.hasText(isbn) && !StringUtils.hasText(writer) && !StringUtils.hasText(name)) {

			return new BookStoreListRes(BookStoreRtnCode.ISBN_NAME_WRITER_REQUIRED.getMessage());
		}

		// 從資料庫把輸入項目所對應的書籍資訊取出來
		Optional<BookStore> bookOp = bookStoredao.findById(isbn);
		List<BookStore> booksFindByName = bookStoredao.findByName(name);
		List<BookStore> booksFindByWriter = bookStoredao.findByWriter(writer);

		// 創建一個用來回傳書籍資訊的物件
		BookStoreListRes res = new BookStoreListRes();

		// 判斷是消費者查詢或書籍商查詢,若有輸入權限密碼0000則為書籍商,若無輸入或輸入錯誤則為消費者,這裡是消費者查詢的部分
		if (code == null || !code.equalsIgnoreCase("0000")) {

			// 若輸入的查詢項目為isbn
			if (StringUtils.hasText(isbn) && bookOp.isPresent()) {

				BookStore book = bookOp.get();

				res = cusGetBook(book);
			}

			// 若輸入的查詢項目為書名
			if (StringUtils.hasText(name) && !bookStoredao.findByName(name).isEmpty()) {

				res = cusGetBooks(booksFindByName);
			}

			// 若輸入的查詢項目為作者
			if (StringUtils.hasText(writer) && !bookStoredao.findByWriter(writer).isEmpty()) {

				res = cusGetBooks(booksFindByWriter);
			}
			return res;

		}
		// 這裡是書籍商查詢的部分
		else {

			// 若輸入的查詢項目為isbn
			if (StringUtils.hasText(isbn) && bookStoredao.findById(isbn).isPresent()) {

				BookStore book = bookOp.get();
				res = shopGetBook(book);
			}

			// 若輸入的查詢項目為書名
			if (StringUtils.hasText(name) && !bookStoredao.findByName(name).isEmpty()) {

				res = shopGetBooks(booksFindByName);
			}

			// 若輸入的查詢項目為作者
			if (StringUtils.hasText(writer) && !bookStoredao.findByWriter(writer).isEmpty()) {

				res = shopGetBooks(booksFindByWriter);
			}
		}
		return res;
	}

	@Override
	public BookStoreRes updateStorage(String isbn, Integer num) {

		if (!StringUtils.hasText(isbn)) {
			return new BookStoreRes(BookStoreRtnCode.ISBN_REQUIRED.getMessage());
		}
		if (num == null) {
			return new BookStoreRes(BookStoreRtnCode.NUM_REQUIRED.getMessage());
		}

		BookStoreRes messageList = new BookStoreRes();

		Optional<BookStore> bookOp = bookStoredao.findById(isbn);

		if (bookOp.isPresent()) {

			BookStore book = bookOp.get();

			if (book.getStorage() + num < 0) {

				return new BookStoreRes(BookStoreRtnCode.NUM_REQUIRED.getMessage());
			}

			book.setStorage(book.getStorage() + num);

			bookStoredao.save(book);

			messageList.setIsbn(book.getIsbn());
			messageList.setName(book.getName());
			messageList.setPrice(book.getPrice());
			messageList.setStorage(book.getStorage());

		} else {
			return new BookStoreRes(BookStoreRtnCode.NO_FOUND_ISBN.getMessage());
		}
		return messageList;
	}

	@Override
	public BookStoreRes updatePrice(String isbn, Integer price) {

		if (!StringUtils.hasText(isbn)) {
			return new BookStoreRes(BookStoreRtnCode.ISBN_REQUIRED.getMessage());
		}
		if (price == null) {
			return new BookStoreRes(BookStoreRtnCode.PRICE_REQUIRED.getMessage());
		}

		BookStoreRes messageList = new BookStoreRes();

		Optional<BookStore> bookOp = bookStoredao.findById(isbn);

		if (bookOp.isPresent()) {

			if (price < 0) {

				return new BookStoreRes(BookStoreRtnCode.PRICE_REQUIRED.getMessage());
			}
			BookStore book = bookOp.get();

			book.setPrice(price);

			bookStoredao.save(book);

			messageList.setIsbn(book.getIsbn());
			messageList.setName(book.getName());
			messageList.setPrice(book.getPrice());
			messageList.setStorage(book.getStorage());

		} else {
			return new BookStoreRes(BookStoreRtnCode.NO_FOUND_ISBN.getMessage());
		}

		return messageList;
	}
}
