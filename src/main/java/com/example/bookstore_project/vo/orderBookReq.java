package com.example.bookstore_project.vo;

import java.util.List;

public class orderBookReq {
	
	private String isbn;
	
	private Integer num;
	
	List<orderBookReq> orderList;
	
	public orderBookReq() {
		
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public List<orderBookReq> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<orderBookReq> orderList) {
		this.orderList = orderList;
	}
	
	

}
