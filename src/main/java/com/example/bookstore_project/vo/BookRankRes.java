package com.example.bookstore_project.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookRankRes {
	
	private String name;
	
	private String isbn;
	
	private String writer;
	
	private Integer price;
	
	List<BookRankRes> booksinfo;
	
	public BookRankRes() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public List<BookRankRes> getBooksinfo() {
		return booksinfo;
	}

	public void setBooksinfo(List<BookRankRes> booksinfo) {
		this.booksinfo = booksinfo;
	}
	

}
