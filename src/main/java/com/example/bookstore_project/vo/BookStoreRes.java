package com.example.bookstore_project.vo;

import java.util.List;

import com.example.bookstore_project.entity.BookStore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookStoreRes {
	
	@JsonProperty("book_info")
	private BookStore bookstore;
	
	private String isbn;
	
	private String category;

	private String writer;
	
	private String name;
	
	private Integer price;
	
	private Integer sales;
	
	private Integer storage;
	
	private String message;
	
    private Integer num;
    
    private Integer buyprice;
	
	private Integer totalprice;
	
	public BookStoreRes () {
		
	}
	private List<String>messagelist;
	
	private List<BookStore>booklist;
	
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
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
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
	public Integer getSales() {
		return sales;
	}
	public void setSales(Integer sales) {
		this.sales = sales;
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

	public List<String> getMessagelist() {
		return messagelist;
	}

	public void setMessagelist(List<String> messagelist) {
		this.messagelist = messagelist;
	}

	public Integer getBuyprice() {
		return buyprice;
	}

	public void setBuyprice(Integer buyprice) {
		this.buyprice = buyprice;
	}

	
}
