package com.example.bookstore_project.service.ifs;

import java.util.List;
import com.example.bookstore_project.vo.BookRankRes;
import com.example.bookstore_project.vo.BookStoreRes;
import com.example.bookstore_project.vo.BookStoreListRes;
import com.example.bookstore_project.vo.orderBookReq;

public interface BookStoreService {

	public BookStoreRes create(String isbn, String name, String category, String writer, Integer price,
			Integer storage);

	public BookStoreRes updateByIsbn(String isbn, String name, String category, String writer, Integer price,
			Integer storage);

	public BookStoreRes deleteByIsbn(String isbn);

	public BookStoreListRes findByCategory(String category);

	public List<BookRankRes> bookrank();

	public BookStoreListRes buyBooks(orderBookReq orderlist);

	public BookStoreListRes searchBooks(String code, String isbn, String name, String writer);

	public BookStoreRes updateStorage(String isbn, Integer num);

	public BookStoreRes updatePrice(String isbn, Integer price);

}
