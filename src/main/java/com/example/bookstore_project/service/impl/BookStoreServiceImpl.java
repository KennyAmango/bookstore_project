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
import com.example.bookstore_project.vo.ResBookStoreRes;
import com.example.bookstore_project.vo.orderBookReq;
import com.example.bookstore_project.constans.BookStoreRtnCode;
import com.example.bookstore_project.entity.BookStore;
import com.example.bookstore_project.repository.BookStoreDao;

@Service
public class BookStoreServiceImpl implements BookStoreService {

	@Autowired
	private BookStoreDao bookStoredao;
	
	private ResBookStoreRes shopGetBook(BookStore books) {
		BookStoreRes book = new BookStoreRes();
		ResBookStoreRes bookInfo = new ResBookStoreRes();
		book.setIsbn(books.getIsbn());
		book.setName(books.getName());
		book.setWriter(books.getWriter());
		book.setPrice(books.getPrice());
		book.setStorage(books.getStorage());
		book.setSales(books.getSales());
		return bookInfo;
	}
	private ResBookStoreRes cusGetBook(BookStore books) {
		BookStoreRes book = new BookStoreRes();
		ResBookStoreRes bookInfo = new ResBookStoreRes();
		book.setIsbn(books.getIsbn());
		book.setName(books.getName());
		book.setWriter(books.getWriter());
		book.setPrice(books.getPrice());
		return bookInfo;
	}
	
	private ResBookStoreRes shopGetBooks(List<BookStore> books) {
		BookStoreRes bookInfo = new BookStoreRes();
		List<BookStoreRes> title = new ArrayList<>();
		ResBookStoreRes booksinfo = new ResBookStoreRes();
		booksinfo.setBooksInfo(title);
		for (BookStore book : books) {
			bookInfo.setIsbn(book.getIsbn());
			bookInfo.setName(book.getName());
			bookInfo.setWriter(book.getWriter());
			bookInfo.setPrice(book.getPrice());
			bookInfo.setStorage(book.getStorage());
			bookInfo.setSales(book.getSales());
			booksinfo.getBooksInfo().add(bookInfo);
		}
		return booksinfo;
	}
	
	private ResBookStoreRes cusGetBooks(List<BookStore> books) {
		BookStoreRes bookInfo = new BookStoreRes();
		List<BookStoreRes> title = new ArrayList<>();
		ResBookStoreRes booksinfo = new ResBookStoreRes();
		booksinfo.setBooksInfo(title);
		for (BookStore book : books) {
			bookInfo.setIsbn(book.getIsbn());
			bookInfo.setName(book.getName());
			bookInfo.setWriter(book.getWriter());
			bookInfo.setPrice(book.getPrice());
			booksinfo.getBooksInfo().add(bookInfo);
		}
		return booksinfo;
	}
	
	@Override
	public BookStoreRes create(String isbn, String name, String category, String writer, Integer price,
			Integer storage) {

		if (!bookStoredao.findById(isbn).isPresent()) {
			BookStore book = new BookStore(isbn, name, category, writer, price, storage);
			bookStoredao.save(book);
			BookStoreRes res = new BookStoreRes(book, BookStoreRtnCode.CREATE_SUCCESSFUL.getMessage());
			return res;
		}
		return null;
	}

	@Override
	public BookStoreRes updateByIsbn(String isbn, String name, String category, String writer, Integer price,
			Integer storage) {
		if (isbn == null) {
			return new BookStoreRes(null, BookStoreRtnCode.ISBN_REQUIRED.getMessage());
		}
		if (!bookStoredao.findById(isbn).isPresent()) {
			return new BookStoreRes(null, BookStoreRtnCode.NO_FOUND_ISBN.getMessage());
		}
		BookStore book = new BookStore();

		Optional<BookStore> bookInfo = bookStoredao.findById(isbn);

		if (name == null) {
			book.setName(bookInfo.get().getName());
		} else {
			book.setName(name);
		}
		if (category == null) {
			book.setCategory(bookInfo.get().getCategory());
		} else {
			book.setCategory(category);
		}
		if (writer == null) {
			book.setWriter(bookInfo.get().getWriter());
		} else {
			book.setWriter(writer);
		}
		if (price == null) {
			book.setPrice(bookInfo.get().getPrice());
		} else {
			book.setPrice(price);
		}
		if (storage == null) {
			book.setStorage(bookInfo.get().getStorage());
		} else {
			book.setStorage(storage);
		}
		book.setIsbn(isbn);

		bookStoredao.save(book);
		return new BookStoreRes(book, BookStoreRtnCode.UPDATE_SUCCESSFUL.getMessage());
	}

	@Override
	public BookStoreRes deleteById(String isbn) {
		if (isbn == null) {
			return new BookStoreRes(null, BookStoreRtnCode.ISBN_REQUIRED.getMessage());
		}
		if (!bookStoredao.findById(isbn).isPresent()) {
			return new BookStoreRes(BookStoreRtnCode.ISBN_REQUIRED.getMessage() + isbn);
		}

		bookStoredao.deleteById(isbn);

		return new BookStoreRes("ISBN:" + isbn + BookStoreRtnCode.Delete_SUCCESSFUL.getMessage());
	}

	@Override
	public ResBookStoreRes findByCategory(String category) {
		if (!StringUtils.hasText(category)) {
			return new ResBookStoreRes(null, BookStoreRtnCode.CATEGORY_REQUIRED.getMessage());
		}
		
		List<BookStoreRes> title = new ArrayList<>();
		ResBookStoreRes booksInfo = new ResBookStoreRes();
		booksInfo.setBooksInfo(title);
		ResBookStoreRes res = new ResBookStoreRes();

		Set<String> categorySetlist = new HashSet<>();
		String[] categorylist = category.split(",");

		for (int i = 0; i < categorylist.length; i++) {
			String item = categorylist[i].trim();
			categorySetlist.add(item);
		}

		for (String item : categorySetlist) {
			res.setBooklist(bookStoredao.findByCategoryContaining(item));
			if (res.getBooklist().isEmpty()) {
				return new ResBookStoreRes(null, BookStoreRtnCode.NO_CATEGORY.getMessage() + category);
			}
			for (BookStore book : res.getBooklist()) {
				BookStoreRes bookInfo = new BookStoreRes();
				bookInfo.setIsbn(book.getIsbn());
				bookInfo.setName(book.getName());
				bookInfo.setCategory(book.getCategory());
				bookInfo.setWriter(book.getWriter());
				bookInfo.setPrice(book.getPrice());
				booksInfo.getBooksInfo().add(bookInfo);
			}
		}
		return booksInfo;
	}

	@Override
	public List<BookRankRes> bookrank() {

		List<BookStore> booklist = bookStoredao.findTop5ByOrderBySalesDesc();

		List<BookRankRes> booksinfo = new ArrayList<>();

		for (BookStore book : booklist) {
			BookRankRes res = new BookRankRes();
			res.setIsbn(book.getIsbn());
			res.setName(book.getName());
			res.setWriter(book.getWriter());
			res.setPrice(book.getPrice());
			booksinfo.add(res);
		}
		return booksinfo;
	}

	@Override
	public ResBookStoreRes buyBooks(orderBookReq orderList) {
		
		ResBookStoreRes resbooks = new ResBookStoreRes();
		
		int price;
		int totalprice = 0;
		List<String> message = new ArrayList<>();
		message.add("購買訊息:");
		List<BookStoreRes> title = new ArrayList<>();
		BookStoreRes booksInfo = new BookStoreRes();
		resbooks.setMessagelist(message);
		resbooks.setOrderlist(title);
		

		for (orderBookReq entry : orderList.getOrderList()) {
			if (!StringUtils.hasText(entry.getIsbn())) {
				resbooks.getMessagelist()
						.add("編號:" + entry.getIsbn() + " " + BookStoreRtnCode.ISBN_REQUIRED.getMessage());
			} else if (entry.getNum() == null || entry.getNum() <= 0) {
				resbooks.getMessagelist()
						.add("編號:" + entry.getIsbn() + " " + BookStoreRtnCode.NUM_REQUIRED.getMessage());
			} else if (bookStoredao.findById(entry.getIsbn()).isPresent()) {
				Optional<BookStore> book = bookStoredao.findById(entry.getIsbn());

				if (book.get().getStorage() >= entry.getNum()) {
					book.get().setSales(book.get().getSales() + entry.getNum());
					book.get().setStorage(book.get().getStorage() - entry.getNum());
					BookStore bookstore = bookStoredao.save(book.get());

					price = bookstore.getPrice() * entry.getNum();
					totalprice += price;
					BookStoreRes bookInfo = new BookStoreRes();
					bookInfo.setIsbn(bookstore.getIsbn());
					bookInfo.setName(bookstore.getName());
					bookInfo.setPrice(bookstore.getPrice());
					bookInfo.setNum(entry.getNum());
					bookInfo.setBuyprice(price);
					resbooks.getOrderlist().add(bookInfo);
				} else {
					resbooks.getMessagelist().add("編號:" + entry.getIsbn() + " "
							+ BookStoreRtnCode.STORAGE_ERRO.getMessage() + " 目前庫存:" + book.get().getStorage());
				}
			} else {
				booksInfo.getMessagelist().add(BookStoreRtnCode.ISBN_REQUIRED.getMessage() + entry.getIsbn());
			}
		}
		resbooks.setTotalprice(totalprice);
		resbooks.getMessagelist().add("購買完成,歡迎下次光臨");
		return resbooks;
	}

	@Override
	public ResBookStoreRes searchBooks(String code, String isbn, String name, String writer) {
		ResBookStoreRes res = new ResBookStoreRes();
         
		if (code == null || !code.equalsIgnoreCase("0000")) {
			
			if(!StringUtils.hasText(isbn) && !StringUtils.hasText(writer) && !StringUtils.hasText(name)) {
				return new ResBookStoreRes(null, BookStoreRtnCode.ISBN_NAME_WRITER_REQUIRED.getMessage());
			}
				  if (StringUtils.hasText(isbn) && bookStoredao.findById(isbn).isPresent()) {
					Optional<BookStore> book = bookStoredao.findById(isbn);
					BookStore books = book.get();
					 res = cusGetBook(books);
				} else {}
				 
				  if (StringUtils.hasText(name) && !bookStoredao.findByName(name).isEmpty()) {
					List<BookStore> books = bookStoredao.findByName(name);
					res = cusGetBooks(books);
					}
				 else {}
			      
				  if (StringUtils.hasText(writer) && !bookStoredao.findByWriter(writer).isEmpty()) {
				List<BookStore> books = bookStoredao.findByWriter(writer);
				    res = cusGetBooks(books);
			    } else {}
		        return res;
		}
		else {
			if(!StringUtils.hasText(isbn) && !StringUtils.hasText(name) && !StringUtils.hasText(writer)) {
				return new ResBookStoreRes(null, BookStoreRtnCode.ISBN_NAME_WRITER_REQUIRED.getMessage());
			}
				  if (StringUtils.hasText(isbn) && bookStoredao.findById(isbn).isPresent()) {
					Optional<BookStore> book = bookStoredao.findById(isbn);
					BookStore books = book.get();
					res = shopGetBook(books);
				} else {}
				 
				  if (StringUtils.hasText(name) && !bookStoredao.findByName(name).isEmpty()) {
					List<BookStore> books = bookStoredao.findByName(name);
					res = shopGetBooks(books);
					}
				 else {}
			      
				  if (StringUtils.hasText(writer) && !bookStoredao.findByWriter(writer).isEmpty()) {
				    List<BookStore> books = bookStoredao.findByWriter(writer);
				    res = shopGetBooks(books);
			    } else {}
		}
		return res;
	}

	@Override
	public BookStoreRes updateStorage(String code, String isbn, Integer num) {
		if (!code.equalsIgnoreCase("0000")) {
			return new BookStoreRes(null, BookStoreRtnCode.ISBNPOWER_EXISTED.getMessage());
		}
		if (!StringUtils.hasText(isbn)) {
			return new BookStoreRes(null, BookStoreRtnCode.ISBN_REQUIRED.getMessage());
		}
		if (num == null) {
			return new BookStoreRes(null, BookStoreRtnCode.NUM_REQUIRED.getMessage());
		}
		List<String> message = new ArrayList<>();
		message.add("更新結果:");
		BookStoreRes messageList = new BookStoreRes();
		messageList.setMessagelist(message);

		if (bookStoredao.findById(isbn).isPresent()) {
			Optional<BookStore> book = bookStoredao.findById(isbn);
			if (book.get().getStorage() + num < 0) {
				messageList.getMessagelist().add(BookStoreRtnCode.STORAGE_REQUIRED.getMessage());
				;
				messageList.getMessagelist().add("目前庫存:" + book.get().getStorage());
				return messageList;
			}
			messageList.getMessagelist().add("更新前的庫存:" + book.get().getStorage());
			messageList.getMessagelist().add("===========");
			messageList.getMessagelist().add("更新後:");
			book.get().setStorage(book.get().getStorage() + num);
			bookStoredao.save(book.get());
			messageList.getMessagelist().add("ID:" + book.get().getIsbn());
			messageList.getMessagelist().add("書名:" + book.get().getName());
			messageList.getMessagelist().add("作者:" + book.get().getWriter());
			messageList.getMessagelist().add("價格:" + book.get().getPrice());
			messageList.getMessagelist().add("庫存:" + book.get().getStorage());
		}
		return messageList;
	}

	@Override
	public BookStoreRes updatePrice(String code, String isbn, Integer price) {
		if (!code.equalsIgnoreCase("0000")) {
			return new BookStoreRes(null, BookStoreRtnCode.ISBNPOWER_EXISTED.getMessage());
		}
		if (!StringUtils.hasText(isbn)) {
			return new BookStoreRes(null, BookStoreRtnCode.ISBN_REQUIRED.getMessage());
		}
		if (price == null) {
			return new BookStoreRes(null, BookStoreRtnCode.PRICE_REQUIRED.getMessage());
		}
		List<String> message = new ArrayList<>();
		message.add("更新結果:");
		BookStoreRes messageList = new BookStoreRes();
		messageList.setMessagelist(message);

		if (bookStoredao.findById(isbn).isPresent()) {
			Optional<BookStore> book = bookStoredao.findById(isbn);
			if (price < 0) {
				messageList.getMessagelist().add(BookStoreRtnCode.PRICE_REQUIRED.getMessage());
				;
				messageList.getMessagelist().add("目前價格:" + book.get().getPrice());
				return messageList;
			}
			messageList.getMessagelist().add("更新前的價格:" + book.get().getPrice());
			messageList.getMessagelist().add("===========");
			messageList.getMessagelist().add("更新後:");
			book.get().setPrice(price);
			bookStoredao.save(book.get());
			messageList.getMessagelist().add("ID:" + book.get().getIsbn());
			messageList.getMessagelist().add("書名:" + book.get().getName());
			messageList.getMessagelist().add("作者:" + book.get().getWriter());
			messageList.getMessagelist().add("價格:" + book.get().getPrice());
			messageList.getMessagelist().add("庫存:" + book.get().getStorage());
		}
		return messageList;
	}
}
