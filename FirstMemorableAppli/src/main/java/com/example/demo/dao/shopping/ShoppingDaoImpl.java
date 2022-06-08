package com.example.demo.dao.shopping;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.shopping.Products;
import com.example.demo.entity.shopping.SizeStock;

@Repository
public class ShoppingDaoImpl implements ShoppingDao {

	private final JdbcTemplate jdbcTemplate;

	public ShoppingDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	String sql = "select product_id, ss.size_id, count(*) from size_stock2 ss  left outer join size_correct sc on ss.size_id = sc.size_id group by ss.size_id, product_id ";

	public List<Products> displayProducts(int genreId) {

		List<Products> list = new ArrayList<>();
		String sql = "select product_id, name,price, img from products p "
				+ "inner join  product_genre pg on pg.genre_id = p.genre_id where p.genre_id = ?;";

		List<Map<String, Object>> rs = jdbcTemplate.queryForList(sql, genreId);

		for (Map<String, Object> r : rs) {
			Products p = new Products();
			p.setPro_id((int) r.get("product_id"));
			p.setPro_name((String) r.get("name"));
			p.setPrice((int) r.get("price"));
			p.setPro_img((String) r.get("img"));

			list.add(p);
		}

		return list;
	}

	public Products displayDetailProduct(int proId) {
		String sql = "select p.product_id, p.name, p.price, p.img, s.shop_name, address, p.style_id, ps.style_name, p.genre_id, pg.genre_name, l_size, xl_size, xxl_size from products p "
				+ "inner join size_stock ss on p.product_id = ss.product_id "
				+ "inner join shop s on s.shop_id = p.shop_id "
				+ "inner join product_style ps on ps.style_id = p.style_id "
				+ "inner join  product_genre pg on pg.genre_id = p.genre_id where p.product_id = ?;";

		Map<String, Object> rs = jdbcTemplate.queryForMap(sql, proId);
		Products p = new Products();
		p.setPro_id((int) rs.get("product_id"));
		p.setPro_name((String) rs.get("name"));
		p.setPrice((int) rs.get("price"));
		p.setPro_img((String) rs.get("img"));
		p.setShop_name((String) rs.get("shop_name"));
		p.setShop_address((String) rs.get("address"));
		p.setStyle_id((int) rs.get("style_id"));
		p.setStyle_name((String) rs.get("style_name"));
		p.setGenre_id((int) rs.get("genre_id"));
		p.setGenre_name((String) rs.get("genre_name"));
		p.setL_size((int) rs.get("l_size"));
		p.setXl_size((int) rs.get("xl_size"));
		p.setXxl_size((int) rs.get("xxl_size"));

		return p;
	}

	public List<SizeStock> countProduct(int proId) {
		String sql = "select p.product_id, ss.size_id, count(*) count from products p "
				+ "inner join size_stock2 ss on p.product_id = ss.product_id "
				+ "where p.product_id = ? group  by ss.size_id, product_id ;";

     	List<Map<String, Object>> rs = jdbcTemplate.queryForList(sql, proId);
        List<Products> list = new ArrayList<>();
        List<SizeStock> list2 = new ArrayList<>();
        
		for (Map<String, Object> r : rs) {
			SizeStock ss = new SizeStock();
			ss.setPro_id((int) r.get("product_id"));
			ss.setSize_count((Long) r.get("count"));
			list2.add(ss);
		}
		
		return list2;
	}

	public boolean orderComplete(int proId, String payment, LocalDateTime orderday, int l, int xl, int xxl,String arriveday ,int userId) {
		boolean isComplete = true;
		String sql = "INSERT INTO order_master(product_id, payment, orderday, l_size, xl_size, xxl_size, arriveday ,user_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		System.out.println(isComplete);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		if (jdbcTemplate.update(sql, proId, payment, timestamp,l, xl, xxl, arriveday,userId) == 0) {
			isComplete = false;
		}

		return isComplete;
	}

	public void deleteProducts(int l, int xl, int xxl, int proId) {
		String sql = "UPDATE size_stock SET l_size = l_size - ? , xl_size = xl_size - ?, xxl_size = xxl_size - ? WHERE product_id = ?";
		jdbcTemplate.update(sql, l, xl, xxl, proId);
	}
	
	public void incrementProducts(int l, int xl, int xxl, int proId) {
		String sql = "UPDATE size_stock SET l_size = l_size + ? , xl_size = xl_size + ?, xxl_size = xxl_size + ? WHERE product_id = ?";
		jdbcTemplate.update(sql, l, xl, xxl, proId);
	}

	
	
	
	
	
	
	
	
	
	
	

}
