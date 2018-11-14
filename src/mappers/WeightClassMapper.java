package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import models.WeightClassModel;

public class WeightClassMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		WeightClassModel wc = new WeightClassModel();
		wc.setName(rs.getString("name"));
		
		return wc;
	}

}
