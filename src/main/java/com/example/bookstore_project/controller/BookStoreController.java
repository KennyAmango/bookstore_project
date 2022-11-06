package com.example.bookstore_project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstore_project.service.ifs.BookStoreService;
import com.example.bookstore_project.constans.BookStoreRtnCode;
import com.example.bookstore_project.vo.BookStoreRes;
import com.example.bookstore_project.vo.SerchBooksReq;
import com.example.bookstore_project.vo.UpdatePriceReq;
import com.example.bookstore_project.vo.UpdateStorageReq;
import com.example.bookstore_project.vo.orderBookReq;
import com.example.bookstore_project.vo.BookRankRes;
import com.example.bookstore_project.vo.BookStoreReq;

@RestController
public class BookStoreController {
	
	@Autowired
	private BookStoreService bookstoreservice;
	
private BookStoreRes checkreq(BookStoreReq req) {
		
		if(!StringUtils.hasText(req.getId())) {
			return new BookStoreRes(BookStoreRtnCode.ID_REQUIRED.getMessage());
		}else if(!StringUtils.hasText(req.getName())) {
			return new BookStoreRes(BookStoreRtnCode.NAME_REQUIRED.getMessage());
		}
		else if(!StringUtils.hasText(req.getCategory())) {
			return new BookStoreRes(BookStoreRtnCode.CATEGORY_REQUIRED.getMessage());
		}
		else if(!StringUtils.hasText(req.getWriter())) {
			return new BookStoreRes(BookStoreRtnCode.WRITER_REQUIRED.getMessage());
		}
		else if(req.getPrice() < 0) {
			return new BookStoreRes(BookStoreRtnCode.PRICE_REQUIRED.getMessage());
		}
		else if(req.getStorage() < 0) {
			return new BookStoreRes(BookStoreRtnCode.STORAGE_REQUIRED.getMessage());
		}
		return null;
	}
	
	@PostMapping(value = "/api/createbook")
	public BookStoreRes create(@RequestBody BookStoreReq req) {
		BookStoreRes checkreq = checkreq(req);
		if(checkreq == null) {
			BookStoreRes res = bookstoreservice.create(req.getId(), req.getName(), req.getCategory(), req.getWriter(), 
					req.getPrice(),req.getStorage());
			if(res == null) {
				return new BookStoreRes(BookStoreRtnCode.ID_EXISTED.getMessage() + req.getId());
			}
			return res;
		}
		return checkreq;
	}
	
	
	@PostMapping(value = "/api/updatebook")
	public BookStoreRes updatebook(@RequestBody BookStoreReq req) {
		
		BookStoreRes checkreq = checkreq(req);
		if(checkreq == null) {
			return checkreq;
		}
		
		BookStoreRes res = bookstoreservice.create(req.getId(), req.getName(), req.getCategory(), req.getWriter(), 
				req.getPrice(),req.getStorage());
		
		return res;
	}
	
	@PostMapping(value = "/api/deletebook")
	public BookStoreRes deletebook(@RequestBody BookStoreReq req) {
		
		return bookstoreservice.deleteById(req.getId());
		
	}
	
	@PostMapping(value = "/api/findByCategory")
	public BookStoreRes findByCategory(@RequestBody BookStoreReq req) {
		
		if(req.getCategory().isEmpty()) {
			return new BookStoreRes(null,BookStoreRtnCode.CATEGORY_REQUIRED.getMessage());
		}
		
		BookStoreRes res = new BookStoreRes();
		res.setBooklist(bookstoreservice.findByCategory(req.getCategory()));
		
		return res;
	}
	
	@PostMapping(value = "/api/bookrank")
	public List<BookRankRes> bookrank() {
		return bookstoreservice.bookrank();
	}
	
	@PostMapping(value = "/api/buybooks")
	public BookStoreRes buyBooks(@RequestBody orderBookReq req) {
		return bookstoreservice.buyBooks(req.getOrderList());
	}
	
	@PostMapping(value = "/api/serchBooksByIdOrNameOrWriter")
	public BookStoreRes searchBooks(@RequestBody SerchBooksReq req) {
		if(!StringUtils.hasText(req.getIdornameorwriter())) {
			return new BookStoreRes(null,BookStoreRtnCode.ID_NAME_WRITER_REQUIRED.getMessage());
		}
		return bookstoreservice.searchBooks(req.getCode(),req.getIdornameorwriter());
	}
	
	@PostMapping(value = "/api/updateStorage")
	public BookStoreRes updateStorage(@RequestBody UpdateStorageReq req) {
		
		return bookstoreservice.updateStorage(req.getCode(), req.getId(), req.getnum());
	}
	
	@PostMapping(value = "/api/updatePrice")
	public BookStoreRes updatePrice(@RequestBody UpdatePriceReq req) {
		return bookstoreservice.updatePrice(req.getCode(),  req.getId(), req.getPrice());
		
	}

}
