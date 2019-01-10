package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import models.FightModel;

public class FightMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		FightModel fight = new FightModel();
		fight.setPlayer1(rs.getInt("player1"));
		fight.setPlayer2(rs.getInt("player2"));
		fight.setEvent(rs.getInt("event"));
		fight.setJudge(rs.getInt("judge"));
		fight.setWinner(rs.getInt("winner"));
		
		return fight;
	}

}
