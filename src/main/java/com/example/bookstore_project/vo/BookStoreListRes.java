package com.example.bookstore_project.vo;

import java.util.List;
import java.util.Set;

import com.example.bookstore_project.entity.BookStore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookStoreListRes {

	private List<BookStoreRes> booksInfo;

	private Set<BookStore> bookSet;

	private List<BookStore> booklist;

	private Integer totalprice;

	private String message;

	private BookStoreRes bookstore;

	private BookStoreRes bookstoreres;

	List<String> messagelist;

	Set<String> messageSet;

	public BookStoreListRes() {

	}

	public BookStoreListRes(String message) {
		this.message = message;
	}

	public BookStoreListRes(BookStoreRes bookstoreres, String message) {
		this.bookstoreres = bookstore;
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

	public Set<BookStore> getBookSet() {
		return bookSet;
	}

	public void setBookSet(Set<BookStore> bookSet) {
		this.bookSet = bookSet;
	}

	public List<BookStoreRes> getBooksInfo() {
		return booksInfo;
	}

	public void setBooksInfo(List<BookStoreRes> booksInfo) {
		this.booksInfo = booksInfo;
	}

	public List<BookStore> getBooklist() {
		return booklist;
	}

	public void setBooklist(List<BookStore> booklist) {
		this.booklist = booklist;
	}

	public Integer getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(Integer totalprice) {
		this.totalprice = totalprice;
	}

	public BookStoreRes getBookstore() {
		return bookstore;
	}

	public void setBookstore(BookStoreRes bookstore) {
		this.bookstore = bookstore;
	}

	public Set<String> getMessageSet() {
		return messageSet;
	}

	public void setMessageSet(Set<String> messageSet) {
		this.messageSet = messageSet;
	}

}
