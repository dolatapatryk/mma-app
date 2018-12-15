package repositories;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import mappers.ContractMapper;
import models.ContractModel;
import utils.Db;

public class ContractRepository {
	static ContractMapper mapper = new ContractMapper();
	
	public static List<ContractModel> getByPlayer(int playerId) {
		String sql = "SELECT * FROM contracts WHERE player = ? and dateTo <= ?";
		
		return Db.getJdbcTemplate().query(sql, new Object[] {playerId, new Date(System.currentTimeMillis())}, mapper);
	}
	
	public static void create(ContractModel contract) {
		String sql = "INSERT INTO contracts(player, sponsor, payment, date_from, date_to) VALUES"
				+ "(?, ?, ?, ?, ?)";
		
		try(Connection connection = Db.getJdbcTemplate().getDataSource().getConnection()) {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, contract.getPlayer());
			ps.setString(2, contract.getSponsor());
			ps.setDouble(3, contract.getPayment());
			ps.setDate(4, contract.getDateFrom());
			ps.setDate(5, contract.getDateTo());
			ps.execute();
		} catch (SQLException e) {
			System.out.println(e.getCause());
		}
	}
}
