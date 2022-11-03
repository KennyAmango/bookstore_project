package com.example.bookstore_project.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "bookstore")
public class BookStore {
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "category")
	private String category;
	
	@Column(name = "writer")
	private String writer;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "price")
	private int price;
	
	@Column(name = "sales_volume")
	private int sales;
	
	@Column(name = "storage")
	private int storage;
	
	public BookStore() {
		
	}
	
	public BookStore(String id, String name, String category, String writer, 
			int price, int storage) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.writer = writer;
		this.price = price;
		this.storage = storage;
		
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getSales() {
		return sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}

	public int getStorage() {
		return storage;
	}

	public void setStorage(int storage) {
		this.storage = storage;
	}

}
