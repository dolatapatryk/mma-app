package repositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import mappers.OrganisationMapper;
import models.OrganisationModel;
import utils.Db;

public class OrganisationRepository {
	static OrganisationMapper organisationMapper = new OrganisationMapper();
	
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
}
