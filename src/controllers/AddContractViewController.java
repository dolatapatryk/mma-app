package controllers;

import java.sql.Date;
import java.util.List;

import application.Main;
import exceptions.NoDataException;
import exceptions.UniqueKeyException;
import exceptions.WrongDatesException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
		try {
			ContractModel contract = new ContractModel();

			if (playerChoiceBox.getValue() == null || sponsorChoiceBox.getValue() == null
					|| paymentTextField.getText().isEmpty() || fromDatePicker.getValue() == null
					|| toDatePicker.getValue() == null) {
				throw new NoDataException();
			} else {
				contract.setPlayer(playerChoiceBox.getValue().getId());
				contract.setSponsor(sponsorChoiceBox.getValue().getName());

				contract.setDateFrom(Date.valueOf(fromDatePicker.getValue().plusDays(1)));
				contract.setDateTo(Date.valueOf(toDatePicker.getValue().plusDays(1)));

				if(contract.getDateTo().getTime() < contract.getDateFrom().getTime())
					throw new WrongDatesException();

				try {
					String paymentString = paymentTextField.getText();
					paymentString = paymentString.replace(",", ".");
					double budget = Double.valueOf(paymentString);
					contract.setPayment(budget);
				} catch (NumberFormatException e) {
					throw new NumberFormatException();
				}
			}

			ContractRepository.create(contract);

			reset();
			Main.getAddContractStage().close();
		} catch (Exception e) {
			handleException(e);
		}
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
	private void showNumberMessageDialog() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Błędna wartość!");
		alert.setContentText("Zapłata musi być liczbą !");

		alert.showAndWait();
	}

	private void showEnterDataMessageDialog() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Brak danych");
		alert.setContentText("Wymagane pola nie mogą być puste!");

		alert.showAndWait();
	}

	private void showWrongDatesMessageDialog() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Złe daty");
		alert.setContentText("Data \"do\" musi być późniejsza niż data \"od\"");

		alert.showAndWait();
	}

	private void handleException(Exception e) {
		if(e instanceof NoDataException)
			showEnterDataMessageDialog();
		else if(e instanceof NumberFormatException)
			showNumberMessageDialog();
		else if(e instanceof WrongDatesException)
			showWrongDatesMessageDialog();
		else
			e.printStackTrace();
	}

	
}
