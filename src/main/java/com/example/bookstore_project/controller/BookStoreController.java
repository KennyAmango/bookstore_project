package com.example.bookstore_project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstore_project.service.ifs.BookStoreService;
import com.example.bookstore_project.constans.BookStoreRtnCode;
import com.example.bookstore_project.vo.BookStoreRes;
import com.example.bookstore_project.vo.BookStoreListRes;
import com.example.bookstore_project.vo.SerchBooksReq;
import com.example.bookstore_project.vo.UpdatePriceReq;
import com.example.bookstore_project.vo.UpdateStorageReq;
import com.example.bookstore_project.vo.orderBookReq;
import com.example.bookstore_project.vo.BookRankRes;
import com.example.bookstore_project.vo.BookStoreReq;

@CrossOrigin
@RestController
public class BookStoreController {

	@Autowired
	private BookStoreService bookstoreservice;

	private BookStoreRes checkreq(BookStoreReq req) {

		if (!StringUtils.hasText(req.getIsbn())) {
			return new BookStoreRes(BookStoreRtnCode.ISBN_REQUIRED.getMessage());
		} else if (!StringUtils.hasText(req.getName())) {
			return new BookStoreRes(BookStoreRtnCode.NAME_REQUIRED.getMessage());
		} else if (!StringUtils.hasText(req.getCategory())) {
			return new BookStoreRes(BookStoreRtnCode.CATEGORY_REQUIRED.getMessage());
		} else if (!StringUtils.hasText(req.getWriter())) {
			return new BookStoreRes(BookStoreRtnCode.WRITER_REQUIRED.getMessage());
		} else if (req.getPrice() == null || req.getPrice() < 0) {
			return new BookStoreRes(BookStoreRtnCode.PRICE_REQUIRED.getMessage());
		} else if (req.getStorage() == null || req.getStorage() < 0) {
			return new BookStoreRes(BookStoreRtnCode.STORAGE_REQUIRED.getMessage());
		}
		return null;
	}

	@PostMapping(value = "/api/createbook")
	public BookStoreRes create(@RequestBody BookStoreReq req) {
		// �ϥ�29�檺�P�_���ӧP�_��J����
		BookStoreRes checkreq = checkreq(req);
		// �Ycheckreq�^��null,�h�N���J���ȨS�����D,�Y�i�ϥΤ�k�Ыطs�����y
		if (checkreq == null) {
			BookStoreRes res = bookstoreservice.create(req.getIsbn(), req.getName(), req.getCategory(), req.getWriter(),
					req.getPrice(), req.getStorage());
			// �Yres��null,�h�N���Ʈw�w���ۦP��isbn,�o�ɴN�^�ǿ��~�T��
			if (res == null) {
				return new BookStoreRes(BookStoreRtnCode.ISBN_EXISTED.getMessage() + req.getIsbn());
			}
			// �^��53��Ыئn���y��res
			return res;
		}
		// �Y52��:checkreq����null,�h�N���J���Ȧ����~,�^��checkreq�̪����~�T��
		return checkreq;
	}

	@PostMapping(value = "/api/updatebook")
	public BookStoreRes updatebook(@RequestBody BookStoreReq req) {

		return bookstoreservice.updateByIsbn(req.getIsbn(), req.getName(), req.getCategory(), req.getWriter(),
				req.getPrice(), req.getStorage());
	}

	@PostMapping(value = "/api/deletebook")
	public BookStoreRes deletebook(@RequestBody BookStoreReq req) {

		return bookstoreservice.deleteByIsbn(req.getIsbn());

	}

	@PostMapping(value = "/api/findByCategory")
	public BookStoreListRes findByCategory(@RequestBody BookStoreReq req) {

		return bookstoreservice.findByCategory(req.getCategory());
	}

	@PostMapping(value = "/api/bookrank")
	public List<BookRankRes> bookrank() {
		return bookstoreservice.bookrank();
	}

	@PostMapping(value = "/api/buybooks")
	public BookStoreListRes buyBooks(@RequestBody orderBookReq req) {

		if (CollectionUtils.isEmpty(req.getOrderList())) {
			return new BookStoreListRes(BookStoreRtnCode.BUYBOOS_ERRO.getMessage());
		}

		return bookstoreservice.buyBooks(req);
	}

	@PostMapping(value = "/api/serchBooksByIdOrNameOrWriter")
	public BookStoreListRes searchBooks(@RequestBody SerchBooksReq req) {

		return bookstoreservice.searchBooks(req.getCode(), req.getIsbn(), req.getName(), req.getWriter());
	}

	@PostMapping(value = "/api/updateStorage")
	public BookStoreRes updateStorage(@RequestBody UpdateStorageReq req) {

		return bookstoreservice.updateStorage(req.getIsbn(), req.getnum());
	}

	@PostMapping(value = "/api/updatePrice")
	public BookStoreRes updatePrice(@RequestBody UpdatePriceReq req) {

		return bookstoreservice.updatePrice(req.getIsbn(), req.getPrice());

	}

}
