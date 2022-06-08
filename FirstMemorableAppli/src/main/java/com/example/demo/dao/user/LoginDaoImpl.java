package com.example.demo.dao.user;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.update.UpdateOrder;
import com.example.demo.entity.user.UserInfo;

@Repository
public class LoginDaoImpl implements LoginDao {
	
	
	private final JdbcTemplate jdbcTemplate;
	
	public LoginDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;	
	}

	@Override
	public List<UserInfo> signIn(UserInfo usersInfo) {
		String sql = "SELECT password, mailaddress, user_name FROM usersInfo";
		
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
		
		List<UserInfo> list = new ArrayList<>();
		
		for(Map<String, Object> res: resultList) {
			UserInfo ui = new UserInfo();
			ui.setMailaddress((String) res.get("mailaddress"));
			ui.setPassword((String) res.get("password"));
			ui.setUser_name((String) res.get("user_name"));
			list.add(ui);
		}
		return list;
	}
	
	@Override
	public void signUp(UserInfo usersInfo) {
		String sql = "INSERT INTO usersInfo(user_name, mailaddress, password, age_id, created, user_img) VALUES(?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, 
				usersInfo.getUser_name(), usersInfo.getMailaddress(), usersInfo.getPassword(), usersInfo.getAge_id(), usersInfo.getDatetime(), usersInfo.getUser_img());
	}
	
	public boolean updateResultName(String inputName, int id) {
		boolean isUpdate = true;
		String sql = "UPDATE usersInfo SET user_name = ? where user_id = ? ";
		int num =  jdbcTemplate.update(sql, inputName, id);
		if( 0 == num) isUpdate = false;
		
		return isUpdate;
	}
	public boolean updateResultMail(String inputMail, int id) {
		boolean isUpdate = true;
		String sql = "UPDATE usersInfo SET mailaddress = ? where user_id = ? ";
		int num =  jdbcTemplate.update(sql, inputMail, id);
		if( 0 == num) isUpdate = false;
		
		return isUpdate;
	}
	public boolean updateResultPass(String inputPass, int id) {
		boolean isUpdate = true;
		String sql = "UPDATE usersInfo SET password = ? where user_id = ? ";
		int num =  jdbcTemplate.update(sql, inputPass, id);
		if( 0 == num) isUpdate = false;
		
		return isUpdate;
	}
	
	public UserInfo fetchUserInfoMail(String mail) {
		String sql = "SELECT password, mailaddress, user_name, user_id, user_img FROM usersInfo where mailaddress = ?";
		Map<String, Object> resultMap = jdbcTemplate.queryForMap(sql, mail);
		UserInfo ui = new UserInfo();
		
		ui.setMailaddress((String) resultMap.get("mailaddress"));
		ui.setPassword((String) resultMap.get("password"));
		ui.setUser_name((String) resultMap.get("user_name"));
		ui.setUser_id((int) resultMap.get("user_id"));
		ui.setUser_img((String) resultMap.get("user_img"));
//		ui.setUser_img((int) resultMap.get("user_img"));
		
		return ui;
	}
	
	public UserInfo fetchUserInfoId(int id) {
		String sql = "SELECT password, mailaddress, user_name, user_id, user_img FROM usersInfo where user_id = ?";
		Map<String, Object> resultMap = jdbcTemplate.queryForMap(sql, id);
		UserInfo ui = new UserInfo();
		
		ui.setMailaddress((String) resultMap.get("mailaddress"));
		ui.setPassword((String) resultMap.get("password"));
		ui.setUser_name((String) resultMap.get("user_name"));
		ui.setUser_id((int) resultMap.get("user_id"));
		ui.setUser_img((String) resultMap.get("user_img"));
		
		return ui;
	}
	public List<UserInfo> checkUnique() {
		String sql = "SELECT mailaddress FROM usersInfo";
		
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
		List<UserInfo> list = new ArrayList<>();
		
		for(Map<String, Object> res: resultList) {
			UserInfo ui = new UserInfo();
			ui.setMailaddress((String) res.get("mailaddress"));
			
			list.add(ui);
		}
		
		return list;
	}
	
	public List<UpdateOrder> confirmOrder(int userId) {
		String sql = "select order_id, p.product_id, name, payment, orderday, l_size, xl_size, xxl_size, img  from order_master om inner join products p on p.product_id = om.product_id where user_id = ?";
		
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, userId);
		List<UpdateOrder> list = new ArrayList<>();
		
		for(Map<String, Object> res: resultList) {
			UpdateOrder uo = new UpdateOrder();
			uo.setOrder_id((int) res.get("order_id"));
			uo.setPro_name((String) res.get("name"));
			uo.setPayment((String) res.get("payment"));
			uo.setOrderday((Timestamp) res.get("orderday"));
			uo.setImg((String) res.get("img"));
			uo.setProduct_id((int) res.get("product_id"));
			uo.setX_size((int) res.get("l_size"));
			uo.setXl_size((int) res.get("xl_size"));
			uo.setXxl_size((int) res.get("xxl_size"));
			list.add(uo);
		}
		
		return list;
	}
	
	public void cancelOrder(int orderId,int l, int xl, int xxl, int productId) {
		String DELETE = "DELETE FROM order_master WHERE order_id = ?";
		jdbcTemplate.update(DELETE, orderId);
		String RETURN_STOCK = "UPDATE size_stock SET l_size = l_size + ? , xl_size = xl_size + ?, xxl_size = xxl_size + ? WHERE product_id = ?";
		jdbcTemplate.update(RETURN_STOCK, l, xl, xxl, productId);
	}
	
	public UpdateOrder detailOrder(int orderId) {
		String sql = "select order_id, p.product_id, name, payment, orderday, l_size, xl_size, xxl_size, img, arriveday from order_master om "
				+ "inner join products p on p.product_id = om.product_id where order_id = ?";
		
		UpdateOrder uo = new UpdateOrder();
		Map<String, Object> res = jdbcTemplate.queryForMap(sql, orderId);
		uo.setOrder_id((int) res.get("order_id"));
		uo.setPro_name((String) res.get("name"));
		uo.setPayment((String) res.get("payment"));
		uo.setOrderday((Timestamp) res.get("orderday"));
		uo.setImg((String) res.get("img"));
		uo.setProduct_id((int) res.get("product_id"));
		uo.setX_size((int) res.get("l_size"));
		uo.setXl_size((int) res.get("xl_size"));
		uo.setXxl_size((int) res.get("xxl_size"));
		uo.setArriveday((String) res.get("arriveday"));
		
		return uo;
		
	}

	
	
	

	


}
