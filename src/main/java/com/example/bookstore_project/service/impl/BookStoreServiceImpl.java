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
import com.example.bookstore_project.vo.BookStoreReq;
import com.example.bookstore_project.vo.BookStoreRes;
import com.example.bookstore_project.vo.orderBookReq;
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
	public BookStoreRes buyBooks(List<orderBookReq>orderList) {
		
		int price;
		int totalprice = 0;
		BookStoreRes messageList = new BookStoreRes();
		List<String>buy = new ArrayList<>();
		buy.add("購買明細:");
		messageList.setMessagelist(buy);
		for (orderBookReq entry : orderList) {
			if(entry.getId().isEmpty()){
				return new BookStoreRes(null,BookStoreRtnCode.ID_REQUIRED.getMessage());
			}
			else if(entry.getNum()< 0){
				return new BookStoreRes(null,BookStoreRtnCode.NUM_REQUIRED.getMessage());
			}
		    if (bookStoredao.findById(entry.getId()).isPresent()) {
		    	Optional<BookStore> book = bookStoredao.findById(entry.getId());
			
				book.get().setSales(book.get().getSales()+ entry.getNum());
				book.get().setStorage(book.get().getStorage()- entry.getNum());
				bookStoredao.save(book.get());
				
				price = book.get().getPrice() * entry.getNum();
				totalprice += price;
				messageList.messagelist.add("書籍id:" + book.get().getId()+ " 書籍名稱:"+book.get().getName()
						+" 購買數量:"+entry.getNum()+ " 小計:"+ price);
		    }
		    else{
		    	messageList.messagelist.add(BookStoreRtnCode.ID_REQUIRED.getMessage()+ entry.getId());
			}
			
		}
		messageList.messagelist.add("總金額:"+totalprice);
		return messageList;
	}
}
