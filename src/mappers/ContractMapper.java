package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import models.ContractModel;

public class ContractMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		ContractModel c = new ContractModel();
		c.setPlayer(rs.getInt("player"));
		c.setSponsor(rs.getString("sponsor"));
		c.setPayment(rs.getDouble("payment"));
		c.setDateFrom(rs.getDate("date_from"));
		c.setDateTo(rs.getDate("date_to"));
		
		return c;
	}

	
}
