package com.example.bookstore_project.service.ifs;

import java.util.List;
import java.util.Map;

import com.example.bookstore_project.entity.BookStore;
import com.example.bookstore_project.vo.BookRankRes;
import com.example.bookstore_project.vo.BookStoreRes;

public interface BookStoreService {
	
	public BookStoreRes create(String id,String name,String category,String writer,
			Integer price,Integer storage);
	
	public BookStoreRes update(String id,String name,String category,String writer,
			Integer price,Integer storage);
	
	public BookStoreRes deleteById(String id);
	
	public List<BookStore> findByCategory(String category);
	
	public List<BookRankRes> bookrank();
	
	public BookStoreRes buyBooks(Map<String,Integer>orderlist);

}
