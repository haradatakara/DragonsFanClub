package com.example.demo.app.search;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.search.Shop;
import com.example.demo.entity.user.UserInfo;
import com.example.demo.service.search.SearchService;
import com.example.demo.service.user.LoginService;

@Controller
@RequestMapping("/mystyle/landing")
public class SearchController {
	
	private final LoginService loginService;
	private final SearchService searchService;
	
	@Autowired
	HttpSession session;

	@Autowired
	public SearchController(LoginService loginService, SearchService searchService) {
		this.loginService = loginService;
		
		this.searchService = searchService;
		
	}
	@GetMapping("all_shop")
	public String SearcShops(Model model) {
		List<Shop> list = searchService.serachAllShop();
		model.addAttribute("resultList", list);
		try {
			UserInfo user = loginService.fetchUserInfoId((int) session.getAttribute("id"));
			model.addAttribute("form", user);
		} catch (Exception e) {}
		return "all_shop";
	}

	@GetMapping("part_shop/{shop_id}")
	public String SearcPartShops(Model model, @PathVariable("shop_id") int shopId) {
		Shop s = searchService.searchPartShop(shopId);
		model.addAttribute("resultList", s);
		try {
			UserInfo user = loginService.fetchUserInfoId((int) session.getAttribute("id"));
			model.addAttribute("form", user);
		} catch (Exception e) {}
		return "part_shop";
	}

}
