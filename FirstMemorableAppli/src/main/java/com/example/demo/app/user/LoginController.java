package com.example.demo.app.user;

import java.time.LocalDateTime;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.user.UserInfo;
import com.example.demo.service.players.PlayerService;
import com.example.demo.service.user.LoginService;

@Controller
@RequestMapping("/baseball")
@SessionAttributes(types= {SignInForm.class, SignUpForm.class, UserInfo.class})
public class LoginController {
	
	private final LoginService loginService;
	private final PlayerService playerService;
	
	@Autowired
	HttpSession session;
	
	@Autowired
	public LoginController(LoginService loginService,PlayerService playerService) {
		this.loginService = loginService;
		this.playerService = playerService;
	}
	@ModelAttribute("signInForm")
	public SignInForm setUpSignInForm() {
		return new SignInForm();
	}
	
	@ModelAttribute("signUpForm")
	public SignUpForm setUpSignUpForm() {
		return new SignUpForm();
	}
	
	@ModelAttribute("usersInfo")
	public UserInfo setUpUserInfo() {
		return new UserInfo();
	}
	
	@GetMapping("/signIn")
	public String signIn(Model model, SignInForm signInForm) {
		model.addAttribute("title", "ログインページ");
		return "signIn";
	}
	
	@GetMapping("/signUp")
	public String signUp(Model model, SignUpForm signUpForm) {
		model.addAttribute("title", "会員登録ページ");
		return "signUp";
	}
	
	@PostMapping("/signUp")
	public String isSignUp(
			Model model ,
			@Valid @ModelAttribute SignUpForm signUpForm, 
			BindingResult result,
			RedirectAttributes redirectAttributes
			) {
			if(result.hasErrors() || !signUpForm.getPassword().equals(signUpForm.getRepassword())) {
				model.addAttribute("signUpForm", signUpForm);
				model.addAttribute("title", "会員登録ページ");
				return "signUp";
			} else {
				model.addAttribute("title", "ログインページ");
				redirectAttributes.addFlashAttribute("signInComplete", "会員登録に成功しました！ログインを行ってください");
				UserInfo userInfo = new UserInfo();
				userInfo.setUser_name(signUpForm.getUsername());
				userInfo.setPassword(signUpForm.getPassword());
				
				//メールアドレスがユニークかどうか判定
				if(loginService.checkUnique(signUpForm.getMailaddress())) {
				} else {
					model.addAttribute("signUpForm", signUpForm);
					model.addAttribute("title", "会員登録ページ");
					model.addAttribute("mailError", "そのメールアドレスは、すでに登録済みです。");
					return "signUp";
				}
				userInfo.setMailaddress(signUpForm.getMailaddress());
				userInfo.setAge_id(signUpForm.getAge_id());
				userInfo.setDatetime(LocalDateTime.now());		
				loginService.signUp(userInfo);	
				return "redirect:signIn";
			} 	
	}	

}
