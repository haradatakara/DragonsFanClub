package com.example.demo.app.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpForm {
	
	    @NotNull(message="入力してください。")
	    @Size(min = 3, max = 15)
		private String username;
//        @Min(1)
//        @Max(5)
		private int age_id;
	    
	    @NotNull(message="入力してください。")
	    @Email
		private String mailaddress;
	    
	    @NotNull(message="入力してください。")
	    @Size(min = 3, max = 20)
		private String password;
	    
	    @NotNull(message="入力してください。")
	    @Size(min = 3, max = 20)
	    private String repassword;
	    
	    @NotNull
	    private String userImg = "/img/user/img1.jpg";
}
