package repositories;

import java.util.List;

import mappers.PersonMapper;
import models.CoachModel;
import models.JudgeModel;
import utils.Db;

public class PersonRepository {

	static PersonMapper mapper = new PersonMapper();
	
	public static List<JudgeModel> getJudges() {
		String sql = "SELECT * FROM judges";
				
		return Db.getJdbcTemplate().query(sql, mapper);
	}
	
	public static List<CoachModel> getCoaches() {
		String sql = "SELECT * FROM coaches";
				
		return Db.getJdbcTemplate().query(sql, mapper);
	}
}
