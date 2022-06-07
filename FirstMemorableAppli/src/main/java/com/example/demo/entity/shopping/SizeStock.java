package com.example.demo.entity.shopping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SizeStock {
	private int pro_id;
	private int size_id;
	private Long size_count;
	private Long size_l;
	private Long size_xl;
	private Long size_xxl;

}
