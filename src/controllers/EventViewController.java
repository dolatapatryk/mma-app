package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import models.OrganisationModel;
import models.PlayerModel;
import models.WeightClassModel;

public class EventViewController {

	@FXML
	private ChoiceBox<OrganisationModel> organisationChoiceBox = new ChoiceBox<>();
	private ObservableList<OrganisationModel> organisationItems;
	
	@FXML
	private ListView<PlayerModel> playerListView;
	private ObservableList<PlayerModel> playerItems;
	
	@FXML
	private void initialize() {
		organisationItems = FXCollections.observableArrayList();
		playerItems = FXCollections.observableArrayList();
	}
}
