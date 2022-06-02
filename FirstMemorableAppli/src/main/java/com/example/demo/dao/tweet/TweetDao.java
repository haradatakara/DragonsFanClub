package com.example.demo.dao.tweet;
import java.util.List;

import com.example.demo.entity.tweet.Tweet;
public interface TweetDao {
	boolean InsertTweet(Tweet tweet);
	List<Tweet> displayTweet();
	boolean CountLike(int userId, int commentId);
	boolean DeleteLike(int userId, int commentId);
	List<Tweet> SearchPushLike(int userId);
}
