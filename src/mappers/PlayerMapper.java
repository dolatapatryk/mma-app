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
		p.setDraws(rs.getInt("draws"));
		p.setClub(rs.getString("club"));
		p.setOrganisation(rs.getString("organisation"));
		p.setWeightClass(rs.getString("weight_class"));
		p.setStandUp(rs.getInt("stand_up"));
		p.setGrappling(rs.getInt("grappling"));
		p.setWrestling(rs.getInt("wrestling"));
		p.setClinch(rs.getInt("clinch"));
		p.setCoach(rs.getInt("coach"));
		
		return p;
	}

}
