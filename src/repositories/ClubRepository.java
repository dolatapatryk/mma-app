package repositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import mappers.ClubMapper;
import models.ClubModel;
import utils.Db;

public class ClubRepository {
	static ClubMapper clubMapper = new ClubMapper();
	
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
}
