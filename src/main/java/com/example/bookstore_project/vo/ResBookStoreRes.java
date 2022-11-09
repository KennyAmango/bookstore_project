package com.example.bookstore_project.vo;

import java.util.List;

import com.example.bookstore_project.entity.BookStore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResBookStoreRes {
	
	private List<BookStoreRes> orderlist;
	
	private List<BookStoreRes> serchlist;
	
	private List<BookStoreRes> booksInfo;
	
	private List<BookStore> booklist;
	
    private Integer buyprice;
	
	private Integer totalprice;
	
	private String message;
	
	private BookStore bookstore;

	private BookStoreRes bookstoreres;
	
	List<String> messagelist;
	
	public ResBookStoreRes() {
		
	}
	
	public ResBookStoreRes(BookStore bookstore,String message) {
		this.bookstore = bookstore;
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public BookStoreRes getBookstoreres() {
		return bookstoreres;
	}
	
	public List<String> getMessagelist() {
		return messagelist;
	}

	public void setMessagelist(List<String> messagelist) {
		this.messagelist = messagelist;
	}

	public void setBookstoreres(BookStoreRes bookstoreres) {
		this.bookstoreres = bookstoreres;
	}

	public List<BookStoreRes> getBooksInfo() {
		return booksInfo;
	}

	public void setBooksInfo(List<BookStoreRes> booksInfo) {
		this.booksInfo = booksInfo;
	}

	public List<BookStoreRes> getOrderlist() {
		return orderlist;
	}

	public void setOrderlist(List<BookStoreRes> orderlist) {
		this.orderlist = orderlist;
	}

	public List<BookStoreRes> getSerchlist() {
		return serchlist;
	}

	public void setSerchlist(List<BookStoreRes> serchlist) {
		this.serchlist = serchlist;
	}
	
	public List<BookStore> getBooklist() {
		return booklist;
	}

	public void setBooklist(List<BookStore> booklist) {
		this.booklist = booklist;
	}

	public BookStore getBookstore() {
		return bookstore;
	}

	public void setBookstore(BookStore bookstore) {
		this.bookstore = bookstore;
	}

	public Integer getBuyprice() {
		return buyprice;
	}

	public void setBuyprice(Integer buyprice) {
		this.buyprice = buyprice;
	}

	public Integer getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(Integer totalprice) {
		this.totalprice = totalprice;
	}
	
	

}
