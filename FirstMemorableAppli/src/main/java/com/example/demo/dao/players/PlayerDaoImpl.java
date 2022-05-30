package com.example.demo.dao.players;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.players.BatterRecord;
import com.example.demo.entity.players.PeatcherRecord;
import com.example.demo.entity.players.PlayersInfo;

@Repository
public class PlayerDaoImpl implements PlayerDao {
	private final JdbcTemplate jdbcTemplate;
	
	public PlayerDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<PlayersInfo> allSearch() {
		List<PlayersInfo> list = new ArrayList<>();
		String sql = "SELECT * FROM players_info  order by  player_id";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
		
		 for(Map<String, Object> li: resultList) {
			 PlayersInfo pi = new PlayersInfo();
			 pi.setPlayer_id((int) li.get("player_id"));
			 pi.setPlayer_name((String) li.get("player_name"));
			 pi.setPosition_id((int) li.get("position_id"));
			 pi.setUniform_number((int) li.get("uniform_number"));
			 pi.setPathname((String) li.get("pathname"));
			 
			 list.add(pi);
		 }
		return list;
	}
	
	public List<PlayersInfo> batterNameSearch(String inputName) {
		List<PlayersInfo> list = new ArrayList<>();
		String sql =
				"SELECT pi.player_id, player_name, position_id, uniform_number, pathname, average, homeran, rbi, steal FROM players_info pi inner join batter_record br on pi.player_id = br.player_id where player_name LIKE ? ";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql,"%" +inputName + "%");
		for(Map<String, Object> li: resultList) {
			PlayersInfo pi = new PlayersInfo();
			pi.setPlayer_id((int) li.get("player_id"));
			pi.setPlayer_name((String) li.get("player_name"));
			pi.setPosition_id((int) li.get("position_id"));
			pi.setUniform_number((int) li.get("uniform_number"));
			pi.setPathname((String) li.get("pathname"));
			
			BatterRecord br = new BatterRecord();
			br.setAverage((Double) li.get("average"));
			br.setHomeran((int) li.get("homeran"));
			br.setRbi((int) li.get("rbi"));
			br.setSteal((int) li.get("steal"));
			pi.setBatterRecord(br);
			list.add(pi);
		}
		
		return list;
	}
	
	public List<PlayersInfo> batterUniSearch(int inputUni) {
		List<PlayersInfo> list = new ArrayList<>();
		String sql =
				"SELECT pi.player_id, player_name, position_id, uniform_number, pathname, average, homeran, rbi, steal FROM players_info pi inner join batter_record br on pi.player_id = br.player_id where uniform_number = ? ";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, inputUni);
		for(Map<String, Object> li: resultList) {
			PlayersInfo pi = new PlayersInfo();
			pi.setPlayer_id((int) li.get("player_id"));
			pi.setPlayer_name((String) li.get("player_name"));
			pi.setPosition_id((int) li.get("position_id"));
			pi.setUniform_number((int) li.get("uniform_number"));
			pi.setPathname((String) li.get("pathname"));
			
			BatterRecord br = new BatterRecord();
			br.setAverage((Double) li.get("average"));
			br.setHomeran((int) li.get("homeran"));
			br.setRbi((int) li.get("rbi"));
			br.setSteal((int) li.get("steal"));
			pi.setBatterRecord(br);
			list.add(pi);
		}
		
		return list;
	}

	@Override
	public List<PlayersInfo> peatcherNameSearch(String inputName) {
		List<PlayersInfo> list = new ArrayList<>();
		String sql =
				"SELECT pi.player_id, player_name, position_id, uniform_number, pathname, era, win, lose FROM players_info pi inner join peatcher_record pr on pi.player_id = pr.player_id where player_name LIKE ? ";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql,"%" +inputName + "%");
		for(Map<String, Object> li: resultList) {
			PlayersInfo pi = new PlayersInfo();
			pi.setPlayer_id((int) li.get("player_id"));
			pi.setPlayer_name((String) li.get("player_name"));
			pi.setPosition_id((int) li.get("position_id"));
			pi.setUniform_number((int) li.get("uniform_number"));
			pi.setPathname((String) li.get("pathname"));
			
			PeatcherRecord pr = new PeatcherRecord();
			pr.setWin((int) li.get("win"));
			pr.setLose((int) li.get("lose"));
			pr.setEra((Double) li.get("era"));
			
			pi.setPeatcherRecord(pr);
			list.add(pi);
		}
		
		return list;
	}

	@Override
	public List<PlayersInfo> peatcherUniSearch(int inputUni) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
	
	public PlayersInfo fetchPositionId(String inputName) {
		PlayersInfo pi = new PlayersInfo();
		String sql = "SELECT position_id FROM players_info where LIKE ?";
		
		Map<String, Object> player = jdbcTemplate.queryForMap(sql,"%" + inputName + "%");
		
		pi.setPlayer_id((int)player.get("positio_id"));
		return pi;
	}
	
	

}
