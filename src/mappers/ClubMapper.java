package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import models.ClubModel;

public class ClubMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		ClubModel c = new ClubModel();
		c.setName(rs.getString("name"));
		c.setAddress(rs.getString("address"));
		c.setCity(rs.getString("city"));
		
		return c;
	}

	
}
