package com.example.demo.app.players;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartSearchForm {
	private String name = null;
	private int uniformNum;
}
