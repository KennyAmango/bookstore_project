package com.example.bookstore_project.vo;

import java.util.List;

import com.example.bookstore_project.entity.BookStore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookStoreRes {
	
	@JsonProperty("book_info")
	private BookStore bookstore;
	
	private String id;
	
	private String category;

	private String writer;
	
	private String name;
	
	private Integer price;
	
	private Integer sales_volume;
	
	private Integer storage;
	
	private String message;
	
    private Integer num;
	
	private Integer totalprice;
	
	public BookStoreRes () {
		
	}
	public List<String>messagelist;
	
	public List<BookStore>booklist;
	
	public List<BookStoreRes>booksInfo;
	
	public BookStoreRes(String message) {
		this.message = message;
	}
	
	public BookStoreRes(BookStore bookstore,String message) {
		this.bookstore = bookstore;
		this.message = message;
	}

	public BookStore getBookstore() {
		return bookstore;
	}

	public void setBookstore(BookStore bookstore) {
		this.bookstore = bookstore;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getSales_volume() {
		return sales_volume;
	}
	public void setSales_volume(Integer sales_volume) {
		this.sales_volume = sales_volume;
	}
	public Integer getStorage() {
		return storage;
	}
	public void setStorage(Integer storage) {
		this.storage = storage;
	}

	public List<BookStore> getBooklist() {
		return booklist;
	}

	public void setBooklist(List<BookStore> booklist) {
		this.booklist = booklist;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(Integer totalprice) {
		this.totalprice = totalprice;
	}

	public List<BookStoreRes> getBooksInfo() {
		return booksInfo;
	}

	public void setBooksInfo(List<BookStoreRes> booksInfo) {
		this.booksInfo = booksInfo;
	}

	public List<String> getMessagelist() {
		return messagelist;
	}

	public void setMessagelist(List<String> messagelist) {
		this.messagelist = messagelist;
	}

	
}
