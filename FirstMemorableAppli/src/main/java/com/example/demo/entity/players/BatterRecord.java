package com.example.demo.entity.players;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatterRecord {
	private int player_id;
	private double average;
	private int homeran;
	private int rbi;
	private int steal;
}
