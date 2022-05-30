package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.players.PlayerDao;
import com.example.demo.entity.players.PlayersInfo;
@Service
public class PlayerServiceImpl implements PlayerService {
	
	private final PlayerDao dao;
	@Autowired
	public PlayerServiceImpl(PlayerDao dao) {
		this.dao = dao;
	}
	
	public List<PlayersInfo> allSearch() {
		
		List<PlayersInfo> list = dao.allSearch();
		return list;
	}
	
	public List<PlayersInfo> batterNameSearch(String inputName) {
		List<PlayersInfo> list = dao.batterNameSearch(inputName);
		return list;
	 }

	@Override
	public List<PlayersInfo> batterUniSearch(int inputUni) {
		List<PlayersInfo> list = dao.batterUniSearch(inputUni);
		return list;
	}

	@Override
	public List<PlayersInfo> peatcherNameSearch(String inputName) {
		List<PlayersInfo> list = dao.peatcherNameSearch(inputName);
		return list;
	}

	@Override
	public List<PlayersInfo> peatcherUniSearch(int inputUni) {
		List<PlayersInfo> list = dao.peatcherUniSearch(inputUni);
		return list;
	}

	@Override
	public PlayersInfo fetchPositionId(String name) {
		PlayersInfo pi = dao.fetchPositionId(name);
		return pi;
	}

}
