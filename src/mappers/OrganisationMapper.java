package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import models.OrganisationModel;

public class OrganisationMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		OrganisationModel o = new OrganisationModel();
		o.setName(rs.getString("name"));
		o.setBudget(rs.getDouble("budget"));
		o.setAddress(rs.getString("address"));
		o.setCity(rs.getString("city"));
		
		return o;
	}

}
