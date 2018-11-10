package application;
	
import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controllers.RootViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.Getter;
import utils.Db;


public class Main extends Application {
	Logger logger = LoggerFactory.getLogger(Main.class);
	
	private Stage primaryStage;
	@Getter private static Stage addPlayerStage;
	@Getter private static Stage addOrganisationStage;
	@Getter private static BorderPane rootLayout;
	
	@Override
	public void start(Stage primaryStage) {
		Db.addConnection();
		try {
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("MMA");
			
			initRootLayout();
			initAddPlayerStage();
		} catch(Exception e) {
			logger.warn(e.getMessage());
		}
	}
	
	public void initRootLayout() {
		try {
			FXMLLoader loaderRoot = new FXMLLoader();
			File rootViewFXML = new File(System.getProperty("user.dir") + 
					"/resources/RootView.fxml");	
			loaderRoot.setLocation(rootViewFXML.toURI().toURL());
			rootLayout = (BorderPane) loaderRoot.load();
			RootViewController rootViewController = loaderRoot.getController();
			rootLayout.setCenter(rootViewController.getMainView());
		} catch (IOException e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
		}
		Scene scene = new Scene(rootLayout);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void initAddPlayerStage() {
		addPlayerStage = new Stage();
		addPlayerStage.setTitle("Dodaj zawodnika");
		addPlayerStage.setX(500);
		addPlayerStage.setY(500);
		Scene scene = new Scene(RootViewController.getAddPlayerView());
		addPlayerStage.setScene(scene);
	}
	
	public void initAddOrganisationStage() {
		addOrganisationStage = new Stage();
		addOrganisationStage.setTitle("Dodaj organizacje");
		addOrganisationStage.setX(500);
		addOrganisationStage.setY(500);
		Scene scene = new Scene(RootViewController.getAddOrganisationView());
		addOrganisationStage.setScene(scene);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
