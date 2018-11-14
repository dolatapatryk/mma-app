package repositories;

import java.util.List;

import mappers.WeightClassMapper;
import models.WeightClassModel;
import utils.Db;

public class WeightClassRepository {
	static WeightClassMapper mapper = new WeightClassMapper();
	
	public static List<WeightClassModel> getWeightClasses() {
		String sql = "SELECT * FROM weight_classes";
		
		return Db.getJdbcTemplate().query(sql, mapper);
	}

}
