package com.example.demo.app.tweet;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.user.UserInfo;
import com.example.demo.service.tweet.TweetService;
import com.example.demo.service.user.LoginService;

@Controller
@RequestMapping("/baseball/landing/twitter")
public class TweetController {
	
	private final TweetService tweetService;
	private final LoginService loginService;
	@Autowired
	HttpSession session;
	
	@Autowired
	public TweetController(TweetService tweetService, LoginService loginService) {
		this.loginService = loginService;
		this.tweetService = tweetService;
	}
	
	@GetMapping
	public String twitter(Model model) { 
		UserInfo user = loginService.fetchUserInfoId((int)session.getAttribute("id"));
		model.addAttribute("form", user);
		return "twitter";
	}

}
