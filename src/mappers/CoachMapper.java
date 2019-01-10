package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import models.CoachModel;

public class CoachMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		CoachModel c = new CoachModel();
		c.setId(rs.getInt("id"));
		c.setName(rs.getString("name"));
		c.setSurname(rs.getString("surname"));
		
		return c;
	}

}
