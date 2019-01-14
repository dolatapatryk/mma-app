package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import mappers.EventMapper;
import models.EventModel;
import models.PlayerModel;
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
	
	public static List<EventModel> getEventsByNames(List<String> names) {
		if(names.isEmpty())
			return new ArrayList<>();
		
		String sql = "SELECT * FROM events WHERE name in (:names)";
		
		return Db.getNamedParameterJdbcTemplate().query(sql, new MapSqlParameterSource("names", names), 
				mapper);
	}
	
	public static Optional<EventModel> getByName(String name) {
		List<EventModel> events = getEventsByNames(Arrays.asList(name));
		
		return events.isEmpty() ? Optional.empty() : Optional.ofNullable(events.get(0));
	}
	
	public static void endEvent(int eventId) {
		String sql = "UPDATE events SET active = 0 WHERE id = ?";
		
		Db.getJdbcTemplate().update(sql, new Object[] {eventId});
	}

	public static List<EventModel> getByName() {
		String sql = "SELECT * FROM events ORDER BY active DESC";

		return Db.getJdbcTemplate().query(sql, mapper);
	}

}
