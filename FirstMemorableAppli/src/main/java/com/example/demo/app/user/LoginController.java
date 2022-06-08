package com.example.demo.app.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.app.shopping.CartForm;
import com.example.demo.entity.user.UserInfo;
import com.example.demo.service.shopping.ShoppingService;
import com.example.demo.service.user.LoginService;

@Controller
@RequestMapping("/mystyle")
@SessionAttributes(types= {SignInForm.class, SignUpForm.class, UserInfo.class})
public class LoginController {
	
	private final LoginService loginService;
	private final ShoppingService shoppingService;
	
	@Autowired
	HttpSession session;
	
	@Autowired
	public LoginController(LoginService loginService, ShoppingService shoppingService) {
		this.loginService = loginService;
		this.shoppingService = shoppingService;
	}
	
	@GetMapping
	public String top() {
		return "top";
	}
	
	@GetMapping("signIn")
	public String signIn(Model model, SignInForm signInForm) {
		return "signIn";
	}
	
	@GetMapping("signUp")
	public String signUp(Model model , SignUpForm signUpForm) {
		model.addAttribute("signUpForm", signUpForm);
		return "signUp";
	}
	
	@GetMapping("selectImg")
	public String selectImg(Model model, SignUpForm signUpForm) {
		return "selectUserImage";
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("logout")
	@PostMapping("logout")
	public String logout() {
		List<CartForm> list = (List<CartForm>) session.getAttribute("items");
		if(!CollectionUtils.isEmpty(list)) {
			for(CartForm l: list) {
				shoppingService.incrementProducts(l.getSize_l(), l.getSize_xl(),l.getSize_xxl(), l.getPro_id());
			}			
		}	
		session.invalidate();
		return "landing";
	}

	
	@PostMapping("signUp")
	public String isSignUp(
			Model model ,
			@Valid @ModelAttribute SignUpForm signUpForm, 
			BindingResult result,
			RedirectAttributes redirectAttributes
			) {
			if(result.hasErrors() || !signUpForm.getPassword().equals(signUpForm.getRepassword())) {
				model.addAttribute("signUpForm", signUpForm);
				return "signUp";
			} else {
				redirectAttributes.addFlashAttribute("signInComplete", "会員登録に成功しました！ログインを行ってください");
				UserInfo userInfo = new UserInfo();
				userInfo.setUser_name(signUpForm.getUsername());
				userInfo.setPassword(signUpForm.getPassword());
				//メールアドレスがユニークかどうか判定
				if(loginService.checkUnique(signUpForm.getMailaddress())) {
				} else {
					model.addAttribute("signUpForm", signUpForm);
					model.addAttribute("mailError", "そのメールアドレスは、すでに登録済みです。");
					return "signUp";
				}
				userInfo.setMailaddress(signUpForm.getMailaddress());
				userInfo.setAge_id(signUpForm.getAge_id());
				userInfo.setUser_img(signUpForm.getUserImg());
				userInfo.setDatetime(LocalDateTime.now());		
				//入力された情報をInsert
				loginService.signUp(userInfo);
				
				return "redirect:signIn";
			} 	
	}	

}
