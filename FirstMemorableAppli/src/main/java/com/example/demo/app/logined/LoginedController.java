package com.example.demo.app.logined;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.app.players.PartSearchForm;
import com.example.demo.app.update.UpdateForm;
import com.example.demo.app.user.SignInForm;
import com.example.demo.entity.players.PlayersInfo;
import com.example.demo.entity.search.Shop;
import com.example.demo.entity.user.UserInfo;
import com.example.demo.service.players.PlayerService;
import com.example.demo.service.search.SearchService;
import com.example.demo.service.user.LoginService;

@Controller
@RequestMapping("/baseball/landing")
public class LoginedController {

	private final LoginService loginService;
	private final PlayerService playerService;
	private final SearchService searchService;
	@Autowired
	HttpSession session;
	
	@Autowired
	public LoginedController(LoginService loginService, PlayerService playerService,SearchService searchService) {
		this.loginService = loginService;
		this.playerService = playerService;
		this.searchService = searchService;
	}
	@PostMapping
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
				UserInfo ui = loginService.fetchUserInfoMail(signInForm.getMailaddress());
				session.setAttribute("userlist", ui);
				session.setAttribute("username", ui.getUser_name());
				session.setAttribute("mailaddress", ui.getMailaddress());
				session.setAttribute("password", ui.getPassword());
				session.setAttribute("id", ui.getUser_id());
				UserInfo user = loginService.fetchUserInfoId((int)session.getAttribute("id"));
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
	public String landing(Model model){
		model.addAttribute("form", (UserInfo) session.getAttribute("userlist"));
		UserInfo user = loginService.fetchUserInfoId((int)session.getAttribute("id"));
		model.addAttribute("form", user);
		return "landing";
	}
	
	@GetMapping("all_shop")
	public String SearcShops(Model model) { 
		List<Shop> list = searchService.serachAllShop();
		model.addAttribute("resultList", list);
		model.addAttribute("form", loginService.fetchUserInfoId((int)session.getAttribute("id")));
		return "all_shop";
	}
	
	@GetMapping("part_shop")
	public String SearcPartShops(Model model) { 
		List<PlayersInfo> list = playerService.allSearch();
		model.addAttribute("resultList", list);
		UserInfo user = loginService.fetchUserInfoId((int)session.getAttribute("id"));
		model.addAttribute("form", user);
		return "part_shop";
	}
	
//	@GetMapping("all_search")
//	public String allSearch(Model model) { 
//		List<PlayersInfo> list = playerService.allSearch();
//		model.addAttribute("resultList", list);
//		UserInfo user = loginService.fetchUserInfoId((int)session.getAttribute("id"));
//		model.addAttribute("form", user);
//		return "all_search";
//	}
//	
//	@GetMapping("part_search")
//	public String partSearch(Model model) { 
//		List<PlayersInfo> list = playerService.allSearch();
//		model.addAttribute("resultList", list);
//		UserInfo user = loginService.fetchUserInfoId((int)session.getAttribute("id"));
//		model.addAttribute("form", user);
//		return "part_search";
//	}
	@GetMapping("insert")
	public String insert(Model model) { 
		List<PlayersInfo> list = playerService.allSearch();
		model.addAttribute("resultList", list);
		UserInfo user = loginService.fetchUserInfoId((int)session.getAttribute("id"));
		model.addAttribute("form", user);
		return "insert";
	}
	
	@GetMapping("update")
	public String update(Model model) { 
		UserInfo user = loginService.fetchUserInfoId((int)session.getAttribute("id"));
		model.addAttribute("form", user);
		return "update";
	}
	
	@GetMapping("update_check_name")
	public String updateCheckName(Model model) { 
		UserInfo user = loginService.fetchUserInfoId((int)session.getAttribute("id"));
		model.addAttribute("form", user);
		model.addAttribute("secTitle", "Name");
		model.addAttribute("inputValue", user.getUser_name());
		return "update_check";
	}
	
	@GetMapping("update_check_mail")
	public String updateCheckMail(Model model) { 
		UserInfo user = loginService.fetchUserInfoId((int)session.getAttribute("id"));
		model.addAttribute("form", user);
		model.addAttribute("secTitle", "MailAddress");
		model.addAttribute("inputValue",user.getMailaddress());
		return "update_check";
	}
	
	@GetMapping("update_check_pass")
	public String updateCheckPass(Model model) { 
		UserInfo user = loginService.fetchUserInfoId((int)session.getAttribute("id"));
		model.addAttribute("form", user);
		model.addAttribute("secTitle", "Password");
		model.addAttribute("inputValue", user.getPassword());
		return "update_check";
	}
	
	
	@PostMapping("update") 
	public String updateResName(
			Model model,
			@Valid @ModelAttribute UpdateForm updateForm, 
			BindingResult result,
			RedirectAttributes redirectAttributes
		){
		System.out.println(updateForm.getSecTitle());
		if(result.hasErrors()) {
			model.addAttribute("updateError", "更新失敗です。");
			return "redirect:update_check";
		} else {
			int sessionId =  (int) session.getAttribute("id");
			boolean isUpdate = false;
			
			if(updateForm.getSecTitle().equals("Name")) {
				if(!updateForm.getName().equals("")) {
					isUpdate = loginService.updateResultName(updateForm.getName(), sessionId);
					session.setAttribute("username", updateForm.getName());
				} else {
					model.addAttribute("error", "値を入力してください");
					model.addAttribute("secTitle", "Name");
					model.addAttribute("inputValue",updateForm.getName());
					return "update_check";
				}
			} else if(updateForm.getSecTitle().equals("MailAddress")) {
				//メールアドレスがユニークかどうか判定
				if(loginService.checkUnique(updateForm.getMail_address())) {
					isUpdate = loginService.updateResultMail(updateForm.getMail_address(), sessionId);
					session.setAttribute("mailaddress", updateForm.getMail_address());
				} else {
					model.addAttribute("error", "そのメールアドレスは、すでに登録済みです。");
					model.addAttribute("secTitle", "MailAddress");
					model.addAttribute("inputValue",updateForm.getMail_address());
					return "update_check";
				}
			} else {
				isUpdate = loginService.updateResultPass(updateForm.getPassword(), sessionId);
				if(!updateForm.getPassword().equals("")) {
					isUpdate = loginService.updateResultPass(updateForm.getPassword(), sessionId);
					session.setAttribute("password", updateForm.getPassword());
				} else {
					model.addAttribute("error", "値を入力してください");
					model.addAttribute("secTitle", "Password");
					model.addAttribute("inputValue",updateForm.getPassword());
					return "update_check";
				}
			}

			if(isUpdate) {
				model.addAttribute("isUpdate", isUpdate);
				UserInfo user = loginService.fetchUserInfoId(sessionId);
				session.setAttribute("userlist", user);
				model.addAttribute("form", (UserInfo) session.getAttribute("userlist"));
				return "update";
			} else {
				model.addAttribute("updateError", "更新失敗です。");
				return "update_check";
			}
		}
	}
	@GetMapping("delete")
	public String delete(Model model) { 
		List<PlayersInfo> list = playerService.allSearch();
		model.addAttribute("resultList", list);
		UserInfo user = loginService.fetchUserInfoId((int)session.getAttribute("id"));
		model.addAttribute("form", user);
		return "delete";
	}
	@GetMapping("shopping")
	public String shopping(Model model) { 
		List<PlayersInfo> list = playerService.allSearch();
		model.addAttribute("resultList", list);
		UserInfo user = loginService.fetchUserInfoId((int)session.getAttribute("id"));
		model.addAttribute("form", user);
		return "shopping";
	}
//	@GetMapping("landing/twitter")
//	public String twitter(Model model) { 
//		List<PlayersInfo> list = playerService.allSearch();
//		model.addAttribute("resultList", list);
//		UserInfo user = loginService.fetchUserInfoId((int)session.getAttribute("id"));
//		model.addAttribute("form", user);
//		return "twitter";
//	}
	
	@GetMapping("part_search_result_name")
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
					UserInfo user = loginService.fetchUserInfoId((int)session.getAttribute("id"));
					model.addAttribute("form", user);
					return "part_search_result";
				}
			} else {
				model.addAttribute("list", batterList);
				model.addAttribute("isRecord", "batter");
				UserInfo user = loginService.fetchUserInfoId((int)session.getAttribute("id"));
				model.addAttribute("form", user);
				return "part_search_result";
			}
		}
	}
	@GetMapping("part_search_result_num")
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
				UserInfo user = loginService.fetchUserInfoId((int)session.getAttribute("id"));
				model.addAttribute("form", user);
				return "part_search_result";
			}
		}
	}

}
