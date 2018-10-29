package repositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import mappers.PlayerMapper;
import models.PlayerModel;
import utils.Db;

public class PlayerRepository {
	static PlayerMapper playerMapper = new PlayerMapper();

	public static List<PlayerModel> getPlayers() {
		String sql = "SELECT * FROM players";
		
		return Db.getJdbcTemplate().query(sql, playerMapper);
	}
	
	public static List<PlayerModel> getPlayersByIds(List<Integer> ids) {
		if(ids.isEmpty())
			return new ArrayList<>();
		
		String sql = "SELECT * FROM players WHERE id in (:ids)";
		
		return Db.getNamedParameterJdbcTemplate().query(sql, new MapSqlParameterSource("ids", ids), 
				playerMapper);
	}
	
	public static Optional<PlayerModel> getPlayer(int id) {
		List<PlayerModel> players = getPlayersByIds(Arrays.asList(id));
		
		return players.isEmpty() ? Optional.empty() : Optional.ofNullable(players.get(0));
	}
	
	public static List<PlayerModel> getPlayersByClub(String clubName) {
		String sql = "SELECT * FROM players WHERE club = ?";
		
		return Db.getJdbcTemplate().query(sql, new Object[] {clubName}, playerMapper);
	}
	
	public static List<PlayerModel> getPlayersByOrganisation(String organisationName) {
		String sql = "SELECT * FROM players WHERE organisation = ?";
		
		return Db.getJdbcTemplate().query(sql, new Object[] {organisationName}, playerMapper);
	}
}
