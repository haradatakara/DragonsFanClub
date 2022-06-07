package com.example.demo.entity.user;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
	private int user_id;
	private String user_name;
	private String mailaddress;
	private String password;
	private int age_id;
	private LocalDateTime datetime;
	private String user_img;
//	private int user_img;
}
