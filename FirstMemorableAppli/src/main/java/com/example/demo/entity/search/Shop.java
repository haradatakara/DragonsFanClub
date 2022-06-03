package com.example.demo.entity.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shop {
	private int shop_id;
	private String shop_name;
	private String address;
	private String tel_number;
	private String shop_img; 
	private String url;
	private String description;
}
