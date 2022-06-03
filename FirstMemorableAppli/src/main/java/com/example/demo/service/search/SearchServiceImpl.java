package com.example.demo.service.search;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dao.search.SearchDao;
import com.example.demo.entity.search.Shop;
@Service
public class SearchServiceImpl implements SearchService {
	
	private final SearchDao dao;
	
	public SearchServiceImpl(SearchDao dao) {
		this.dao = dao;
	}

	@Override
	public List<Shop> serachAllShop() {
		List<Shop>  list = dao.serachAllShop();
		return list;
	}

}
