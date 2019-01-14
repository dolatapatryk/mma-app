package controllers;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import models.EventModel;
import models.OrganisationModel;
import repositories.EventRepository;
import repositories.OrganisationRepository;

public class RootViewController {
	
	static Logger logger = LoggerFactory.getLogger(RootViewController.class);	
	
	@FXML
	@Getter private Menu organisationMenu;
	@FXML
	private Menu eventMenu;
	
	@FXML 
	private Menu actionMenu;
	
	@FXML
	private MenuItem closeMenuItem;
	
	@FXML 
	private MenuItem mainSceneMenuItem;
	
	@FXML
	private MenuItem addEventMenuItem;
	
	private static AnchorPane organisationView = null;
	@Getter private static OrganisationViewController organisationViewController = null;
	
	private AnchorPane eventView = null;
	private static  @Getter EventViewController eventViewController = null;
	
	@Getter private static AnchorPane addPlayerView = null;
	@Getter private static AddPlayerViewController addPlayerViewController = null;
	
	@Getter private static AnchorPane addOrganisationView = null;
	
	@Getter private static AnchorPane addClubView = null;
	
	@Getter private static AnchorPane addJudgeCoachView = null;
	
	@Getter private static AnchorPane addEventView = null;
	
	@Getter private static AnchorPane addSponsorView = null;
	
	@Getter private static AnchorPane addContractView = null;
	@Getter private static AddContractViewController addContractViewController = null;
	
	@Getter private AnchorPane mainView = null;
	@Getter private static MainViewController mainViewController = null;
		
	
	@FXML
	private void initialize() {		
		loadOtherViews();
				
		loadOrganisationsToMenu();
		loadEventsToMenu();
	}
	
	private void setOrganisationView(String organisation) {
		OrganisationViewController.organisation = organisation;
		Main.getRootLayout().setCenter(organisationView);
		organisationViewController.refresh();
		logger.info("przelaczono na widok organizacji: " + organisation);
		logger.info(organisation);
	}
	
	private void setEventView(String event) {
		EventViewController.eventName = event;
		Main.getRootLayout().setCenter(eventView);
		eventViewController.refresh(true);
		boolean active = eventViewController.checkIfEventIsActive();
		if(!active)
			eventViewController.enableEvent(false);
		else
			eventViewController.enableEvent(true);
		
		logger.info("przelaczono na widok gali: " + event);
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
	
	public void loadEventsToMenu() {
		List<EventModel> events = EventRepository.getByName();
		eventMenu.getItems().clear();

		events.stream().map(e -> e.getName()).collect(Collectors.toList())
			.forEach(e -> {
				MenuItem menuItem = new MenuItem(e);
				eventMenu.getItems().add(menuItem);
			});
		
		setOnActionsToEventMenu();
		setColorsToEventMenu();
	}
	
	private void setOnActionsToOrganisationMenu() {
		organisationMenu.getItems().forEach(item -> item
				.setOnAction(i -> setOrganisationView(item.getText())));
	}
	
	private void setOnActionsToEventMenu() {
		eventMenu.getItems().forEach(item -> item
				.setOnAction(i -> setEventView(item.getText())));
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
	
	private void loadEventView() {
		FXMLLoader eventLoader = new FXMLLoader();
		File eventViewFXML = new File(System.getProperty("user.dir") +
				"/resources/EventView.fxml");
		try {
			eventLoader.setLocation(eventViewFXML.toURI().toURL());
			eventView = (AnchorPane) eventLoader.load();
			eventViewController = eventLoader.getController();
		} catch (Exception e) {
			logger.info(e.getMessage());
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
			addClubView = (AnchorPane) addClubLoader.load();
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}
	
	private void loadAddJudgeCoachView() {
		FXMLLoader addJudgeCoachLoader = new FXMLLoader();
		File addJudgeCoachFXML = new File(System.getProperty("user.dir") + 
				"/resources/AddJudgeCoachView.fxml");
		try {
			addJudgeCoachLoader.setLocation(addJudgeCoachFXML.toURI().toURL());
			addJudgeCoachView = (AnchorPane) addJudgeCoachLoader.load();
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}
	
	private void loadAddEventView() {
		FXMLLoader addEventLoader = new FXMLLoader();
		File addEventFXML = new File(System.getProperty("user.dir") + 
				"/resources/AddEventView.fxml");
		try {
			addEventLoader.setLocation(addEventFXML.toURI().toURL());
			addEventView = (AnchorPane) addEventLoader.load();
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}
	
	private void loadAddSponsorView() {
		FXMLLoader addSponsorLoader = new FXMLLoader();
		File addSponsorFXML = new File(System.getProperty("user.dir") +
				"/resources/AddSponsorView.fxml");
		
		try {
			addSponsorLoader.setLocation(addSponsorFXML.toURI().toURL());
			addSponsorView = (AnchorPane) addSponsorLoader.load();
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}
	
	private void loadAddContractView() {
		FXMLLoader addContractLoader = new FXMLLoader();
		File addContractFXML = new File(System.getProperty("user.dir") +
				"/resources/AddContractView.fxml");
		
		try {
			addContractLoader.setLocation(addContractFXML.toURI().toURL());
			addContractView = (AnchorPane) addContractLoader.load();
			addContractViewController = addContractLoader.getController();
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
			mainViewController = mainSceneLoader.getController();
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
		loadEventView();
		loadAddPlayerView();
		loadAddOrganisationView();
		loadAddClubView();
		loadAddJudgeCoachView();
		loadAddEventView();
		loadAddSponsorView();
		loadAddContractView();
	}
	
	@FXML
	private void setMainScene() {
		Main.getRootLayout().setCenter(this.mainView);
		logger.info("przelaczono do ekranu glownego");
	}
	
	@FXML
	private void handleAddEventMenuItem() {
		Main.getAddEventStage().show();
	}
	
	private void setColorsToEventMenu() {
		for(MenuItem item : eventMenu.getItems()) {
			Optional<EventModel> event = EventRepository.getByName(item.getText());
			if(event.isPresent()) {
				if(event.get().getActive() == 0) {
					item.setGraphic(new ImageView(new File(System.getProperty("user.dir") +
							"/resources/notactive.png").toURI().toString()));
				} else {
					item.setGraphic(new ImageView(new File(System.getProperty("user.dir") +
							"/resources/active.png").toURI().toString()));
				}
			}
		}
	}
}
