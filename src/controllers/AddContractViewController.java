package controllers;

import java.sql.Date;
import java.util.List;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import models.ContractModel;
import models.PlayerModel;
import models.SponsorModel;
import repositories.ContractRepository;
import repositories.PlayerRepository;
import repositories.SponsorRepository;

public class AddContractViewController {

	@FXML
	private Button okButton;
	@FXML
	private ChoiceBox<PlayerModel> playerChoiceBox = new ChoiceBox<>();
	@FXML
	private ChoiceBox<SponsorModel> sponsorChoiceBox = new ChoiceBox<>();
	@FXML
	private TextField paymentTextField;
	@FXML
	private DatePicker fromDatePicker;
	@FXML
	private DatePicker toDatePicker;
	
	private ObservableList<PlayerModel> playerItems;
	private ObservableList<SponsorModel> sponsorItems;
	
	@FXML
	private void initialize() {
		playerItems = FXCollections.observableArrayList();
		sponsorItems = FXCollections.observableArrayList();
		playerChoiceBox.setItems(playerItems);
		sponsorChoiceBox.setItems(sponsorItems);
		addPlayersToList();
		addSponsorsToList();
	}
	
	@FXML
	private void handleOkButton() {
		ContractModel contract = new ContractModel();
		if(playerChoiceBox.getValue() != null)
			contract.setPlayer(playerChoiceBox.getValue().getId());
		if(sponsorChoiceBox.getValue() != null)
			contract.setSponsor(sponsorChoiceBox.getValue().getName());
		if(!paymentTextField.getText().isEmpty()) {
			try {
				double payment = Double.valueOf(paymentTextField.getText());
				contract.setPayment(payment);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		if(fromDatePicker.getValue() != null)
			contract.setDateFrom(Date.valueOf(fromDatePicker.getValue().plusDays(1)));
		if(toDatePicker.getValue() != null)
			contract.setDateTo(Date.valueOf(toDatePicker.getValue().plusDays(1)));
		
		ContractRepository.create(contract);
		
		reset();
		Main.getAddContractStage().close();
	}
	
	public void addPlayersToList() {
		playerItems.clear();
		List<PlayerModel> players = PlayerRepository.getPlayers();
		playerItems.addAll(players);
	}
	
	public void addSponsorsToList() {
		sponsorItems.clear();
		List<SponsorModel> sponsors = SponsorRepository.get();
		sponsorItems.addAll(sponsors);
	}
	
	private void reset() {
		playerChoiceBox.setValue(null);
		sponsorChoiceBox.setValue(null);
		paymentTextField.setText("");
		fromDatePicker.setValue(null);
		toDatePicker.setValue(null);
	}
	
}
