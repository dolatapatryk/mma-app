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

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.sun.media.jfxmedia.logging.Logger;

import mappers.CoachMapper;
import mappers.JudgeMapper;
import models.CoachModel;
import models.JudgeModel;
import models.PersonModel;
import models.PlayerModel;
import utils.Db;

public class PersonRepository {

	static CoachMapper coachMapper = new CoachMapper();
	static JudgeMapper judgeMapper = new JudgeMapper();
	
	public static List<JudgeModel> getJudges() {
		String sql = "SELECT * FROM judges";
				
		return Db.getJdbcTemplate().query(sql, judgeMapper);
	}
	
	public static List<JudgeModel> getJudgesByIds(List<Integer> ids) {
		if(ids.isEmpty())
			return new ArrayList<>();
		
		String sql = "SELECT * FROM judges WHERE id in (:ids)";
		
		return Db.getNamedParameterJdbcTemplate().query(sql, new MapSqlParameterSource("ids", ids), 
				judgeMapper);
	}
	
	public static Optional<JudgeModel> getJudge(int id) {
		List<JudgeModel> judges = getJudgesByIds(Arrays.asList(id));
		
		return judges.isEmpty() ? Optional.empty() : Optional.ofNullable(judges.get(0));
	}
	
	public static List<CoachModel> getCoaches() {
		String sql = "SELECT * FROM coaches";
				
		return Db.getJdbcTemplate().query(sql, coachMapper);
	}
	
	public static List<CoachModel> getCoachesByIds(List<Integer> ids) {
		if(ids.isEmpty())
			return new ArrayList<>();
		
		String sql = "SELECT * FROM coaches WHERE id in (:ids)";
		
		return Db.getNamedParameterJdbcTemplate().query(sql, new MapSqlParameterSource("ids", ids), 
				coachMapper);
	}
	
	public static Optional<CoachModel> getCoach(int id) {
		List<CoachModel> coaches = getCoachesByIds(Arrays.asList(id));
		
		return coaches.isEmpty() ? Optional.empty() : Optional.ofNullable(coaches.get(0));
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
