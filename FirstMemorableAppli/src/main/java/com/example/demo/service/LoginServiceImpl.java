package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.LoginDao;
import com.example.demo.entity.UserInfo;

@Service
public class LoginServiceImpl implements LoginService {
	
	private final LoginDao dao;
	
	@Autowired
	public LoginServiceImpl(LoginDao dao) {
		this.dao = dao;
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
	public boolean signIn(UserInfo usersInfo) {
		boolean isLogin = false;
		List<UserInfo> list = dao.signIn(usersInfo);
		String inputMail = usersInfo.getMailaddress();
		String inputPass = usersInfo.getPassword();
		
		for(UserInfo l: list) {
			if(l.getPassword().equals(inputPass) && l.getMailaddress().equals(inputMail)) { 
				return isLogin = true;
			}
		}
		return isLogin;
	}
	
	public boolean checkUnique(String inputWord) {
		boolean isUnique = true;
		List<UserInfo> list = dao.checkUnique();
		
		for(UserInfo l: list) {
			if(l.getMailaddress().equals(inputWord)) {
				isUnique = false; 
			}
		}
		return isUnique;
	}
	
	public UserInfo fetchUserInfo(String mail) {
		UserInfo ui = dao.fetchUserInfo(mail);
		return ui;
	}

	@Override
	public void signUp(UserInfo usersInfo) {
		dao.signUp(usersInfo);
	}
	
	public boolean updateResult(String inputName, int id) {
        boolean isUpdate = dao.updateResult(inputName, id);
		return isUpdate;
	}


}
