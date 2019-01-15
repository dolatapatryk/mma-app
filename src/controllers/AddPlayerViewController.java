package controllers;

import java.util.List;

import application.Main;
import exceptions.NoDataException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import lombok.Getter;
import models.ClubModel;
import models.CoachModel;
import models.OrganisationModel;
import models.PlayerModel;
import models.WeightClassModel;
import repositories.ClubRepository;
import repositories.OrganisationRepository;
import repositories.PersonRepository;
import repositories.PlayerRepository;
import repositories.WeightClassRepository;

public class AddPlayerViewController {

	@FXML
	private TextField nameTextField;
	@FXML
	private TextField surnameTextField;
	@FXML
	private TextField standUpTextField;
	@FXML
	private TextField grapplingTextField;
	@FXML
	private TextField wrestlingTextField;
	@FXML
	private TextField clinchTextField;
	@FXML
	private Button okButton;
	@FXML
	private ChoiceBox<ClubModel> clubChoiceBox = new ChoiceBox<>();
	@FXML
	@Getter
	private ChoiceBox<OrganisationModel> organisationChoiceBox = new ChoiceBox<>();
	@FXML
	@Getter
	private ChoiceBox<WeightClassModel> weightClassChoiceBox = new ChoiceBox<>();
	@FXML
	private ChoiceBox<CoachModel> coachChoiceBox = new ChoiceBox<>();
	
	private ObservableList<ClubModel> clubItems;
	private ObservableList<OrganisationModel> orgItems;
	private ObservableList<WeightClassModel> weightClassItems;
	private ObservableList<CoachModel> coachItems;
	
	@FXML
	private void initialize() {
		clubItems = FXCollections.observableArrayList();
		orgItems = FXCollections.observableArrayList();
		weightClassItems = FXCollections.observableArrayList();
		coachItems = FXCollections.observableArrayList();
		clubChoiceBox.setItems(clubItems);
		organisationChoiceBox.setItems(orgItems);
		weightClassChoiceBox.setItems(weightClassItems);
		coachChoiceBox.setItems(coachItems);
		addClubsToList();
		addOrgsToList();
		addWeightClassesToList();
		addCoachesToList();
	}
	
	@FXML
	private void handleOkButton() {
		try {
			PlayerModel player = new PlayerModel();

			if (nameTextField.getText().trim().isEmpty() || surnameTextField.getText().trim().isEmpty()
					|| organisationChoiceBox.getValue() == null || weightClassChoiceBox.getValue() == null
					|| coachChoiceBox.getValue() == null) {
				throw new NoDataException();
			} else {
				player.setName(nameTextField.getText());
				player.setSurname(surnameTextField.getText());
				player.setOrganisation(organisationChoiceBox.getValue().getName());
				player.setWeightClass(weightClassChoiceBox.getValue().getName());
				player.setCoach(coachChoiceBox.getValue().getId());
			}

			if (clubChoiceBox.getValue() != null)
				player.setClub(clubChoiceBox.getValue().getName());

			if (!standUpTextField.getText().isEmpty()) {
				try {
					int standUp = Integer.valueOf(standUpTextField.getText());
					player.setStandUp(standUp);
				} catch (NumberFormatException e) {
					throw new NumberFormatException();
				}
			}
			if (!grapplingTextField.getText().isEmpty()) {
				try {
					int grappling = Integer.valueOf(grapplingTextField.getText());
					player.setGrappling(grappling);
				} catch (NumberFormatException e) {
					throw new NumberFormatException();
				}
			}
			if (!wrestlingTextField.getText().isEmpty()) {
				try {
					int wrestling = Integer.valueOf(wrestlingTextField.getText());
					player.setWrestling(wrestling);
				} catch (NumberFormatException e) {
					throw new NumberFormatException();
				}
			}
			if (!clinchTextField.getText().isEmpty()) {
				try {
					int clinch = Integer.valueOf(clinchTextField.getText());
					player.setClinch(clinch);
				} catch (NumberFormatException e) {
					throw new NumberFormatException();
				}
			}
			PlayerRepository.create(player);

			reset();
			Main.getAddPlayerStage().close();
			RootViewController.getOrganisationViewController().refresh();
			RootViewController.getMainViewController().addPlayersToList();
			RootViewController.getAddContractViewController().addPlayersToList();
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void addClubsToList() {
		clubItems.clear();
		List<ClubModel> clubs = ClubRepository.getClubs();
		clubItems.addAll(clubs);
	}
	
	public void addOrgsToList() {
		orgItems.clear();
		List<OrganisationModel> orgs = OrganisationRepository.getOrganisations();
		orgItems.addAll(orgs);
	}
	
	public void addWeightClassesToList() {
		weightClassItems.clear();
		List<WeightClassModel> weightClasses = WeightClassRepository.getWeightClasses();
		weightClassItems.addAll(weightClasses);
	}
	
	public void addCoachesToList() {
		coachItems.clear();
		List<CoachModel> coaches = PersonRepository.getCoaches();
		coachItems.addAll(coaches);
	}
	
	private void reset() {
		nameTextField.setText("");
		surnameTextField.setText("");
		clubChoiceBox.setValue(null);
		organisationChoiceBox.setValue(null);
		weightClassChoiceBox.setValue(null);
		standUpTextField.clear();
		grapplingTextField.clear();
		wrestlingTextField.clear();
		clinchTextField.clear();
		coachChoiceBox.setValue(null);
	}

	private void showIntegerMessageDialog() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Błędna wartość!");
		alert.setContentText("Statystyka zawodnika powinna być liczbą całkowitą!");

		alert.showAndWait();
	}

	private void showEnterDataMessageDialog() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Brak danych");
		alert.setContentText("Wymagane pola nie mogą być puste!");

		alert.showAndWait();
	}

	private void handleException(Exception e) {
		if(e instanceof NoDataException)
			showEnterDataMessageDialog();
		if(e instanceof NumberFormatException)
			showIntegerMessageDialog();
		else
			e.printStackTrace();
	}
}
