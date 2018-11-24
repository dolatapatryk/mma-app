package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import mappers.PlayerMapper;
import models.PlayerModel;
import utils.Db;

public class PlayerRepository {
	static PlayerMapper playerMapper = new PlayerMapper();
	static Logger logger = LoggerFactory.getLogger(PlayerRepository.class);

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
	
	public static Optional<PlayerModel> get(int id) {
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
	
	public static List<PlayerModel> getPlayersByWeightClass(String weightClass) {
		String sql = "SELECT * FROM players WHERE weight_class = ?";
		
		return Db.getJdbcTemplate().query(sql, new Object[] {weightClass}, playerMapper);
	}
	
	public static void create(PlayerModel player) {
		String sql = "INSERT INTO players(name, surname, club, organisation, weight_class, "
				+ "stand_up, grappling, wrestling, clinch, coach) values(?, ?, ?, ?, ?, ?, ?, "
				+ "?, ?, ?)";
		
		try (Connection connection = Db.getJdbcTemplate().getDataSource().getConnection()) {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, player.getName());
			ps.setString(2, player.getSurname());
			ps.setString(3, player.getClub());
			ps.setString(4, player.getOrganisation());
			ps.setString(5, player.getWeightClass());
			ps.setInt(6, player.getStandUp());
			ps.setInt(7, player.getGrappling());
			ps.setInt(8, player.getWrestling());
			ps.setInt(9, player.getClinch());
			ps.setInt(10, player.getCoach());
			ps.execute();
			
			ResultSet generatedKeys = ps.getGeneratedKeys();
			generatedKeys.next();
			player.setId(generatedKeys.getInt(1));
			logger.info("Utworzono zawodnika o id: {}", player.getId());
		} catch (SQLException e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void delete(int id) {
		String sql = "DELETE FROM players WHERE id = ?";
		
		Db.getJdbcTemplate().update(sql, new Object[] {id});
	}
	
	public static void update(PlayerModel player) {
		String sql = "UPDATE players SET name = ?, surname = ?, stand_up = ?, grappling = ?, wrestling = ?, clinch = ? "
				+ "WHERE id = ?";
		
		Object[] params = {player.getName(), player.getSurname(), player.getStandUp(), player.getGrappling(),
				player.getWrestling(), player.getClinch(), player.getId()
		};
		
		Db.getJdbcTemplate().update(sql, params);
	}
}
