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
		// �p�G�b��Ʈw�S�����۹�����isbn,�N�Ыطs��BookStore����,�N��J�ȳ]�w�b�o�ӷs����W
		if (!bookStoredao.existsById(isbn)) {

			BookStore book = new BookStore(isbn, name, category, writer, price, storage);
			// �s�i��Ʈw��,�^��res
			bookStoredao.save(book);

			BookStoreRes res = new BookStoreRes(book, BookStoreRtnCode.CREATE_SUCCESSFUL.getMessage());
			return res;
		}
		// �Y�����h�^��null��Controller�P�_
		return null;
	}

	@Override
	public BookStoreRes updateByIsbn(String isbn, String name, String category, String writer, Integer price,
			Integer storage) {

		// �P�_��J��isbn�O�_����,�Y�S���ȫh�^��isbn���ର��
		if (!StringUtils.hasText(isbn)) {
			return new BookStoreRes(BookStoreRtnCode.ISBN_REQUIRED.getMessage());
		}

		// �P�_��J��isbn�b��Ʈw�O�_�s�b,�Y�S���h�^�ǵL��isbn
		Optional<BookStore> bookInfo = bookStoredao.findById(isbn);
		if (!bookInfo.isPresent()) {
			return new BookStoreRes(BookStoreRtnCode.NO_FOUND_ISBN.getMessage());
		}

		// �q��Ʈw��X�ŦX��Jisbn�����y���,�å�book���_��
		BookStore book = bookInfo.get();

		// �P�_�Ҧ���J�����,�Y������즳��J�n��諸��,�h����諸�Ȧs��book��
		// �Y�������S����J��,�h�������,�O�����
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

		// �N��ʫ᪺book�s�^��Ʈw��
		bookStoredao.save(book);

		// �^�ǧ�ʫ᪺���y��ƥH�Φ��\�T��
		return new BookStoreRes(book, BookStoreRtnCode.UPDATE_SUCCESSFUL.getMessage());
	}

	@Override
	public BookStoreRes deleteByIsbn(String isbn) {

		// �P�_��J��isbn�O�_����
		if (!StringUtils.hasText(isbn)) {
			return new BookStoreRes(BookStoreRtnCode.ISBN_REQUIRED.getMessage());
		}

		// �P�_��J��isbn�s���s�b���Ʈw
		if (!bookStoredao.existsById(isbn)) {
			return new BookStoreRes(BookStoreRtnCode.ISBN_REQUIRED.getMessage() + isbn);
		}

		// �R����Ʈw�̲ŦX��Jisbn�����y
		bookStoredao.deleteById(isbn);

		// �^�ǧR�����\���T��
		return new BookStoreRes("ISBN:" + isbn + BookStoreRtnCode.Delete_SUCCESSFUL.getMessage());
	}

	@Override
	public BookStoreListRes findByCategory(String category) {

		// �P�_��J�����y���O�O�_����,�S���h�^�ǿ��~�T��
		if (!StringUtils.hasText(category)) {
			return new BookStoreListRes(BookStoreRtnCode.CATEGORY_REQUIRED.getMessage());
		}

		// �N��J�����y���O�γr��������,���槹��ӧO�s�Jcategorylist��
		String[] categorylist = category.split(",");
		
		Set<String> categorySet = new HashSet<>();
		// �N�s�bcategorylist�̪����y���Oforeach�X�Ӧs�iSet�}�C��,�h������
		for (String item : categorylist) {
			categorySet.add(item.trim());
		}

		// �����Ʈw�Ҧ��ѧ�X��
		List<BookStore> books = bookStoredao.findAll();

		Set<BookStore> bookSet = new HashSet<>();

		// �N��J�������P��Ʈw�����Ҧ��Ѷi����,���Ʈw�t��item�������Ѧs�JbookSet,�h�����ƪ����y
		for (BookStore book : books) {
			for (String item : categorySet) {
				// ��java���ت��\��P�_���S���������r��
				if (book.getCategory().contains(item)) {
					bookSet.add(book);
					
				}
			}
		}

		// �N�h�������ƪ��Ҧ����y�s�ires��,�æ^��res
		BookStoreListRes res = new BookStoreListRes();
		res.setBookSet(bookSet);
		return res;
	}

	@Override
	public List<BookRankRes> bookrank() {

		// �ϥ�JPA�k�q��Ʈw����P��q�e�������y��ƨ��X�åѤj��p�Ƨ�
		List<BookStore> booklist = bookStoredao.findTop5ByOrderBySalesDesc();

		// �Ыؤ@��BookRankRes���}�C,�N�n�^�Ǫ����y��T�s�J
		List<BookRankRes> booksinfo = new ArrayList<>();

		// foreach�q��Ʈw���X�Ʀ�e�������y��T
		for (BookStore book : booklist) {

			// �b�j�餤�Ыؤ@��BookRankRes���s����,���C���i�J�j�鳣��]�w�s��res,�~���|�����л\
			BookRankRes res = new BookRankRes();

			res.setIsbn(book.getIsbn());
			res.setName(book.getName());
			res.setWriter(book.getWriter());
			res.setPrice(book.getPrice());

			// �N�]�w�n�����y��T�s�Jbooksinfo
			booksinfo.add(res);
		}
		return booksinfo;
	}

	@Override
	public BookStoreListRes buyBooks(orderBookReq orderList) {

		// �Ыس̫�n�^�Ǫ��`����
		int price;
		int totalprice = 0;

		// �Ыئ^�ǦU�ؿ��~�T����messageList
		Set<String> messageSet = new HashSet<>();
		// �Ы��x�s�n�ʶR�����y��ƪ��}�C
		List<BookStoreRes> booksList = new ArrayList<>();

		// �N��J���}�Cforeach,���o��J��isbn���ʶR�ƶq
		for (orderBookReq entry : orderList.getOrderList()) {

			// �P�_��J��isbn�O�_����,�Y�S���h�^�ǿ��~�T��
			if (!StringUtils.hasText(entry.getIsbn())) {
				messageSet.add("�s��:" + entry.getIsbn() + BookStoreRtnCode.ISBN_REQUIRED.getMessage());

			}
			// �P�_�O�_����J�ʶR�ƶq,���ʶR�ƶq�O�_����0�έt��,�Y���ŦX�W�h�n�D�h�^�ǿ��~�T��
			if (entry.getNum() == null || entry.getNum() <= 0) {
				messageSet.add("�s��:" + entry.getIsbn() + BookStoreRtnCode.NUM_REQUIRED.getMessage());

			}

			// ��X��Ʈw�����Ҧ����y
			List<BookStore> booksInfo = bookStoredao.findAll();

			// �ΨӰO���i�J�j�骺����
			int x = 0;

			// �N��Ʈw���Ҧ����yforeach,���o���y�̪����
			for (BookStore item : booksInfo) {

				// �o�̪�x�ȥN��j�骺����
				x++;

				// �P�_��J��isbn�P��Ʈw�Ť��ŦX,�ŦX���ܴN�i���ʶR���ʧ@
				if (entry.getIsbn().equalsIgnoreCase(item.getIsbn())) {

					// �Y�w�s�j�󵥩��ʶR�ƶq�~�i���ʶR���ʧ@
					if (item.getStorage() >= entry.getNum()) {

						// �]�w�ʶR�᪺�w�s�H�ξP��q
						item.setSales(item.getSales() + entry.getNum());
						item.setStorage(item.getStorage() - entry.getNum());

						// �N�]�w�n���w�s�ξP��q�s�^��Ʈw
						BookStore bookstore = bookStoredao.save(item);

						// �p���ʶR�����y������`��
						price = bookstore.getPrice() * entry.getNum();
						totalprice += price;

						// �Ыطs��BookStoreRes,��n�^�Ǫ����ئs�i�s�����
						BookStoreRes bookInfo = new BookStoreRes();

						bookInfo.setIsbn(bookstore.getIsbn());
						bookInfo.setName(bookstore.getName());
						bookInfo.setPrice(bookstore.getPrice());
						bookInfo.setNum(entry.getNum());
						bookInfo.setBuyprice(price);

						// �N�^�Ǹ�T�[�JbookList
						booksList.add(bookInfo);

						// �����ʶR�ʧ@�����i�J�U�@���ʶR����,�~���|�i�J313�檺�[�J���~�T��
						break;
					}
					// �Y�w�s�p���ʶR�ƶq�h�������ʶR�ʧ@,�ñN���~�T���s�JmesaageList��
					else {
						messageSet.add("�s��:" + entry.getIsbn() + BookStoreRtnCode.STORAGE_ERRO.getMessage() + " �ثe�w�s:"
								+ item.getStorage());
					}
				}
				// �Y��J��isbn��񧹸�Ʈw�����Ҧ��ѫ�S���ŦX��,�N�N���~�T���[�JmessageSet��
				else if (!entry.getIsbn().equalsIgnoreCase(item.getIsbn()) && x == booksInfo.size()) {
					messageSet.add("�s��:" + entry.getIsbn() + BookStoreRtnCode.NO_FOUND_ISBN.getMessage());
				}
			}
		}
		// �Ы�BookStoreListRes���s����,�ΨӦ^�ǩҦ��n��ܪ���T
		BookStoreListRes resbooks = new BookStoreListRes();

		// �N�`����,�ʶR�����y��T,�T���[�Jresbooks
		resbooks.setTotalprice(totalprice);
		resbooks.setBooksInfo(booksList);
		resbooks.setMessageSet(messageSet);

		return resbooks;
	}

	@Override
	public BookStoreListRes searchBooks(String code, String isbn, String name, String writer) {

		// �P�_��J���d�߶��ئ��S����,�Y���S���h�^�ǿ��~�T��
		if (!StringUtils.hasText(isbn) && !StringUtils.hasText(writer) && !StringUtils.hasText(name)) {

			return new BookStoreListRes(BookStoreRtnCode.ISBN_NAME_WRITER_REQUIRED.getMessage());
		}

		// �q��Ʈw���J���ةҹ��������y��T���X��
		Optional<BookStore> bookOp = bookStoredao.findById(isbn);
		List<BookStore> booksFindByName = bookStoredao.findByName(name);
		List<BookStore> booksFindByWriter = bookStoredao.findByWriter(writer);

		// �Ыؤ@�ӥΨӦ^�Ǯ��y��T������
		BookStoreListRes res = new BookStoreListRes();

		// �P�_�O���O�̬d�ߩή��y�Ӭd��,�Y����J�v���K�X0000�h�����y��,�Y�L��J�ο�J���~�h�����O��,�o�̬O���O�̬d�ߪ�����
		if (code == null || !code.equalsIgnoreCase("0000")) {

			// �Y��J���d�߶��ج�isbn
			if (StringUtils.hasText(isbn) && bookOp.isPresent()) {

				BookStore book = bookOp.get();

				res = cusGetBook(book);
			}

			// �Y��J���d�߶��ج��ѦW
			if (StringUtils.hasText(name) && !bookStoredao.findByName(name).isEmpty()) {

				res = cusGetBooks(booksFindByName);
			}

			// �Y��J���d�߶��ج��@��
			if (StringUtils.hasText(writer) && !bookStoredao.findByWriter(writer).isEmpty()) {

				res = cusGetBooks(booksFindByWriter);
			}
			return res;

		}
		// �o�̬O���y�Ӭd�ߪ�����
		else {

			// �Y��J���d�߶��ج�isbn
			if (StringUtils.hasText(isbn) && bookStoredao.findById(isbn).isPresent()) {

				BookStore book = bookOp.get();
				res = shopGetBook(book);
			}

			// �Y��J���d�߶��ج��ѦW
			if (StringUtils.hasText(name) && !bookStoredao.findByName(name).isEmpty()) {

				res = shopGetBooks(booksFindByName);
			}

			// �Y��J���d�߶��ج��@��
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
