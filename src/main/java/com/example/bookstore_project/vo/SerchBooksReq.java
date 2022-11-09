package com.example.bookstore_project.vo;

public class SerchBooksReq {
	
	private String code;
	
	private String isbn;
	
	private String name;
	
	private String writer;
	
	
	public SerchBooksReq() {
		
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	

}
