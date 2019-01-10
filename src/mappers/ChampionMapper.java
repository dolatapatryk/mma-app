package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import models.ChampionModel;

public class ChampionMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		ChampionModel champion = new ChampionModel();
		champion.setPlayer(rs.getInt("player"));
		champion.setOrganisation(rs.getString("organisation"));
		champion.setWeightClass(rs.getString("organisation"));
		
		return champion;
	}

}
