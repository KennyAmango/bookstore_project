package com.example.bookstore_project.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookStoreReq {
	
	@JsonProperty("isbn")
	private String isbn;
	
	@JsonProperty("category")
	private String category;
	
	@JsonProperty("writer")
	private String writer;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("price")
	private Integer price;
	
	@JsonProperty("sales_volume")
	private int sales_volume;
	
	@JsonProperty("storage")
	private Integer storage;
	
	private Integer num;
	
	List<BookStoreReq>orderlist;
	

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

	public void setSales_volume(int sales_volume) {
		this.sales_volume = sales_volume;
	}

	public List<BookStoreReq> getOrderlist() {
		return orderlist;
	}

	public void setOrderlist(List<BookStoreReq> orderlist) {
		this.orderlist = orderlist;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	
}
