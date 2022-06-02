package com.example.demo.app.tweet;

import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TweetForm {
	
	@Size(min = 1, max = 140)
	private String comment;
//	private int userId;
//	private String userImg;
}
