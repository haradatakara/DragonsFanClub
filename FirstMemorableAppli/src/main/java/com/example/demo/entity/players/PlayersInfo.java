package com.example.demo.entity.players;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayersInfo {
	private int player_id;
	private String player_name;
	private int uniform_number;
	private int position_id;
	private String pathname;
	private BatterRecord batterRecord;
	private PeatcherRecord peatcherRecord;
}
