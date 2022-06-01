package com.example.demo.service.tweet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.tweet.TweetDao;
import com.example.demo.entity.tweet.Tweet;
@Service
public class TweetServiceImpl implements TweetService {
	private final TweetDao dao;
	
	@Autowired
	public TweetServiceImpl(TweetDao tweetDao) {
		this.dao = tweetDao;
	}

	@Override
	public boolean InsertTweet(String comment, int userId, String img) {
		boolean isInsert = dao.InsertTweet(comment, userId, img);
		return isInsert;
	}

	@Override
	public Tweet displayTweet(int tweetId) {
		Tweet tweet = dao.displayTweet(tweetId);
		return tweet;
	}

}
