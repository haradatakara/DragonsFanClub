package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.UserInfo;

public interface LoginService {
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
    boolean signIn(UserInfo usersInfo);
    
    boolean checkUnique(String inputWord);
    
    UserInfo fetchUserInfo(String mail);
    
    boolean updateResult(String inputName, int id);

}
