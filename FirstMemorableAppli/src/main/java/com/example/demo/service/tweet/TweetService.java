package com.example.demo.service.tweet;

import com.example.demo.entity.tweet.Tweet;

public interface TweetService {
	boolean InsertTweet(String comment, int userId, String img);
	Tweet displayTweet(int tweetId);

}
