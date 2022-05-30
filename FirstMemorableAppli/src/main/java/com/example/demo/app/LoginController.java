package com.example.demo.app;

import java.time.LocalDateTime;
import java.util.List;

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

import com.example.demo.app.players.PartSearchForm;
import com.example.demo.app.update.UpdateForm;
import com.example.demo.entity.UserInfo;
import com.example.demo.entity.players.PlayersInfo;
import com.example.demo.service.LoginService;
import com.example.demo.service.PlayerService;

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
	
	@PostMapping("/landing") 
	public String isSignIn(
			@Valid @ModelAttribute("signInForm") SignInForm signInForm, 
			@ModelAttribute("usersInfo") UserInfo usersInfo, 
			Model model,
			BindingResult result,
			RedirectAttributes redirectAttributes
			) {
		if(!result.hasErrors()) {
			UserInfo userInfo = new UserInfo();
			userInfo.setMailaddress(signInForm.getMailaddress());
			userInfo.setPassword(signInForm.getPassword());
				
			boolean isLogin = loginService.signIn(userInfo);
			if(isLogin) {
				UserInfo ui = loginService.fetchUserInfo(signInForm.getMailaddress());
				session.setAttribute("userlist", ui);
				session.setAttribute("username", ui.getUser_name());
				session.setAttribute("mailaddress", ui.getMailaddress());
				session.setAttribute("password", ui.getPassword());
				session.setAttribute("id", ui.getUser_id());
				
				model.addAttribute("form", (UserInfo) session.getAttribute("userlist"));
				
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
	
	@GetMapping("/landing") 
	public String landing(Model model){
		model.addAttribute("form", (UserInfo) session.getAttribute("userlist"));
		return "landing";
	}
	
	
	
	
	
	
	@GetMapping("landing/all_search")
	public String allSearch(Model model) { 
		List<PlayersInfo> list = playerService.allSearch();
		model.addAttribute("resultList", list);
		model.addAttribute("form", (UserInfo) session.getAttribute("userlist"));
		return "all_search";
	}
	
	@GetMapping("landing/part_search")
	public String partSearch(Model model) { 
		List<PlayersInfo> list = playerService.allSearch();
		model.addAttribute("resultList", list);
		model.addAttribute("form", (UserInfo) session.getAttribute("userlist"));
		return "part_search";
	}
	@GetMapping({"landing/insert"})
	public String insert(Model model) { 
		List<PlayersInfo> list = playerService.allSearch();
		model.addAttribute("resultList", list);
		model.addAttribute("form", (UserInfo) session.getAttribute("userlist"));
		return "insert";
	}
	
	@GetMapping("landing/update")
	public String update(Model model) { 
		model.addAttribute("form", (UserInfo) session.getAttribute("userlist"));
		return "update";
	}
	
	@GetMapping("landing/update_check_name")
	public String updateCheckName(Model model) { 
		model.addAttribute("form", (UserInfo) session.getAttribute("userlist"));
		model.addAttribute("secTitle", "名前");
		model.addAttribute("inputValue", (String) session.getAttribute("username"));
		return "update_check";
	}
	@GetMapping("landing/update_check_mail")
	public String updateCheckMail(Model model) { 
		model.addAttribute("form", (UserInfo) session.getAttribute("userlist"));
		model.addAttribute("secTitle", "メールアドレス");
		model.addAttribute("inputValue", (String) session.getAttribute("mailaddress"));
		return "update_check";
	}
	@GetMapping("landing/update_check_pass")
	public String updateCheckPass(Model model) { 
		model.addAttribute("form", (UserInfo) session.getAttribute("userlist"));
		model.addAttribute("secTitle", "パスワード");
		model.addAttribute("inputValue", (String) session.getAttribute("password"));
		return "update_check";
	}
	
	@GetMapping("landing/delete")
	public String delete(Model model) { 
		List<PlayersInfo> list = playerService.allSearch();
		model.addAttribute("resultList", list);
		model.addAttribute("form", (UserInfo) session.getAttribute("userlist"));
		return "delete";
	}
	
	@PostMapping("landing/update") 
	public String updateResName(
			Model model,
			@Valid @ModelAttribute UpdateForm updateForm, 
			BindingResult result,
			RedirectAttributes redirectAttributes
		){
		
		if(result.hasErrors()) {
			model.addAttribute("updateError", "更新失敗です。");
			return "update_check";
		} else {
			UserInfo ui = new UserInfo();
			ui.setUser_name(updateForm.getSectionCon());
			int sessionid =  (int) session.getAttribute("id");
			boolean isUpdate = loginService.updateResult(ui.getUser_name(), sessionid);
			if(isUpdate) {
				model.addAttribute("isUpdate", isUpdate);
				model.addAttribute("form", (UserInfo) session.getAttribute("userlist"));
				return "update";
			} else {
				model.addAttribute("updateError", "更新失敗です。");
				return "update_check";
			}
		}
	}
	
	@GetMapping("landing/shopping")
	public String shopping(Model model) { 
		List<PlayersInfo> list = playerService.allSearch();
		model.addAttribute("resultList", list);
		model.addAttribute("form", (UserInfo) session.getAttribute("userlist"));
		return "shopping";
	}
	@GetMapping("landing/twitter")
	public String twitter(Model model) { 
		List<PlayersInfo> list = playerService.allSearch();
		model.addAttribute("resultList", list);
		model.addAttribute("form", (UserInfo) session.getAttribute("userlist"));
		return "twitter";
	}
	
	@GetMapping("landing/part_search_result_name")
	public String nameSearchResult(
			Model model,
			@Valid @ModelAttribute PartSearchForm partSearchForm, 
			BindingResult result,
			RedirectAttributes redirectAttributes
			) {
		List<PlayersInfo>batterList = null;
		List<PlayersInfo>peatcherList = null;
		
		if(result.hasErrors()) {
			model.addAttribute("searchError", "検索に失敗しました");
			return "part_search";
		} else {
			String inputName = partSearchForm.getName();
			batterList = playerService.batterNameSearch(inputName);	
			if(batterList.size() == 0) {
				peatcherList = playerService.peatcherNameSearch(inputName);
				if(peatcherList.size() == 0) {
					model.addAttribute("searchError", "検索した名前に該当する選手は見つかりませんでした");
					return "part_search";					
				} else {
					model.addAttribute("list", peatcherList);
					model.addAttribute("isRecord", "peatcher");
					model.addAttribute("form", (UserInfo) session.getAttribute("userlist"));
					return "part_search_result";
				}
			} else {
				model.addAttribute("list", batterList);
				model.addAttribute("isRecord", "batter");
				model.addAttribute("form", (UserInfo) session.getAttribute("userlist"));
				return "part_search_result";
			}
		}
	}
	@GetMapping("landing/part_search_result_num")
	public String uniSearchResult(
			Model model,
			@Valid @ModelAttribute PartSearchForm partSearchForm, 
			BindingResult result,
			RedirectAttributes redirectAttributes
			) {
		List<PlayersInfo>list = null;
	
		if(result.hasErrors()) {
			model.addAttribute("searchError", "検索に失敗しました");
			return "part_search";
		} else {
			int inputUni = partSearchForm.getUniformNum();
			list = playerService.batterUniSearch(inputUni);
			if(list.size() == 0) {
				model.addAttribute("searchError", "検索した名前に該当する選手は見つかりませんでした");
				return "part_search";
			} else {
				model.addAttribute("list", list);	
				model.addAttribute("form", (UserInfo) session.getAttribute("userlist"));
				return "part_search_result";
			}
		}
	}
	
	
	

}
