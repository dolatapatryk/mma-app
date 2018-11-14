package repositories;

import java.util.List;

import mappers.CoachMapper;
import models.CoachModel;
import utils.Db;

public class CoachRepository {

	static CoachMapper mapper = new CoachMapper();
	
	public static List<CoachModel> getCoaches() {
		String sql = "SELECT * FROM coaches";
		
		return Db.getJdbcTemplate().query(sql, mapper);
	}
}
