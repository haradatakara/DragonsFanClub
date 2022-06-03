package com.example.demo.dao.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.search.Shop;

@Repository
public class SearchDaoImpl implements SearchDao { 
	
	private final JdbcTemplate jdbcTemplate;
	
	public SearchDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<Shop> serachAllShop() {
		String sql = "SELECT * FROM shop";
		List<Shop> list = new ArrayList<>();
		List<Map<String, Object>> res = jdbcTemplate.queryForList(sql);
		for(Map<String, Object> r : res) {
			Shop shop = new Shop();
			shop.setAddress((String) r.get("address"));
			shop.setShop_id((int) r.get("shop_id"));
			shop.setShop_img((String) r.get("shop_img"));
			shop.setDescription((String) r.get("description"));
			shop.setShop_name((String) r.get("shop_name"));
			shop.setTel_number((String) r.get("tel_number"));
			shop.setUrl((String) r.get("url"));
			list.add(shop);
		}
		return list;
	}

}
