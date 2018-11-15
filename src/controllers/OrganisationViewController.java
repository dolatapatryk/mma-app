package controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import application.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import lombok.Getter;
import models.OrganisationModel;
import models.PlayerModel;
import models.WeightClassModel;
import repositories.OrganisationRepository;
import repositories.PlayerRepository;

public class OrganisationViewController {

	Logger logger = LoggerFactory.getLogger(RootViewController.class);	
	
	public static String organisation = "";
	
	@FXML 
	@Getter private Text text;
	@FXML
	private Text organisationNameText;
	@FXML
	private Text organisationBudgetText;
	@FXML
	private Text organisationAddressText;
	@FXML
	private Text organisationCityText;
	@FXML
	@Getter private Button refreshButton;
	@FXML
	private Button addPlayerButton;
	@FXML
	private ChoiceBox<WeightClassModel> weightClassChoiceBox = new ChoiceBox<>();
	private ObservableList<WeightClassModel> weightClassItems;
	
	@FXML
	private Label clubLabel;
	@FXML 
	private Label coachLabel;
		
	@FXML
	private ListView<PlayerModel> playerList;
	
	private ObservableList<PlayerModel> playerItems;
	
	@FXML
	private void initialize() {
		weightClassItems = FXCollections.observableArrayList();
		playerItems = FXCollections.observableArrayList();
		playerList.setItems(playerItems);
		playerList.getSelectionModel().selectedItemProperty()
        	.addListener(new ChangeListener<PlayerModel>() {
        		public void changed(ObservableValue<? extends PlayerModel> observable,
        				PlayerModel oldValue, PlayerModel newValue) {
        			//TODO dokonczyc listener
        		}
        	});
	}
	
	private void addPlayersToList() {
		playerItems.clear();
		List<PlayerModel> players = PlayerRepository.getPlayersByOrganisation(organisation);
		playerItems.addAll(players);
	}
	
	@FXML
	private void handleRefreshButton() {
		Optional<OrganisationModel> organisationModel = OrganisationRepository.get(organisation);
		if(organisationModel.isPresent()) {
			organisationNameText.setText(organisationModel.get().getName());
			organisationBudgetText.setText(String.valueOf(organisationModel.get().getBudget()));
			organisationAddressText.setText(organisationModel.get().getAddress());
			organisationCityText.setText(organisationModel.get().getCity());
		}
		addPlayersToList();
	}
	
	@FXML
	private void handleAddPlayerButton() {
		Main.getAddPlayerStage().show();
	}
	
}
