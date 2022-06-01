package com.example.demo.service.user;

import java.util.List;

import com.example.demo.entity.user.UserInfo;

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
    
    UserInfo fetchUserInfoMail(String mail);
    UserInfo fetchUserInfoId(int id);
    
    boolean updateResultName(String inputName, int id);
    boolean updateResultMail(String inputMail, int id);
    boolean updateResultPass(String inputPass, int id);
}
