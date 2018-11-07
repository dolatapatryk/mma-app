package controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Getter;
import models.PlayerModel;
import repositories.PlayerRepository;

public class OrganisationViewController {

	Logger logger = LoggerFactory.getLogger(RootViewController.class);	
	
	public static String organisation = "";
	
	@FXML 
	@Getter private Text text;
	@FXML
	@Getter private  Button refreshButton;
	@FXML
	private Button addPlayerButton;
	
	@Getter private static Stage addPlayerStage = null;
	
	@FXML
	private ListView<PlayerModel> playerList;
	
	private ObservableList<PlayerModel> playerItems;
	
	@FXML
	private void initialize() {
		playerItems = FXCollections.observableArrayList();
		playerList.setItems(playerItems);
	}
	
	private void addPlayersToList() {
		playerItems.clear();
		List<PlayerModel> players = PlayerRepository.getPlayersByOrganisation(organisation);
		playerItems.addAll(players);
	}
	
	@FXML
	private void handleRefreshButton() {
		addPlayersToList();
	}
	
	@FXML
	private void handleAddPlayerButton() {
		addPlayerStage = new Stage();
		addPlayerStage.setTitle("Dodaj zawodnika");
		Scene scene = new Scene(RootViewController.getAddPlayerView());
		addPlayerStage.setScene(scene);
		addPlayerStage.setX(500);
		addPlayerStage.setY(500);
		addPlayerStage.show();
	}
	
}
