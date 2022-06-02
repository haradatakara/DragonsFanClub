package com.example.demo.app.tweet;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.tweet.Tweet;
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
	@PostMapping
	public String getTwitter(Model model) { 
		UserInfo user = loginService.fetchUserInfoId((int)session.getAttribute("id"));
		List<Tweet> list = tweetService.displayTweet();
		model.addAttribute("list", list);
		model.addAttribute("name", user.getUser_name());
		model.addAttribute("mail", user.getMailaddress());
		model.addAttribute("form", user);
		List<Tweet> likes = tweetService.SearchPushLike((int) session.getAttribute("id"));
		System.out.println(likes);
		for(Tweet l: likes) {
			System.out.println(l.getCommentId());
		}
		return "twitter";
	}
//	@PostMapping({""})
//	public String postTwitter(Model model) { 
//		UserInfo user = loginService.fetchUserInfoId((int)session.getAttribute("id"));
//		List<Tweet> list = tweetService.displayTweet();
//		model.addAttribute("list", list);
//		model.addAttribute("name", user.getUser_name());
//		model.addAttribute("mail", user.getMailaddress());
//		model.addAttribute("form", user);
//		return "twitter";
//	}
	
	@PostMapping("tweet_complete")
	public String twitter(
			Model model, 
			@Valid @ModelAttribute TweetForm tweetForm, 
			BindingResult result
		) {
		boolean isError = false;
		if(result.hasErrors()) {
			isError = true;
			model.addAttribute("error", isError);
			return "twitter";
		} else {
			Tweet tweet = new Tweet();
			tweet.setComment(tweetForm.getComment());
			tweet.setCreated(LocalDateTime.now());
			tweet.setUserId((int)session.getAttribute("id"));
            tweet.setUserImg("/img/players/img01.jpg");
            boolean isInsert = tweetService.InsertTweet(tweet);
            if(isInsert) {
            	model.addAttribute("success", true);
            	List<Tweet> list = tweetService.displayTweet();
        		model.addAttribute("list", list);
        		UserInfo user = loginService.fetchUserInfoId((int)session.getAttribute("id"));
        		model.addAttribute("name", user.getUser_name());
        		model.addAttribute("mail", user.getMailaddress());
        		model.addAttribute("form", user);
            	return "twitter";
            } else {
            }
//			UserInfo user = loginService.fetchUserInfoId((int)session.getAttribute("id"));
            return "twitter";
		}
	}
	@GetMapping("tweet_complete")
	public String getComTwitter(Model model) {
		List<Tweet> list = tweetService.displayTweet();
		model.addAttribute("list", list);
		model.addAttribute("name", session.getAttribute("username"));
		model.addAttribute("mail", session.getAttribute("mailaddress"));
		UserInfo user = loginService.fetchUserInfoId((int)session.getAttribute("id"));
		model.addAttribute("form", user);
		return "twitter";
	}
	@GetMapping("like_count/{commentId}")
	public String getcountLike(
			Model model, 
			@PathVariable("commentId") int commentId
			) {
		List<Tweet> list = tweetService.displayTweet();
		model.addAttribute("list", list);
		model.addAttribute("name", session.getAttribute("username"));
		model.addAttribute("mail", session.getAttribute("mailaddress"));
		UserInfo user = loginService.fetchUserInfoId((int)session.getAttribute("id"));
		model.addAttribute("form", user);
		return "twitter";
	}

	@PostMapping("like_count/{commentId}")
	public String postcountLike(
			Model model, 
			@PathVariable("commentId") int commentId
			) {
		int userId = (int)session.getAttribute("id");
		boolean isUnique = tweetService.CountLike(userId, commentId);
		List<Tweet> list1 = tweetService.displayTweet();
		model.addAttribute("list", list1);
	    model.addAttribute("name", session.getAttribute("username"));
	    model.addAttribute("mail", session.getAttribute("mailaddress"));
	    model.addAttribute("image", "url(/img/twitter/heart_blue.jpeg) left no-repeat");
	    UserInfo user = loginService.fetchUserInfoId((int)session.getAttribute("id"));
		model.addAttribute("form", user);
		if(isUnique == false) {
			 boolean isDelete = tweetService.DeleteLike(userId, commentId);
			 System.out.println(isDelete + "12");
			 List<Tweet> list2 = tweetService.displayTweet();
			 System.out.println(list2.get(1).getLikeNum());
			 model.addAttribute("list", list2);
			 model.addAttribute("image", "url(/img/twitter/heart_blue.jpeg) left no-repeat");
			 return "twitter";
		} 
		return "twitter";
	}

}
