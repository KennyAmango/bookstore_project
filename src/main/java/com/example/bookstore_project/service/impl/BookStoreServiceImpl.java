package com.example.bookstore_project.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookstore_project.service.ifs.BookStoreService;
import com.example.bookstore_project.vo.BookRankRes;
import com.example.bookstore_project.vo.BookStoreRes;
import com.example.bookstore_project.vo.OrderBookRes;
import com.example.bookstore_project.constans.BookStoreRtnCode;
import com.example.bookstore_project.entity.BookStore;
import com.example.bookstore_project.repository.BookStoreDao;

@Service
public class BookStoreServiceImpl implements BookStoreService{
	
	@Autowired
	private BookStoreDao bookStoredao;

	@Override
	public BookStoreRes create(String id, String name, String category, String writer, 
			Integer price, Integer storage) {
		
		if(!bookStoredao.findById(id).isPresent()) {
		BookStore book = new BookStore(id,name,category,writer,price,storage);
		bookStoredao.save(book);
		BookStoreRes res = new BookStoreRes(book,BookStoreRtnCode.CREATE_SUCCESSFUL.getMessage());
		return res;
		}
		return null;
	}

	@Override
	public BookStoreRes update(String id, String name, String category, String writer, Integer price, Integer storage) {
		BookStore book = new BookStore(id,name,category,writer,price,storage);
		bookStoredao.save(book);
		BookStoreRes res = new BookStoreRes(book,BookStoreRtnCode.UPDATE_SUCCESSFUL.getMessage());
		return res;
	}

	@Override
	public BookStoreRes deleteById(String id) {
		
		 bookStoredao.deleteById(id);
		 
		 return new BookStoreRes(BookStoreRtnCode.Delete_SUCCESSFUL.getMessage());
	}

	@Override
	public List<BookStore> findByCategory(String category) {
		BookStoreRes res = new BookStoreRes();
		
		Set<String>categorySetlist = new HashSet<>();
		String[] categorylist = category.split(",");
	
		for(int i = 0; i < categorylist.length; i++) {
			String item = categorylist[i].trim();
			categorySetlist.add(item);
		}
		List<BookStore>booksInfo = new ArrayList<>();
		
		for(String item : categorySetlist) {
			res.setBooklist(bookStoredao.findByCategoryContaining(item));
			for(BookStore book : res.getBooklist()) {
				booksInfo.add(book);
			}
		}
		return booksInfo;
	}

	@Override
	public List<BookRankRes> bookrank() {
		
		List<BookStore> booklist = bookStoredao.findTop5ByOrderBySalesDesc();
		 
		 List<BookRankRes>booksinfo = new ArrayList<>();
		 
		 for(BookStore book : booklist) {
			 BookRankRes res = new BookRankRes();
			 res.setId(book.getId());
			 res.setName(book.getName());
			 res.setWriter(book.getWriter());
			 res.setPrice(book.getPrice());
			 booksinfo.add(res);
		 }
		 return booksinfo;
}

	@Override
	public BookStoreRes buyBooks(Map<String,Integer>orderlist) {
		if(orderlist.isEmpty()){
			return new BookStoreRes(null,BookStoreRtnCode.ID_REQUIRED.getMessage());
		}
		int price;
		for (Map.Entry<String,Integer> entry : orderlist.entrySet()) {
			
			Optional<BookStore> book = bookStoredao.findById(entry.getKey());
			
			if(book.isPresent() && entry.getValue()> 0) {
				
				book.get().setSales(book.get().getSales()+ 1);
				book.get().setStorage(book.get().getStorage()-1);
				
				
				OrderBookRes res = new OrderBookRes();
				price = book.get().getPrice() * entry.getValue();
				res.setId(book.get().getId());
				res.setName(book.get().getName());
				res.setPrice(price);
			}
		}
		return null;
	}
}
