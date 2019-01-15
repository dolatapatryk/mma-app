package controllers;

import java.sql.Date;
import java.util.List;

import application.Main;
import exceptions.NoDataException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import models.EventModel;
import models.OrganisationModel;
import repositories.EventRepository;
import repositories.OrganisationRepository;

public class AddEventViewController {

	@FXML
	private ChoiceBox<OrganisationModel> organisationChoiceBox = new ChoiceBox<>();
	private ObservableList<OrganisationModel> organisationItems;
	
	@FXML
	private TextField nameTextField;
	@FXML
	private TextField countryTextField;
	@FXML
	private TextField cityTextField;
	@FXML
	private TextField arenaTextField;
	
	
	
	@FXML
	private void initialize() {
		organisationItems = FXCollections.observableArrayList();
		organisationChoiceBox.setItems(organisationItems);
		
		addOrganisationsToList();
	}
	
	
	public void addOrganisationsToList() {
		organisationItems.clear();
		List<OrganisationModel> orgs = OrganisationRepository.getOrganisations();
		organisationItems.addAll(orgs);
	}
	
	@FXML
	private void handleOkButton() {
		try {
			EventModel event = new EventModel();

			if (nameTextField.getText().trim().isEmpty() || countryTextField.getText().trim().isEmpty()
					|| cityTextField.getText().trim().isEmpty() || arenaTextField.getText().trim().isEmpty()
					|| organisationChoiceBox.getValue() == null) {
				throw new NoDataException();
			} else {
				event.setName(nameTextField.getText());
				event.setCountry(countryTextField.getText());
				event.setCity(cityTextField.getText());
				event.setArena(arenaTextField.getText());
				event.setOrganisation(organisationChoiceBox.getValue().getName());
			}

			event.setDate(new Date(System.currentTimeMillis()));

			EventRepository.create(event);
			reset();
			Main.getAddEventStage().close();
			Main.getRootViewController().loadEventsToMenu();
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	private void reset() {
		nameTextField.setText("");
		countryTextField.setText("");
		cityTextField.setText("");
		arenaTextField.setText("");
		organisationChoiceBox.setValue(null);
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
		else
			e.printStackTrace();
	}
}
