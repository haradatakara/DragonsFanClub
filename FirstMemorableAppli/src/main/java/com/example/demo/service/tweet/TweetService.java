package com.example.demo.service.tweet;

import java.util.List;

import com.example.demo.entity.tweet.ThreadTable;
import com.example.demo.entity.tweet.Tweet;

public interface TweetService {
	boolean InsertTweet(Tweet tweet, int threadId);
	List<Tweet> displayTweet();
	boolean CountLike(int userId, int commentId);
	boolean DeleteLike(int userId, int commentId);
	List<Tweet> SearchPushLike(int userId);
	List<ThreadTable> displayThred();
	List<Tweet> ditailThred(int threadId);
	

}
