package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mappers.EventMapper;
import models.EventModel;
import utils.Db;

public class EventRepository {
	
	static Logger logger = LoggerFactory.getLogger(PlayerRepository.class);
	static EventMapper mapper = new EventMapper();
	
	public static void create(EventModel event) {
		String sql = "INSERT INTO events(name, country, city, arena, date, "
				+ "organisation) values(?, ?, ?, ?, ?, ?)";
		
		try (Connection connection = Db.getJdbcTemplate().getDataSource().getConnection()) {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, event.getName());
			ps.setString(2, event.getCountry());
			ps.setString(3, event.getCity());
			ps.setString(4, event.getArena());
			ps.setDate(5, event.getDate());
			ps.setString(6, event.getOrganisation());
			ps.execute();
			
			ResultSet generatedKeys = ps.getGeneratedKeys();
			generatedKeys.next();
			event.setId(generatedKeys.getInt(1));
			logger.info("Utworzono gale o id: {}", event.getId());
		} catch (SQLException e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static List<EventModel> getActiveEvents() {
		String sql = "SELECT * FROM events WHERE active = 1";
		
		return Db.getJdbcTemplate().query(sql, mapper);
	}

}
