package com.example.bookstore_project.service.ifs;

import java.util.List;
import com.example.bookstore_project.vo.BookRankRes;
import com.example.bookstore_project.vo.BookStoreRes;
import com.example.bookstore_project.vo.ResBookStoreRes;
import com.example.bookstore_project.vo.orderBookReq;

public interface BookStoreService {
	
	public BookStoreRes create(String isbn,String name,String category,String writer,
			Integer price,Integer storage);
	
	public BookStoreRes updateByIsbn(String isbn,String name,String category,String writer,
			Integer price,Integer storage);
	
	public BookStoreRes deleteById(String isbn);
	
	public ResBookStoreRes findByCategory(String category);
	
	public List<BookRankRes> bookrank();
	
	public ResBookStoreRes buyBooks(orderBookReq orderlist);
	
	public ResBookStoreRes searchBooks(String code,String isbn,String name,String writer);
	
	public BookStoreRes updateStorage(String code,String isbn, Integer num);
	
	public BookStoreRes updatePrice(String code,String isbn, Integer price);
	
	

}
