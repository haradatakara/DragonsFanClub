package com.example.demo.entity.update;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateOrder {
	private int order_id;
	private String pro_name;
	private String payment; 
	private Timestamp orderday;
	private String img;
	private int product_id;
	private int x_size;
	private int xl_size;
	private int xxl_size;
	private String arriveday;
}
