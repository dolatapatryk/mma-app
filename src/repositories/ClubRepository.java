package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import mappers.ClubMapper;
import models.ClubModel;
import utils.Db;

public class ClubRepository {
	static ClubMapper clubMapper = new ClubMapper();
	static Logger logger = LoggerFactory.getLogger(ClubRepository.class);
	
	public static List<ClubModel> getClubs() {
		String sql = "SELECT * FROM clubs";
		
		return Db.getJdbcTemplate().query(sql, clubMapper);
	}
	
	public static List<ClubModel> getClubsByNames(List<String> names) {
		if(names.isEmpty())
			return new ArrayList<>();
		
		String sql = "SELECT * FROM clubs WHERE name in (:names)";
		
		return Db.getNamedParameterJdbcTemplate().query(sql, new MapSqlParameterSource("names", names), 
				clubMapper);
	}
	
	public static Optional<ClubModel> get(String name) {
		List<ClubModel> clubs = getClubsByNames(Arrays.asList(name));
		
		return clubs.isEmpty() ? Optional.empty() : Optional.ofNullable(clubs.get(0));	
	}

	public static void create(ClubModel club) {
		String sql = "INSERT INTO clubs (name, address, city) VALUES(?, ?, ?)";
		
		try(Connection connection = Db.getJdbcTemplate().getDataSource().getConnection()) {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, club.getName());
			ps.setString(2, club.getAddress());
			ps.setString(3, club.getCity());
			ps.execute();
			
			logger.info("Utworzono klub o nazwie {}", club.getName());
		} catch (SQLException e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void delete(String name) {
		String sql = "DELETE FROM clubs WHERE name = ?";
		
		Db.getJdbcTemplate().update(sql, new Object[] {name});
	}
}
