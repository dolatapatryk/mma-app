package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mappers.ChampionMapper;
import models.ChampionModel;
import utils.Db;

public class ChampionRepository {
	
	static ChampionMapper mapper = new ChampionMapper();
	static Logger logger = LoggerFactory.getLogger(ChampionRepository.class);


	public static Optional<ChampionModel> get(String organisation, String weightClass) {
		String sql = "SELECT * FROM champions where organisation = ? and weight_class = ?";
		
		List<ChampionModel> champs = Db.getJdbcTemplate().query(sql, 
				new Object[] {organisation, weightClass}, mapper);
		if(champs.isEmpty())
			return Optional.empty();
		else
			return Optional.ofNullable(champs.get(0));
	}
	
	public static void create(ChampionModel champ) {
		String sql = "INSERT INTO champions(player, organisation, weight_class) "
				+ "values(?, ?, ?)";
		
		try (Connection connection = Db.getJdbcTemplate().getDataSource().getConnection()) {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, champ.getPlayer());
			ps.setString(2, champ.getOrganisation());
			ps.setString(3, champ.getWeightClass());
			ps.execute();
			
			logger.info("Nowy mistrz w organizacji: {}, w kategorii wagowej: {}", 
					champ.getOrganisation(), champ.getWeightClass());
		} catch (SQLException e) {
			logger.info(e.getMessage());
		}
	}
	
	public static void updateChamp(ChampionModel champ) {
		String sql = "UPDATE champions SET player = ? WHERE "
				+ "organisation = ? and weight_class = ?";
		
		Object[] params = {champ.getPlayer(), champ.getOrganisation(), champ.getWeightClass()};
		
		Db.getJdbcTemplate().update(sql, params);
	}
}
