package controllers;

import java.sql.Date;
import java.util.List;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
		EventModel event = new EventModel();
		
		if(!nameTextField.getText().isEmpty())
			event.setName(nameTextField.getText());
		if(!countryTextField.getText().isEmpty())
			event.setCountry(countryTextField.getText());
		if(!cityTextField.getText().isEmpty())
			event.setCity(cityTextField.getText());
		if(!arenaTextField.getText().isEmpty())
			event.setArena(arenaTextField.getText());
		if(organisationChoiceBox.getValue() != null)
			event.setOrganisation(organisationChoiceBox.getValue().getName());
		event.setDate(new Date(System.currentTimeMillis()));
		
		EventRepository.create(event);
		reset();
		Main.getAddEventStage().close();
	}
	
	private void reset() {
		nameTextField.setText("");
		countryTextField.setText("");
		cityTextField.setText("");
		arenaTextField.setText("");
		organisationChoiceBox.setValue(null);
	}
}
