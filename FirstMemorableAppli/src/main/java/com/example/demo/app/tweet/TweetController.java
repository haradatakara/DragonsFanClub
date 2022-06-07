package com.example.demo.app.tweet;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.app.user.SignInForm;
import com.example.demo.entity.tweet.Tweet;
import com.example.demo.entity.user.UserInfo;
import com.example.demo.service.tweet.TweetService;
import com.example.demo.service.user.LoginService;

@Controller
@RequestMapping("/mystyle/landing/twitter")
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
	public String getTwitter(Model model, SignInForm signInForm) { 
		try {
			if((Integer) session.getAttribute("id") == null) {
				model.addAttribute("notlogin", "ツイート機能を利用するには、ログインをおこなってください");
				model.addAttribute("signInForm", signInForm);
				return "signIn";
			}
			UserInfo user = loginService.fetchUserInfoId((int) session.getAttribute("id"));
			model.addAttribute("form", user);
			List<Tweet> list = tweetService.displayTweet();
			List<Tweet> likes = tweetService.SearchPushLike((int) session.getAttribute("id"));
			for(Tweet t: list) {
				t.setLiked(false);
				for(Tweet l: likes) {
					if(t.getTweetId() == l.getLikeId()) {
						t.setLiked(true);
					}
				}			
			}
			model.addAttribute("list", list);
			//自分がいいねしたコメント(likes.getLikeId())とlistの中のコメント(list.getTweetId())が一致していればblueheartに変更する。出なければgryheartに
			model.addAttribute("name", user.getUser_name());
			model.addAttribute("mail", user.getMailaddress());
			model.addAttribute("image", user.getUser_img());
			model.addAttribute("form", user);
		} catch (Exception e) {}
		
		return "twitter";
	}
	
	
	
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
//            tweet.setUserImg();
            boolean isInsert = tweetService.InsertTweet(tweet);
            List<Tweet> like = tweetService.SearchPushLike((int) session.getAttribute("id"));
    		model.addAttribute("likes", like);
            if(isInsert) {
            	model.addAttribute("success", true);
            	List<Tweet> list = tweetService.displayTweet();
            	List<Tweet> likes = tweetService.SearchPushLike((int) session.getAttribute("id"));
        		for(Tweet t: list) {
        			t.setLiked(false);
        			for(Tweet l: likes) {
        				if(t.getTweetId() == l.getLikeId()) {
        					t.setLiked(true);
        				}
        			}			
        		}
        		model.addAttribute("list", list);
        		UserInfo user = loginService.fetchUserInfoId((int)session.getAttribute("id"));
        		model.addAttribute("name", user.getUser_name());
        		model.addAttribute("mail", user.getMailaddress());
        		model.addAttribute("form", user);
            	return "redirect:/mystyle/landing/twitter";
            } else {
            }
            return "twitter";
		}
	}
	@GetMapping("tweet_complete")
	public String getComTwitter(Model model) {
		List<Tweet> list = tweetService.displayTweet();
		UserInfo user = loginService.fetchUserInfoId((int)session.getAttribute("id"));
		model.addAttribute("form", user);
		List<Tweet> likes = tweetService.SearchPushLike((int) session.getAttribute("id"));
		for(Tweet t: list) {
			t.setLiked(false);
			for(Tweet l: likes) {
				if(t.getTweetId() == l.getLikeId()) {
					t.setLiked(true);
				}
			}			
		}
		model.addAttribute("list", list);
		
		return "twitter";
	}
	
	
	
	@GetMapping("like_count/{commentId}")
	public String getcountLike(
			Model model, 
			@PathVariable("commentId") int commentId
			) {
		List<Tweet> list = tweetService.displayTweet();
		List<Tweet> likes = tweetService.SearchPushLike((int) session.getAttribute("id"));
		for(Tweet t: list) {
			t.setLiked(false);
			for(Tweet l: likes) {
				if(t.getTweetId() == l.getLikeId()) {
					t.setLiked(true);
				}
			}			
		}
		model.addAttribute("list", list);
		UserInfo user = loginService.fetchUserInfoId((int)session.getAttribute("id"));
		model.addAttribute("form", user);
		return "twitter";
	}
	
	@PostMapping("like")
	public String likeCount(
			Model model,
			@ModelAttribute @Validated LikeForm likeForm,
			BindingResult result
			) {
		int userId = (int)session.getAttribute("id");
		//すでにいいねしてないか判定
		boolean isUnique = tweetService.CountLike(userId, likeForm.getTweetId());
		//ツイート全件取得
		List<Tweet> list = tweetService.displayTweet();
		//ログインユーザーがいいねしたコメントの一覧を取得しbooleanでlistに表現
		List<Tweet> likes = tweetService.SearchPushLike((int) session.getAttribute("id"));
		if(isUnique == false) {
			 boolean isDelete = tweetService.DeleteLike(userId, likeForm.getTweetId());
			 List<Tweet> list2 = tweetService.displayTweet();
			 List<Tweet> like = tweetService.SearchPushLike((int) session.getAttribute("id"));
			 for(Tweet t: list2) {
					t.setLiked(false);
					for(Tweet l: like) {
						if(t.getTweetId() == l.getLikeId()) {
							t.setLiked(true);
						}
					}			
			}
			 model.addAttribute("list", list2);
			 return "twitter";
		} else {
			for(Tweet t: list) {
				t.setLiked(false);
				for(Tweet l: likes) {
					if(t.getTweetId() == l.getLikeId()) {
						t.setLiked(true);
					}
				}			
			}
			model.addAttribute("list", list);
		}
		UserInfo user = loginService.fetchUserInfoId((int)session.getAttribute("id"));
		model.addAttribute("form", user);
		return "redirect:/mystyle/landing/twitter";
	}

	@PostMapping("like_count/{commentId}")
	public String postcountLike(
			Model model, 
			@PathVariable("commentId") int commentId
			) {
		int userId = (int)session.getAttribute("id");
		//すでにいいねしてないか判定
		boolean isUnique = tweetService.CountLike(userId, commentId);
		//ツイート全件取得
		List<Tweet> list = tweetService.displayTweet();
		//ログインユーザーがいいねしたコメントの一覧を取得しbooleanでlistに表現
		List<Tweet> likes = tweetService.SearchPushLike((int) session.getAttribute("id"));
		if(isUnique == false) {
			 boolean isDelete = tweetService.DeleteLike(userId, commentId);
			 List<Tweet> list2 = tweetService.displayTweet();
			 List<Tweet> like = tweetService.SearchPushLike((int) session.getAttribute("id"));
			 for(Tweet t: list2) {
					t.setLiked(false);
					for(Tweet l: like) {
						if(t.getTweetId() == l.getLikeId()) {
							t.setLiked(true);
						}
					}			
			}
			 model.addAttribute("list", list2);
			 return "redirect:/mystyle/landing/twitter";
		} else {
			for(Tweet t: list) {
				t.setLiked(false);
				for(Tweet l: likes) {
					if(t.getTweetId() == l.getLikeId()) {
						t.setLiked(true);
					}
				}			
			}
			model.addAttribute("list", list);
		}
		UserInfo user = loginService.fetchUserInfoId((int)session.getAttribute("id"));
		model.addAttribute("form", user);
		return "redirect:/mystyle/landing/twitter";
	}

}
