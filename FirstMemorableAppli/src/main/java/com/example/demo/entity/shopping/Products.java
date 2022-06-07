package com.example.demo.entity.shopping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Products {
	private int pro_id;
	private String pro_name;
	private int price;
	private String pro_img;
	private String shop_name;
	private String shop_address;
	private int style_id;
	private String style_name;
	private int genre_id;
	private String genre_name;
	private int l_size;
	private int xl_size;
	private int xxl_size;
}
