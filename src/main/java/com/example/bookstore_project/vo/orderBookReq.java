package com.example.bookstore_project.vo;

import java.util.List;

public class orderBookReq {
	
	private String id;
	
	private Integer num;
	
	List<orderBookReq> orderList;
	
	public orderBookReq() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
