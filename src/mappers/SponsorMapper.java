package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import models.SponsorModel;

public class SponsorMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		SponsorModel sponsor = new SponsorModel();
		sponsor.setName(rs.getString("name"));
		
		return sponsor;
	}

	
}
