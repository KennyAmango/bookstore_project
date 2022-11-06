package com.example.bookstore_project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookstore_project.entity.BookStore;

public interface BookStoreDao extends JpaRepository<BookStore, String>{
	
    public List<BookStore> findAllByCategory(String category);
	
	public List<BookStore> findByCategoryContaining(String category);
	
	public List<BookStore> findTop5ByOrderBySalesDesc();
	
	public List<BookStore> findByName(String name);
	
	public List<BookStore> findByWriter(String writer);
	
	
	
}
