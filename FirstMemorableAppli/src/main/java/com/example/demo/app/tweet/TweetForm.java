package com.example.demo.app.tweet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TweetForm {
	private String comment;
	private int userId;
	private String userImg;
}
