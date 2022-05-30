package com.example.demo.app;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInForm {
	@NotNull
	private String mailaddress;
	@NotNull
	private String password;
	
	private String user_name;
}
