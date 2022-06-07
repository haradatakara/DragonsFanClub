package com.example.demo.service.shopping;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dao.shopping.ShoppingDao;
import com.example.demo.entity.shopping.Products;
import com.example.demo.entity.shopping.SizeStock;

@Service
public class ShoppingServiceImpl implements ShoppingService {
	
	private final ShoppingDao dao;
	
	public ShoppingServiceImpl(ShoppingDao dao) {
		this.dao = dao;
	}
	
	public List<Products> displayProducts(int genreId) {
		List<Products> list = dao.displayProducts(genreId);
		return list;
	}

	@Override
	public Products displayDetailProduct(int proId) {
		Products p = dao.displayDetailProduct(proId);
		return p;
	}
	
	public boolean orderComplete(int proId, String payment, LocalDateTime orderday, int userId) {
		boolean isComplete = dao.orderComplete(proId, payment, orderday, userId);
		return isComplete;
	}
	
	public List<SizeStock> countProduct(int proId) {
		List<SizeStock> list = dao.countProduct(proId);
		return list;
	}
	
	public void deleteProducts(int l, int xl, int xxl, int proId) {
		dao.deleteProducts(l, xl, xxl, proId);
	}
	
	public void incrementProducts(int l, int xl, int xxl, int proId) {
		dao.incrementProducts(l, xl, xxl, proId);
	}

}
