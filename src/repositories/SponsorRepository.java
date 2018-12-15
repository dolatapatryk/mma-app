package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import mappers.SponsorMapper;
import models.SponsorModel;
import utils.Db;

public class SponsorRepository {
	static SponsorMapper mapper = new SponsorMapper();
	
	public static List<SponsorModel> get(){
		String sql = "SELECT * FROM sponsors";
		
		return Db.getJdbcTemplate().query(sql, mapper);
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
	
}
