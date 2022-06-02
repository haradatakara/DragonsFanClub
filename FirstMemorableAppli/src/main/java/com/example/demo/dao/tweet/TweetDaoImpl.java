package com.example.demo.dao.tweet;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
	public boolean InsertTweet(Tweet tweet) {
		boolean isInsert = false;
		String sql = "INSERT INTO tweet_table(comment, created, user_img, user_id) VALUES(?,?,?,?)";
		if(jdbcTemplate.update(sql, tweet.getComment(), tweet.getCreated(), tweet.getUserImg(), tweet.getUserId()) == 1) {
			isInsert = true;
		}
		
		return isInsert;
	}

	@Override
	public List<Tweet> displayTweet() {
		String sql 
		   = "select tt.comment_id, count(lt.like_id) countnum, comment, created, user_img, tt.user_id "
		   		+ "from tweet_table tt "
		   		+ "left outer join like_table lt on tt.comment_id = lt.comment_id group by tt.comment_id";
		
		List<Tweet> tweets = new ArrayList<>();
		List<Map<String, Object>> res = jdbcTemplate.queryForList(sql);
		
		for(Map<String, Object> r: res) {
			Tweet t = new Tweet();
			t.setComment((String)r.get("comment"));
			t.setUserId((int) r.get("user_id"));
			t.setCreated((LocalDateTime) r.get("created"));
			t.setTweetId((int) r.get("comment_id"));
			t.setUserImg((String) r.get("user_img"));
			t.setLikeNum((Long) r.get("countnum"));
			tweets.add(t);
		}
		
		return tweets;

	}
	
	public boolean CountLike(int userId, int commentId) {
		List<Tweet> list = UniqueCheckLike(commentId);
		boolean isUnique = true;
		for(Tweet l: list) {
			if(l.getUserId() == userId) isUnique = false;
		}
		System.out.println(isUnique);
		boolean isCount = false;
		if(isUnique) {
			String sql = "INSERT INTO like_table(user_id, comment_id) VALUES( ? , ?)";
			isCount = jdbcTemplate.update(sql, userId, commentId) == 0 ? false : true;			
		} 
		
		return isCount;
	}
	
	
	public List<Tweet> UniqueCheckLike(int commentId) {
		String sql = "SELECT user_id FROM like_table WHERE comment_id = ?";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, commentId);
		List<Tweet> tweets = new ArrayList<>();
		for(Map<String, Object> r: list) {
			Tweet t = new Tweet();
			t.setUserId((int) r.get("user_id"));
			tweets.add(t);
		}
		return tweets;
	}
	
	public boolean DeleteLike(int userId, int commentId) {
		String sql = "DELETE FROM like_table WHERE user_id = ? and comment_id = ?";
		boolean isDelete = jdbcTemplate.update(sql, userId, commentId) == 0 ? false: true;
		return isDelete;
	}
	
	public List<Tweet> SearchPushLike(int userId) {
		List<Tweet> tweets = new ArrayList<>();
		String sql = "SELECT comment_id, user_id FROM like_table WHERE user_id = ?";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, userId);
		for(Map<String, Object> r: list) {
			Tweet t = new Tweet();
			t.setUserId((int) r.get("user_id"));
			t.setCommentId((int) r.get("comment_id"));
			tweets.add(t);
		}
		return tweets;
	}


}
