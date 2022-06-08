package com.example.demo.app.update;

import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateForm {
	private String name;
	
	@Email
	private String mail_address;

	private String password;
	
	private String secTitle;
}
