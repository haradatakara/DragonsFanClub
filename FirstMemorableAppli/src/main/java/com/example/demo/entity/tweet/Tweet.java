package com.example.demo.entity.tweet;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tweet {
	private int tweetId;
	private String comment;
	private LocalDateTime created;
	private int userId;
	private String userImg;
}
