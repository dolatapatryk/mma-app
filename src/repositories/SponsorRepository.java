package repositories;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import mappers.SponsorMapper;
import models.SponsorModel;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import utils.Db;

public class SponsorRepository {
	static SponsorMapper mapper = new SponsorMapper();
	
	public static List<SponsorModel> get(){
		String sql = "SELECT * FROM sponsors";
		
		return Db.getJdbcTemplate().query(sql, mapper);
	}

	public static List<SponsorModel> getSponsorsByNames(List<String> names) {
		if(names.isEmpty())
			return new ArrayList<>();

		String sql = "SELECT * FROM sponsors WHERE name in (:names)";

		return Db.getNamedParameterJdbcTemplate().query(sql, new MapSqlParameterSource("names", names),
				mapper);
	}

	public static Optional<SponsorModel> get(String name) {
		List<SponsorModel> clubs = getSponsorsByNames(Arrays.asList(name));

		return clubs.isEmpty() ? Optional.empty() : Optional.ofNullable(clubs.get(0));
	}
	
	public static void create(SponsorModel sponsor) {
		String sql = "INSERT INTO sponsors(name) VALUES(?)";
		
		try(Connection connection = Db.getJdbcTemplate().getDataSource().getConnection()) {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, sponsor.getName());
			ps.execute();
		} catch (SQLException e) {
			System.out.println(e.getCause());
		}
	}
	
	public static List<SponsorModel> getSponsorsByPlayerContracts(int playerId) {
		String sql = "SELECT DISTINCT s.name FROM sponsors s JOIN contracts c ON s.name = c.sponsor "
				+ "WHERE c.player = ? AND c.date_to >= ?";
		
		return Db.getJdbcTemplate().query(sql, new Object[] {playerId, new Date(System.currentTimeMillis())}, mapper);
	}
	
	public static void delete(String name) {
		String sql = "DELETE FROM sponsors WHERE name = ?";
		
		Db.getJdbcTemplate().update(sql, new Object[] {name});
	}
	
}
