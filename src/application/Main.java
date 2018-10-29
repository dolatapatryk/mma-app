package application;
	
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.PlayerModel;
import repositories.PlayerRepository;
import utils.Db;


public class Main extends Application {
	Logger logger = LoggerFactory.getLogger(Main.class);
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			Db.addConnection();
			List<PlayerModel> players = PlayerRepository.getPlayersByOrganisation("ufc");
			System.out.println(players);

		} catch(Exception e) {
			logger.warn(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
