package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import models.PlayerModel;

public class PlayerMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		PlayerModel p = new PlayerModel();
		p.setId(rs.getInt("id"));
		p.setName(rs.getString("name"));
		p.setSurname(rs.getString("surname"));
		p.setWins(rs.getInt("wins"));
		p.setLosses(rs.getInt("losses"));
		p.setTies(rs.getInt("ties"));
		p.setClub(rs.getString("club"));
		p.setOrganisation(rs.getString("organisation"));
		return p;
	}

}
