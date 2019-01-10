package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import mappers.OrganisationMapper;
import models.OrganisationModel;
import utils.Db;

public class OrganisationRepository {
	static OrganisationMapper organisationMapper = new OrganisationMapper();
	static Logger logger = LoggerFactory.getLogger(ClubRepository.class);

	
	public static List<OrganisationModel> getOrganisations() {
		String sql = "SELECT * FROM organisations";
		
		return Db.getJdbcTemplate().query(sql, organisationMapper);
	}
	
	public static List<OrganisationModel> getOrganisationsByNames(List<String> names) {
		if(names.isEmpty())
			return new ArrayList<>();
		
		String sql = "SELECT * FROM organisations WHERE name in (:names)";
		
		return Db.getNamedParameterJdbcTemplate().query(sql, new MapSqlParameterSource("names", names), 
				organisationMapper);
	}
	
	public static Optional<OrganisationModel> get(String name) {
		List<OrganisationModel> orgs = getOrganisationsByNames(Arrays.asList(name));
		
		return orgs.isEmpty() ? Optional.empty() : Optional.ofNullable(orgs.get(0));
	}
	
	public static void create(OrganisationModel org) {
		String sql = "INSERT INTO organisations(name, budget, address, city) VALUES(?, ?, ?, ?)";
		
		try(Connection connection  = Db.getJdbcTemplate().getDataSource().getConnection()) {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, org.getName());
			ps.setDouble(2, org.getBudget());
			ps.setString(3, org.getAddress());
			ps.setString(4, org.getCity());
			ps.execute();
			
			logger.info("Utworzono organizacje o nazwie: {}", org.getName());
		} catch (SQLException e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void delete(String name) {
		String sql = "DELETE FROM organisations WHERE name = ?";
		
		Db.getJdbcTemplate().update(sql, new Object[] {name});
	}
}
