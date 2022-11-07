package com.example.bookstore_project.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.bookstore_project.service.ifs.BookStoreService;
import com.example.bookstore_project.vo.BookRankRes;
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
		if(!bookStoredao.findById(id).isPresent()) {
			return new BookStoreRes(BookStoreRtnCode.ID_REQUIRED.getMessage());
		}
		
		 bookStoredao.deleteById(id);
		 
		 return new BookStoreRes(BookStoreRtnCode.Delete_SUCCESSFUL.getMessage());
	}

	@Override
	public BookStoreRes findByCategory(String category) {
		if(!StringUtils.hasText(category)) {
			return new BookStoreRes(null,BookStoreRtnCode.CATEGORY_REQUIRED.getMessage());
		}
		BookStoreRes messageList = new BookStoreRes();
		List<String> title = new ArrayList<>();
		title.add("�j�M���O: "+ category);
		messageList.setMessagelist(title);
		
		BookStoreRes res = new BookStoreRes();
		
		Set<String>categorySetlist = new HashSet<>();
		String[] categorylist = category.split(",");
	
		for(int i = 0; i < categorylist.length; i++) {
			String item = categorylist[i].trim();
			categorySetlist.add(item);
		}
		
		for(String item : categorySetlist) {
			res.setBooklist(bookStoredao.findByCategoryContaining(item));
			for(BookStore book : res.getBooklist()) {
				messageList.messagelist.add("���yID:"+ book.getId());
				messageList.messagelist.add("�ѦW:"+ book.getName());
				messageList.messagelist.add("����:"+ book.getCategory());
				messageList.messagelist.add("�@��:"+ book.getWriter());
				messageList.messagelist.add("���:"+ book.getPrice());
				messageList.messagelist.add("===========");
			}
		}
		return messageList;
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
		buy.add("�ʶR����:");
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
				messageList.messagelist.add("���yid:" + book.get().getId()+ " ���y�W��:"+book.get().getName()
						+" �ʶR�ƶq:"+entry.getNum()+ " �p�p:"+ price);
		    }
		    else{
		    	messageList.messagelist.add(BookStoreRtnCode.ID_REQUIRED.getMessage()+ entry.getId());
			}
			
		}
		messageList.messagelist.add("�`���B:"+totalprice);
		return messageList;
	}

	@Override
	public BookStoreRes searchBooks(String code,String id_Or_Name_Or_Writer) {
		List<String>message = new ArrayList<>();
		message.add("�d�ߵ��G: ");
		BookStoreRes messageList = new BookStoreRes();
		messageList.setMessagelist(message);
		
		if(!code.equalsIgnoreCase("0000")) {
		if(id_Or_Name_Or_Writer.isEmpty()) {
			return new BookStoreRes(null,BookStoreRtnCode.ID_NAME_WRITER_REQUIRED.getMessage());
		}
		else if(bookStoredao.findById(id_Or_Name_Or_Writer).isPresent()) {
			Optional<BookStore> book = bookStoredao.findById(id_Or_Name_Or_Writer);
			messageList.messagelist.add("ID:"+book.get().getId());
			messageList.messagelist.add("�ѦW:"+book.get().getName());
			messageList.messagelist.add("�@��:"+book.get().getWriter());
			messageList.messagelist.add("����:"+book.get().getPrice());
		}
        else if(!bookStoredao.findByName(id_Or_Name_Or_Writer).isEmpty()) {
        	 List<BookStore> books = bookStoredao.findByName(id_Or_Name_Or_Writer);
        	 for(BookStore info : books) {
        		messageList.messagelist.add("ID:"+info.getId());
     			messageList.messagelist.add("�ѦW:"+info.getName());
     			messageList.messagelist.add("�@��:"+info.getWriter());
     			messageList.messagelist.add("����:"+info.getPrice());
     			messageList.messagelist.add("=============");
        	 }
		}
        else if(!bookStoredao.findByWriter(id_Or_Name_Or_Writer).isEmpty()) {
        	List<BookStore> books = bookStoredao.findByWriter(id_Or_Name_Or_Writer);
       	    for(BookStore info : books) {
       		    messageList.messagelist.add("ID:"+info.getId());
    			messageList.messagelist.add("�ѦW:"+info.getName());
    			messageList.messagelist.add("�@��:"+info.getWriter());
    			messageList.messagelist.add("����:"+info.getPrice());
    			messageList.messagelist.add("=============");
       	 }
		}
        else {
        	return new BookStoreRes(null,BookStoreRtnCode.ID_NAME_WRITER_REQUIRED.getMessage());
        }
	}
		if(code.equalsIgnoreCase("0000")) {
			if(id_Or_Name_Or_Writer.isEmpty()) {
				return new BookStoreRes(null,BookStoreRtnCode.ID_NAME_WRITER_REQUIRED.getMessage());
			}
			else if(bookStoredao.findById(id_Or_Name_Or_Writer).isPresent()) {
				Optional<BookStore> book = bookStoredao.findById(id_Or_Name_Or_Writer);
				messageList.messagelist.add("ID:"+book.get().getId());
				messageList.messagelist.add("�ѦW:"+book.get().getName());
				messageList.messagelist.add("�@��:"+book.get().getWriter());
				messageList.messagelist.add("����:"+book.get().getPrice());
				messageList.messagelist.add("�w�s:"+book.get().getStorage());
				messageList.messagelist.add("�P��q:"+book.get().getSales());
			}
	        else if(!bookStoredao.findByName(id_Or_Name_Or_Writer).isEmpty()) {
	        	 List<BookStore> books = bookStoredao.findByName(id_Or_Name_Or_Writer);
	        	 for(BookStore info : books) {
	        		messageList.messagelist.add("ID:"+info.getId());
	     			messageList.messagelist.add("�ѦW:"+info.getName());
	     			messageList.messagelist.add("�@��:"+info.getWriter());
	     			messageList.messagelist.add("����:"+info.getPrice());
	     			messageList.messagelist.add("�w�s:"+info.getStorage());
					messageList.messagelist.add("�P��q:"+info.getSales());
	     			messageList.messagelist.add("=============");
	        	 }
			}
	        else if(!bookStoredao.findByWriter(id_Or_Name_Or_Writer).isEmpty()) {
	        	List<BookStore> books = bookStoredao.findByWriter(id_Or_Name_Or_Writer);
	       	    for(BookStore info : books) {
	       		    messageList.messagelist.add("ID:"+info.getId());
	    			messageList.messagelist.add("�ѦW:"+info.getName());
	    			messageList.messagelist.add("�@��:"+info.getWriter());
	    			messageList.messagelist.add("����:"+info.getPrice());
	    			messageList.messagelist.add("�w�s:"+info.getStorage());
					messageList.messagelist.add("�P��q:"+info.getSales());
	    			messageList.messagelist.add("=============");
	       	 }
			}
	        else {
	        	return new BookStoreRes(null,BookStoreRtnCode.ID_NAME_WRITER_REQUIRED.getMessage());
	        }
		}
		return messageList;
	}

	@Override
	public BookStoreRes updateStorage(String code, String id, int num) {
		if(!code.equalsIgnoreCase("0000")) {
			return new BookStoreRes(null,BookStoreRtnCode.IDPOWER_EXISTED.getMessage());
		}
		if(!StringUtils.hasText(id)) {
			return new BookStoreRes(null,BookStoreRtnCode.ID_REQUIRED.getMessage());
		}
		List<String>message = new ArrayList<>();
		message.add("��s���G:");
		BookStoreRes messageList = new BookStoreRes();
		messageList.setMessagelist(message);
		
		if(bookStoredao.findById(id).isPresent()) {
			Optional<BookStore> book = bookStoredao.findById(id);
			if(book.get().getStorage() + num < 0) {
				messageList.messagelist.add(BookStoreRtnCode.STORAGE_REQUIRED.getMessage());;
				messageList.messagelist.add("�ثe�w�s:"+book.get().getStorage());
				return messageList;
			}
			messageList.messagelist.add("��s�e���w�s:"+book.get().getStorage());
			messageList.messagelist.add("===========");
			messageList.messagelist.add("��s��:");
			book.get().setStorage(book.get().getStorage() + num);
			bookStoredao.save(book.get());
			messageList.messagelist.add("ID:"+book.get().getId());
			messageList.messagelist.add("�ѦW:"+book.get().getName());
			messageList.messagelist.add("�@��:"+book.get().getWriter());
			messageList.messagelist.add("����:"+book.get().getPrice());
			messageList.messagelist.add("�w�s:"+book.get().getStorage());
		}
		return messageList;
	}

	@Override
	public BookStoreRes updatePrice(String code, String id, int price) {
		if(!code.equalsIgnoreCase("0000")) {
			return new BookStoreRes(null,BookStoreRtnCode.IDPOWER_EXISTED.getMessage());
		}
		if(!StringUtils.hasText(id)) {
			return new BookStoreRes(null,BookStoreRtnCode.ID_REQUIRED.getMessage());
		}
		List<String>message = new ArrayList<>();
		message.add("��s���G:");
		BookStoreRes messageList = new BookStoreRes();
		messageList.setMessagelist(message);
		
		if(bookStoredao.findById(id).isPresent()) {
			Optional<BookStore> book = bookStoredao.findById(id);
			if(price < 0) {
				messageList.messagelist.add(BookStoreRtnCode.PRICE_REQUIRED.getMessage());;
				messageList.messagelist.add("�ثe����:"+book.get().getPrice());
				return messageList;
			}
			messageList.messagelist.add("��s�e������:"+book.get().getPrice());
			messageList.messagelist.add("===========");
			messageList.messagelist.add("��s��:");
			book.get().setPrice(price);
			bookStoredao.save(book.get());
			messageList.messagelist.add("ID:"+book.get().getId());
			messageList.messagelist.add("�ѦW:"+book.get().getName());
			messageList.messagelist.add("�@��:"+book.get().getWriter());
			messageList.messagelist.add("����:"+book.get().getPrice());
			messageList.messagelist.add("�w�s:"+book.get().getStorage());
		}
		return messageList;
	
	}
	
}
