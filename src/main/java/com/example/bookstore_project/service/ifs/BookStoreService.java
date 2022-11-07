package com.example.bookstore_project.service.ifs;

import java.util.List;


import com.example.bookstore_project.entity.BookStore;
import com.example.bookstore_project.vo.BookRankRes;
import com.example.bookstore_project.vo.BookStoreRes;
import com.example.bookstore_project.vo.orderBookReq;

public interface BookStoreService {
	
	public BookStoreRes create(String id,String name,String category,String writer,
			Integer price,Integer storage);
	
	public BookStoreRes update(String id,String name,String category,String writer,
			Integer price,Integer storage);
	
	public BookStoreRes deleteById(String id);
	
	public BookStoreRes findByCategory(String category);
	
	public List<BookRankRes> bookrank();
	
	public BookStoreRes buyBooks(List<orderBookReq>orderlist);
	
	public BookStoreRes searchBooks(String code,String id_Or_Name_Or_Writer);
	
	public BookStoreRes updateStorage(String code,String id, int num);
	
	public BookStoreRes updatePrice(String code,String id, int price);

}
