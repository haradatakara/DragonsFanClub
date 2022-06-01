package com.example.demo.dao.tweet;
import com.example.demo.entity.tweet.Tweet;
public interface TweetDao {
	boolean InsertTweet(String comment, int userId, String img);
	Tweet displayTweet(int tweetId);
}
