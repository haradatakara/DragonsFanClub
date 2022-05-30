package com.example.demo.dao.players;

import java.util.List;

import com.example.demo.entity.players.PlayersInfo;

public interface PlayerDao {
	List<PlayersInfo> allSearch();
	
	List<PlayersInfo> batterNameSearch(String inputName);
	
	List<PlayersInfo> batterUniSearch(int inputUni);
	
	List<PlayersInfo> peatcherNameSearch(String inputName);
	
	List<PlayersInfo> peatcherUniSearch(int inputUni);
	
	PlayersInfo fetchPositionId(String name);

}
