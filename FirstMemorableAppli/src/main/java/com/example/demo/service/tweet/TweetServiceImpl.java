package com.example.demo.service.tweet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.tweet.TweetDao;
import com.example.demo.entity.tweet.ThreadTable;
import com.example.demo.entity.tweet.Tweet;
@Service
public class TweetServiceImpl implements TweetService {
	private final TweetDao dao;
	
	@Autowired
	public TweetServiceImpl(TweetDao tweetDao) {
		this.dao = tweetDao;
	}

	@Override
	public boolean InsertTweet(Tweet tweet, int threadId) {
		boolean isInsert = dao.InsertTweet(tweet, threadId);
		return isInsert;
	}

	@Override
	public List<Tweet> displayTweet() {
		List<Tweet> tweets = dao.displayTweet();
		return tweets;
	}

	@Override
	public boolean CountLike(int userId, int commentId) {
		boolean isCount = dao.CountLike(userId, commentId);
		return isCount;
	}

	@Override
	public boolean DeleteLike(int userId, int commentId) {
		boolean isDelete = dao.DeleteLike(userId, commentId);
		return isDelete;
	}

	@Override
	public List<Tweet> SearchPushLike(int userId) {
		List<Tweet> list = dao.SearchPushLike(userId);
		return list;
	}
	
	public List<ThreadTable> displayThred() {
		List<ThreadTable> threads = dao.displayThred();
		
		return threads;
	}
	
	public List<Tweet> ditailThred(int threadId) {
		List<Tweet> list = dao.ditailThred(threadId);
		return list;
	}

}
