package controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import lombok.Getter;
import models.PlayerModel;
import repositories.PlayerRepository;

public class OrganisationViewController {

	Logger logger = LoggerFactory.getLogger(RootViewController.class);	
	
	public static String organisation = "";
	
	@FXML 
	@Getter private Text text;
	@FXML
	@Getter private Button refreshButton;
	
	@FXML
	private ListView<String> playerList;
	
	private ObservableList<String> playerItems;
	
	@FXML
	private void initialize() {
		playerItems = FXCollections.observableArrayList();
		playerList.setItems(playerItems);
	}
	
	private void addPlayersToList() {
		playerItems.clear();
		List<PlayerModel> players = PlayerRepository.getPlayersByOrganisation(organisation);
		playerItems.addAll(players.stream().map(p -> p.getName() + " " +  p.getSurname())
				.collect(Collectors.toList()));
	}
	
	@FXML
	private void handleRefreshButton() {
		addPlayersToList();
	}
	
}
