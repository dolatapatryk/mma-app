package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import models.EventModel;

public class EventMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		EventModel e = new EventModel();
		e.setId(rs.getInt("id"));
		e.setName(rs.getString("name"));
		e.setCountry(rs.getString("country"));
		e.setCity(rs.getString("city"));
		e.setArena(rs.getString("arena"));
		e.setDate(rs.getDate("date"));
		e.setOrganisation(rs.getString("organisation"));
		e.setActive(rs.getInt("active"));
		
		return e;
	}

}
