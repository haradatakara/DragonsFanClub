package com.example.demo.app.logined;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.app.user.SignInForm;
import com.example.demo.entity.user.UserInfo;
import com.example.demo.service.user.LoginService;

@Controller
@RequestMapping("/mystyle/landing")
public class LoginedController {

	private final LoginService loginService;

	@Autowired
	HttpSession session;

	@Autowired
	public LoginedController(LoginService loginService) {
		this.loginService = loginService;
	}

	@PostMapping
	public String isSignIn(@Valid @ModelAttribute("signInForm") SignInForm signInForm,
			@ModelAttribute("usersInfo") UserInfo usersInfo, Model model, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (!result.hasErrors()) {
			UserInfo userInfo = new UserInfo();
			userInfo.setMailaddress(signInForm.getMailaddress());
			userInfo.setPassword(signInForm.getPassword());

			boolean isLogin = loginService.signIn(userInfo);
			if (isLogin) {
				UserInfo ui = loginService.fetchUserInfoMail(signInForm.getMailaddress());
				session.setAttribute("userlist", ui);
				session.setAttribute("username", ui.getUser_name());
				session.setAttribute("mailaddress", ui.getMailaddress());
				session.setAttribute("password", ui.getPassword());
				session.setAttribute("id", ui.getUser_id());
				UserInfo user = loginService.fetchUserInfoId((int) session.getAttribute("id"));
				session.setMaxInactiveInterval(1800);
				model.addAttribute("form", user);
				return "landing";
			} else {
				model.addAttribute("title", "ログインページ");
				model.addAttribute("loginError", "ログインに失敗しました");
				return "signIn";
			}
		} else {
			model.addAttribute("title", "ログインページ");
			model.addAttribute("loginError", "ログインに失敗しました");
			return "signIn";
		}
	}

	@GetMapping
	public String landing(Model model) {
		try {
			UserInfo user = loginService.fetchUserInfoId((int) session.getAttribute("id"));
			if((Integer) session.getAttribute("id") == null) {
				model.addAttribute("notlogin", "ログインしていません");
				return "landing";
			}
			model.addAttribute("form", user);
		} catch (Exception e) {}
		return "landing";
	}
}
