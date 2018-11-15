package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.sun.media.jfxmedia.logging.Logger;

import mappers.CoachMapper;
import mappers.JudgeMapper;
import models.CoachModel;
import models.JudgeModel;
import models.PersonModel;
import utils.Db;

public class PersonRepository {

	static CoachMapper coachMapper = new CoachMapper();
	static JudgeMapper judgeMapper = new JudgeMapper();
	
	public static List<JudgeModel> getJudges() {
		String sql = "SELECT * FROM judges";
				
		return Db.getJdbcTemplate().query(sql, judgeMapper);
	}
	
	public static List<CoachModel> getCoaches() {
		String sql = "SELECT * FROM coaches";
				
		return Db.getJdbcTemplate().query(sql, coachMapper);
	}
	
	/**
	 * 
	 * @param p
	 * @param judgeOrCoach false - judge, true - coach
	 */
	public static void create(PersonModel p, boolean judgeOrCoach) {
		String sql = "INSERT INTO ";
		if(!judgeOrCoach)
			sql = sql + "judges";
		else
			sql = sql + "coaches";
		sql = sql + "(name, surname) values(?, ?)";
		
		try(Connection connection = Db.getJdbcTemplate().getDataSource().getConnection()) {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, p.getName());
			ps.setString(2, p.getSurname());
			ps.execute();
			
			ResultSet generatedKeys = ps.getGeneratedKeys();
			generatedKeys.next();
			p.setId(generatedKeys.getInt(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
