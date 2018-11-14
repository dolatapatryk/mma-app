package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import lombok.Getter;
import lombok.Setter;
import models.CoachModel;
import models.JudgeModel;
import models.PersonModel;

public class PersonMapper implements RowMapper {
	
	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		PersonModel p = new PersonModel();
		p.setId(rs.getInt("id"));
		p.setName(rs.getString("name"));
		p.setSurname(rs.getString("surname"));
		
		return p;
	}

}
