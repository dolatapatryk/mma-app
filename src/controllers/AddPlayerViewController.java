package controllers;

import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import models.ClubModel;
import models.OrganisationModel;
import models.PlayerModel;
import repositories.ClubRepository;
import repositories.OrganisationRepository;
import repositories.PlayerRepository;

public class AddPlayerViewController {

	@FXML
	private TextField nameTextField;
	@FXML
	private TextField surnameTextField;
	@FXML
	private Button okButton;
	@FXML
	private ChoiceBox<ClubModel> clubChoiceBox = new ChoiceBox<>();
	@FXML
	private ChoiceBox<OrganisationModel> organisationChoiceBox = new ChoiceBox<>();
	
	private ObservableList<ClubModel> clubItems;
	private ObservableList<OrganisationModel> orgItems;
	
	@FXML
	private void initialize() {
		clubItems = FXCollections.observableArrayList();
		orgItems = FXCollections.observableArrayList();
		clubChoiceBox.setItems(clubItems);
		organisationChoiceBox.setItems(orgItems);
		addClubsToList();
		addOrgsToList();
	}
	
	@FXML
	private void handleOkButton() {
		PlayerModel player = new PlayerModel();
		if(!nameTextField.getText().isEmpty())
			player.setName(nameTextField.getText());
		if(!surnameTextField.getText().isEmpty())
			player.setSurname(surnameTextField.getText());
		if(clubChoiceBox.getValue() != null)
			player.setClub(clubChoiceBox.getValue().getName());
		if(organisationChoiceBox.getValue() != null) {
			player.setOrganisation(organisationChoiceBox.getValue().getName());
		}
		
		PlayerRepository.create(player);
		
		OrganisationViewController.getAddPlayerStage().close();
		RootViewController.getOrganisationViewController().getRefreshButton().fire();
	}
	
	private void addClubsToList() {
		clubItems.clear();
		List<ClubModel> clubs = ClubRepository.getClubs();
		clubItems.addAll(clubs);
	}
	
	private void addOrgsToList() {
		orgItems.clear();
		List<OrganisationModel> orgs = OrganisationRepository.getOrganisations();
		orgItems.addAll(orgs);
	}
}
