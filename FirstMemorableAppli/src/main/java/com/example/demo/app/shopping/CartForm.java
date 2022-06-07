package com.example.demo.app.shopping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartForm {
	
	private int size_l;
	private int size_xl;
	private int size_xxl;
	private String pro_name;
	private String pro_img;
	private int pro_id;
}
