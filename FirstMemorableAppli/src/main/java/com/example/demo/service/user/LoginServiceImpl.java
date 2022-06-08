package com.example.demo.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.user.LoginDao;
import com.example.demo.entity.update.UpdateOrder;
import com.example.demo.entity.user.UserInfo;

@Service
public class LoginServiceImpl implements LoginService {
	
	private final LoginDao dao;
	
	@Autowired
	public LoginServiceImpl(LoginDao dao) {
		this.dao = dao;
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
	
	public UserInfo fetchUserInfoMail(String mail) {
		UserInfo ui = dao.fetchUserInfoMail(mail);
		return ui;
	}
	public UserInfo fetchUserInfoId(int id) {
		UserInfo ui = dao.fetchUserInfoId(id);
		return ui;
	}

	@Override
	public void signUp(UserInfo usersInfo) {
		dao.signUp(usersInfo);
	}
	
	public boolean updateResultName(String inputName, int id) {
        boolean isUpdate = dao.updateResultName(inputName, id);
		return isUpdate;
	}
	public boolean updateResultMail(String inputMail, int id) {
		boolean isUpdate = dao.updateResultMail(inputMail, id);
		return isUpdate;
	}
	public boolean updateResultPass(String inputPass, int id) {
		boolean isUpdate = dao.updateResultPass(inputPass, id);
		return isUpdate;
	}
	
	public List<UpdateOrder> confirmOrder(int userId) {
		List<UpdateOrder> list = dao.confirmOrder(userId);
		return list;
	}
	
	public void cancelOrder(int orderId,int l, int xl, int xxl, int productId)  {
		dao.cancelOrder(orderId, l, xl, xxl,productId);
	}
	
	public UpdateOrder detailOrder(int orderId) {
		UpdateOrder uo = dao.detailOrder(orderId);
		return uo;
	}


}
