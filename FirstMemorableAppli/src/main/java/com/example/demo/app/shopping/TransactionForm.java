package com.example.demo.app.shopping;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionForm {
	@NotBlank
	private String username;
	
	@NotBlank
    @Email
	private String mailaddress;
	
	@NotBlank
	private String address;
	
	
	private String creditNumber;
	private String creditPassword;
	@NotBlank
	private String payment;
	
	@NotBlank
	private String dates;
}
