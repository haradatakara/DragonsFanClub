package com.example.demo.dao.search;

import java.util.List;

import com.example.demo.entity.search.Shop;

public interface SearchDao {
	List<Shop> serachAllShop();
	
	Shop searchPartShop(int shopId);

}
