package controllers;

import application.Main;
import exceptions.NoDataException;
import exceptions.UniqueKeyException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.ClubModel;
import repositories.ClubRepository;

import java.util.Optional;

public class AddClubViewController {

	@FXML
	private TextField nameTextField;
	@FXML
	private TextField addressTextField;
	@FXML
	private TextField cityTextField;
	
	@FXML
	private void handleOkButton() {
		try {
			ClubModel club = new ClubModel();

			if (nameTextField.getText().isEmpty() || addressTextField.getText().isEmpty()
					|| cityTextField.getText().isEmpty()) {
				throw new NoDataException();
			} else {
				club.setName(nameTextField.getText());
				club.setAddress(addressTextField.getText());
				club.setCity(cityTextField.getText());
			}

			Optional<ClubModel> clubOpt = ClubRepository.get(club.getName());
			if(clubOpt.isPresent())
				throw new UniqueKeyException();

			ClubRepository.create(club);

			reset();
			Main.getAddClubStage().close();
			RootViewController.getAddPlayerViewController().addClubsToList();
			RootViewController.getMainViewController().addClubsToList();
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	private void reset() {
		nameTextField.setText("");
		addressTextField.setText("");
		cityTextField.setText("");
	}
	private void showEnterDataMessageDialog() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Brak danych");
		alert.setContentText("Wymagane pola nie mogą być puste!");

		alert.showAndWait();
	}


	private void showNameTakenMessageDialog() {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText("Nazwa zajęta");
		alert.setContentText("Klub o takiej nazwie już istnieje!");

		alert.showAndWait();
	}

	private void handleException(Exception e) {
		if(e instanceof NoDataException)
			showEnterDataMessageDialog();
		else if(e instanceof UniqueKeyException)
			showNameTakenMessageDialog();
		else
			e.printStackTrace();
	}
}
