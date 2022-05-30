package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.UserInfo;

public interface LoginDao {
	void update(UserInfo usersInfo);
	List<UserInfo> getAll();
	/**
	 * ユーザー登録をするメソッド
	 */
	void signUp(UserInfo usersInfo);
	/**
	 * ログインをするメソッド
	 * @return 
	 */
	List<UserInfo> signIn(UserInfo usersInfo);
	
	List<UserInfo> checkUnique();
	
	UserInfo fetchUserInfo(String mail);
	
	boolean updateResult(String inputName, int id);
	
}
