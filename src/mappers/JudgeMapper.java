package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import models.JudgeModel;

public class JudgeMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		JudgeModel j = new JudgeModel();
		j.setId(rs.getInt("id"));
		j.setName(rs.getString("name"));
		j.setSurname(rs.getString("surname"));
		
		return j;
	}

}
