package com.example.demo.dao.tweet;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.tweet.ThreadTable;
import com.example.demo.entity.tweet.Tweet;

@Repository
public class TweetDaoImpl implements TweetDao {
	
	private final JdbcTemplate jdbcTemplate;
	
	public TweetDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public boolean InsertTweet(Tweet tweet, int threadId) {
		boolean isInsert = false;
		String sql = "INSERT INTO tweet_table(comment, created, user_id, thread_id) VALUES(?,?,?,?)";
		if(jdbcTemplate.update(sql, tweet.getComment(), tweet.getCreated(), tweet.getUserId(), threadId) == 1) {
			isInsert = true;
		}
		
		return isInsert;
	}

	@Override
	public List<Tweet> displayTweet() {
		String sql 
		   = "select tt.comment_id, count(lt.like_id) countnum, comment, tt.created, ui.user_img, tt.user_id, ui.user_name, ui.mailaddress "
		   		+ "from tweet_table tt "
		   		+ "left outer join like_table lt on tt.comment_id = lt.comment_id left outer join usersInfo ui on ui.user_id = tt.user_id group by tt.comment_id order by tt.created desc";
		
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
			t.setUsername((String) r.get("user_name"));
		    t.setMailaddress((String) r.get("mailaddress"));
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
		String sql = "SELECT comment_id FROM like_table WHERE user_id = ?";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, userId);
		for(Map<String, Object> r: list) {
			Tweet t = new Tweet();
			t.setLikeId((int) r.get("comment_id"));
			tweets.add(t);
		}
		return tweets;
	}
	
	public List<ThreadTable> displayThred() {
		List<ThreadTable> threads = new ArrayList<>();
		String sql = "SELECT * FROM thread_table";
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		for(Map<String, Object> r: list) {
			ThreadTable t = new ThreadTable();
			t.setThread_id((int) r.get("thread_id"));
			t.setCreate_user((String) r.get("create_user_name"));
			t.setTitle((String) r.get("title"));
			t.setCreated((LocalDateTime) r.get("created"));
			threads.add(t);
		}
		return threads;
	}
	public List<Tweet> ditailThred(int threadId) {
		String sql 
		   = "select tt.comment_id, count(lt.like_id) countnum, comment, tt.created, ui.user_img, tt.user_id, ui.user_name, ui.mailaddress, tt.thread_id "
		   		+ "from tweet_table tt "
		   		+ "left outer join like_table lt on tt.comment_id = lt.comment_id left outer join usersInfo ui on ui.user_id = tt.user_id "
		   		+ "where thread_id = ? group by tt.comment_id order by tt.created desc "
		   		;
		
		List<Tweet> tweets = new ArrayList<>();
		List<Map<String, Object>> res = jdbcTemplate.queryForList(sql, threadId);
		
		for(Map<String, Object> r: res) {
			Tweet t = new Tweet();
			t.setComment((String)r.get("comment"));
			t.setUserId((int) r.get("user_id"));
			t.setCreated((LocalDateTime) r.get("created"));
			t.setTweetId((int) r.get("comment_id"));
			t.setUserImg((String) r.get("user_img"));
			t.setLikeNum((Long) r.get("countnum"));
			t.setUsername((String) r.get("user_name"));
		    t.setMailaddress((String) r.get("mailaddress"));
		    t.setThread_id((int) r.get("thread_id"));
			tweets.add(t);
		}
		
		return tweets;
	}


}
