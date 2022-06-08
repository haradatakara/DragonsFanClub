package com.example.demo.service.shopping;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.entity.shopping.Products;
import com.example.demo.entity.shopping.SizeStock;

public interface ShoppingService  {
	
	List<Products> displayProducts(int genreId);
	
	Products displayDetailProduct(int proId);
	
	 boolean orderComplete(int proId, String payment, LocalDateTime orderday, int l, int xl, int xxl,String arriveday ,int userId);
	
	List<SizeStock> countProduct(int proId);
	
	void deleteProducts(int l, int xl, int xxl, int proId);
	
	void incrementProducts(int l, int xl, int xxl, int proId);

}
