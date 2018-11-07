package controllers;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import models.OrganisationModel;
import repositories.OrganisationRepository;

public class RootViewController {
	
	Logger logger = LoggerFactory.getLogger(RootViewController.class);	

	@FXML
	private Menu organisationMenu;
	
	@FXML 
	private Menu actionMenu;
	
	@FXML
	private MenuItem closeMenuItem;
	
	@FXML 
	private MenuItem mainSceneMenuItem;
	
	private AnchorPane organisationView = null;
	private OrganisationViewController organisationViewController = null;
	@Getter private AnchorPane mainView = null;
	
	@FXML
	private void initialize() {
		loadOtherViews();
		
		List<OrganisationModel> orgs = OrganisationRepository.getOrganisations();
		
		orgs.stream().map(o -> o.getName()).collect(Collectors.toList())
			.forEach(o -> {
				MenuItem menuItem = new MenuItem(o);
				organisationMenu.getItems().add(menuItem);
			});
		
		organisationMenu.getItems().forEach(item -> item.
				setOnAction(i -> setOrganisationView(item.getText())));
	}
	
	private void setOrganisationView(String organisation) {
		OrganisationViewController.organisation = organisation;
		Main.getRootLayout().setCenter(this.organisationView);
		organisationViewController.getText().setText("ekran organizacji: " + organisation);
		organisationViewController.getRefreshButton().fire();
		logger.info("przelaczono na widok organizacji: " + organisation);
		logger.debug(organisation);
	}
	
	private void loadOrganisationView() {
		FXMLLoader organisationLoader = new FXMLLoader();
		File organisationViewFXML = new File(System.getProperty("user.dir") + 
				"/resources/OrganisationView.fxml");	
		try {
			organisationLoader.setLocation(organisationViewFXML.toURI().toURL());
			this.organisationView = (AnchorPane) organisationLoader.load();
			this.organisationViewController = organisationLoader.getController();
		} catch (Exception e) {
			logger.warn(e.getMessage());
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
	}
	
	@FXML
	private void setMainScene() {
		Main.getRootLayout().setCenter(this.mainView);
		logger.info("przelaczono do ekranu glownego");
	}
}
