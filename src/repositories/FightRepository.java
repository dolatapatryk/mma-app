package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mappers.FightMapper;
import models.FightModel;
import utils.Db;

public class FightRepository {
	
	static Logger logger = LoggerFactory.getLogger(FightRepository.class);
	static FightMapper mapper = new FightMapper();	

	public static void create(FightModel fight) {
		String sql = "INSERT INTO fights(player1, player2, event, judge, winner) "
				+ "values (?, ?, ?, ?, ?)";
		
		try(Connection connection = Db.getJdbcTemplate().getDataSource().getConnection()) {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, fight.getPlayer1());
			ps.setInt(2, fight.getPlayer2());
			ps.setInt(3, fight.getEvent());
			ps.setInt(4, fight.getJudge());
			ps.setInt(5, fight.getWinner());
			ps.execute();
			
			logger.info("rozpoczeto walke pomiedzy");
		} catch(SQLException e) {
			logger.info(e.getMessage());
		}
	}
	
	public static List<FightModel> getFightsByEvent(int event) {
		String sql = "SELECT * FROM fights WHERE event = ?";
		
		return Db.getJdbcTemplate().query(sql, new Object[] {event}, mapper);
	}
}
