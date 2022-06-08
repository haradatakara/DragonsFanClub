package com.example.demo.dao.user;

import java.util.List;

import com.example.demo.entity.update.UpdateOrder;
import com.example.demo.entity.user.UserInfo;

public interface LoginDao {
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
	
	UserInfo fetchUserInfoMail(String mail);
	UserInfo fetchUserInfoId(int id);
	
	boolean updateResultName(String inputName, int id);
	boolean updateResultMail(String inputMail, int id);
	boolean updateResultPass(String inputPass, int id);
	
	List<UpdateOrder> confirmOrder(int userId);
	void cancelOrder(int orderId,int l, int xl, int xxl, int productId);
	
	UpdateOrder detailOrder(int orderId);
	
	
}
