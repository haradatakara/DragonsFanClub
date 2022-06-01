package com.example.demo.dao.tweet;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.tweet.Tweet;

@Repository
public class TweetDaoImpl implements TweetDao {
	
	private final JdbcTemplate jdbcTemplate;
	
	public TweetDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public boolean InsertTweet(String comment, int userId, String img) {
		boolean isInsert = false;
		String sql = "INSERT INTO comment(comment, created, user_id, user_img) VALUES(?,?,?,?)";
		LocalDateTime dateTime = LocalDateTime.now();
		if(jdbcTemplate.update(sql, comment, dateTime, userId, img) == 1) isInsert = true;
		
		return isInsert;
	}

	@Override
	public Tweet displayTweet(int tweetId) {
		String sql = "SELECT * FROM comment WHERE id = ?";
		Map<String, Object> resultList = jdbcTemplate.queryForMap(sql);
		Tweet tweet = new Tweet();
	    tweet.setTweetId((int)resultList.get("id"));
	    tweet.setComment((String)resultList.get("comment"));
	    tweet.setCreated((LocalDateTime)resultList.get("created"));
	    tweet.setUserId((int)resultList.get("user_id"));
	    tweet.setUserImg((String)resultList.get("user_img"));
		
		return tweet;
	}

}
