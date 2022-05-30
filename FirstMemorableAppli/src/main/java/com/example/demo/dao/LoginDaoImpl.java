package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.UserInfo;

@Repository
public class LoginDaoImpl implements LoginDao {
	
	
	private final JdbcTemplate jdbcTemplate;
	
	public LoginDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;	
	}

	@Override
	public void update(UserInfo usersInfo) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public List<UserInfo> getAll() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
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
	
	public boolean updateResult(String inputName, int id) {
		boolean isUpdate = true;
		System.out.println(inputName);
		String sql = "UPDATE usersInfo SET user_name = ? where user_id = ? ";
		int num =  jdbcTemplate.update(sql, inputName, id);
		if( 0 == num) {
			isUpdate = false;
		}
		
		return isUpdate;
	}
	
	public UserInfo fetchUserInfo(String mail) {
		String sql = "SELECT password, mailaddress, user_name, user_id FROM usersInfo where mailaddress = ?";
		Map<String, Object> resultMap = jdbcTemplate.queryForMap(sql, mail);
		
		UserInfo ui = new UserInfo();
		ui.setMailaddress((String) resultMap.get("mailaddress"));
		ui.setPassword((String) resultMap.get("password"));
		ui.setUser_name((String) resultMap.get("user_name"));
		ui.setUser_id((int) resultMap.get("user_id"));
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

	@Override
	public void signUp(UserInfo usersInfo) {
		String sql = "INSERT INTO usersInfo(user_name, mailaddress, password, age_id, created) VALUES(?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, 
				usersInfo.getUser_name(), usersInfo.getMailaddress(), usersInfo.getPassword(), usersInfo.getAge_id(), usersInfo.getDatetime());
	}

	


}
