package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Db {
	
	static Logger logger = LoggerFactory.getLogger(Db.class);

	private static ConnectionData dbConnection;
	
	public static void addConnection() {
		try {
			HikariConfig config = new HikariConfig();
			config.setDriverClassName("com.mysql.cj.jdbc.Driver");
			config.setJdbcUrl("jdbc:mysql://localhost:3306/mma?"
					+ "useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
			config.setUsername("patryk");
			config.setPassword("dromader15");
			config.setMinimumIdle(1);
			config.setMaximumPoolSize(3);

			HikariDataSource hikariDataSource = new HikariDataSource(config);
			dbConnection = new ConnectionData(hikariDataSource, new JdbcTemplate(hikariDataSource), 
					new NamedParameterJdbcTemplate(hikariDataSource));
			System.out.println("Connected to db");
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
	}
	
	public static JdbcTemplate getJdbcTemplate() {
		return dbConnection.getJdbcTemplate();
	}
	
	public static NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return dbConnection.getNamedJdbcTemplate();
	}
}
