package com.example.demo.service.players;

import java.util.List;

import com.example.demo.entity.players.PlayersInfo;

public interface PlayerService {
	List<PlayersInfo> allSearch();
	
	List<PlayersInfo> batterNameSearch(String inputName);
	
	List<PlayersInfo> batterUniSearch(int inputUni);
	
	List<PlayersInfo> peatcherNameSearch(String inputName);
	
	List<PlayersInfo> peatcherUniSearch(int inputUni);
	
	PlayersInfo fetchPositionId(String name);
	
}
