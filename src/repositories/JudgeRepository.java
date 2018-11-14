package repositories;

import java.util.List;

import mappers.JudgeMapper;
import models.JudgeModel;
import utils.Db;

public class JudgeRepository {

	static JudgeMapper mapper = new JudgeMapper();
	
	public static List<JudgeModel> getJudges() {
		String sql = "SELECT * FROM judges";
		
		return Db.getJdbcTemplate().query(sql, mapper);
	}
}
