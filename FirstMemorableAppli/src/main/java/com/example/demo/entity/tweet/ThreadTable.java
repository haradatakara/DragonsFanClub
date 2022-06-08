package com.example.demo.entity.tweet;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThreadTable {
	private int thread_id;
	private String create_user;
	private String title;
	private LocalDateTime created;

}
