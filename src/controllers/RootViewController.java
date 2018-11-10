package controllers;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import models.OrganisationModel;
import repositories.OrganisationRepository;

public class RootViewController {
	
	static Logger logger = LoggerFactory.getLogger(RootViewController.class);	
	
	@FXML
	@Getter private Menu organisationMenu;
	
	@FXML 
	private Menu actionMenu;
	
	@FXML
	private MenuItem closeMenuItem;
	
	@FXML 
	private MenuItem mainSceneMenuItem;
	
	private static AnchorPane organisationView = null;
	@Getter private static OrganisationViewController organisationViewController = null;
	
	@Getter private static AnchorPane addPlayerView = null;
	@Getter private static AddPlayerViewController addPlayerViewController = null;
	
	@Getter private static AnchorPane addOrganisationView = null;
	
	@Getter private static AnchorPane addClubView = null;
	
	@Getter private AnchorPane mainView = null;
		
	
	@FXML
	private void initialize() {		
		loadOtherViews();
				
		loadOrganisationsToMenu();
	}
	
	private void setOrganisationView(String organisation) {
		OrganisationViewController.organisation = organisation;
		Main.getRootLayout().setCenter(organisationView);
		organisationViewController.getText().setText("ekran organizacji: " + organisation);
		organisationViewController.getRefreshButton().fire();
		logger.info("przelaczono na widok organizacji: " + organisation);
		logger.info(organisation);
	}
	
	public void loadOrganisationsToMenu() {
		List<OrganisationModel> orgs = OrganisationRepository.getOrganisations();
		organisationMenu.getItems().clear();
		
		orgs.stream().map(o -> o.getName()).collect(Collectors.toList())
			.forEach(o -> {
				MenuItem menuItem = new MenuItem(o);
				organisationMenu.getItems().add(menuItem);
			});
		
		setOnActionsToOrganisationMenu();
	}
	
	private void setOnActionsToOrganisationMenu() {
		organisationMenu.getItems().forEach(item -> item
				.setOnAction(i -> setOrganisationView(item.getText())));
	}
	
	private void loadOrganisationView() {
		FXMLLoader organisationLoader = new FXMLLoader();
		File organisationViewFXML = new File(System.getProperty("user.dir") + 
				"/resources/OrganisationView.fxml");	
		try {
			organisationLoader.setLocation(organisationViewFXML.toURI().toURL());
			organisationView = (AnchorPane) organisationLoader.load();
			organisationViewController = organisationLoader.getController();
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
	}
	
	private void loadAddPlayerView() {
		FXMLLoader addPlayerLoader = new FXMLLoader();
		File addPlayerViewFXML = new File(System.getProperty("user.dir") + 
				"/resources/AddPlayerView.fxml");
		try {
			addPlayerLoader.setLocation(addPlayerViewFXML.toURI().toURL());
			addPlayerView = (AnchorPane) addPlayerLoader.load();
			addPlayerViewController = addPlayerLoader.getController();
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}
	
	private void loadAddOrganisationView() {
		FXMLLoader addOrganisationLoader = new FXMLLoader();
		File addOrganisationViewFXML = new File(System.getProperty("user.dir") +
				"/resources/AddOrganisationView.fxml");
		try {
			addOrganisationLoader.setLocation(addOrganisationViewFXML.toURI().toURL());
			addOrganisationView = (AnchorPane) addOrganisationLoader.load();
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}
	
	private void loadAddClubView() {
		FXMLLoader addClubLoader = new FXMLLoader();
		File addClubViewFXML = new File(System.getProperty("user.dir") +
				"/resources/AddClubView.fxml");
		try {
			addClubLoader.setLocation(addClubViewFXML.toURI().toURL());
			addOrganisationView = (AnchorPane) addClubLoader.load();
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}
	
	private void loadMainScene() {
		FXMLLoader mainSceneLoader = new FXMLLoader();
		File mainSceneViewFXML = new File(System.getProperty("user.dir") + 
				"/resources/MainView.fxml");
		try {
			mainSceneLoader.setLocation(mainSceneViewFXML.toURI().toURL());
			this.mainView = (AnchorPane) mainSceneLoader.load();
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
	}
	
	@FXML
	private void closeApp() {
		System.exit(1);
	}
	
	private void loadOtherViews() {
		loadMainScene();
		loadOrganisationView();
		loadAddPlayerView();
		loadAddOrganisationView();
		loadAddClubView();
	}
	
	@FXML
	private void setMainScene() {
		Main.getRootLayout().setCenter(this.mainView);
		logger.info("przelaczono do ekranu glownego");
	}
	
}
