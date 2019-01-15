package controllers;

import application.Main;
import exceptions.NoDataException;
import exceptions.UniqueKeyException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.SponsorModel;
import repositories.SponsorRepository;

import java.util.Optional;

public class AddSponsorViewController {

	@FXML
	private TextField nameTextField;
	@FXML
	private Button okButton;
	
	@FXML
	private void handleOkButton() {
		try {
			SponsorModel s = new SponsorModel();
			if (!nameTextField.getText().trim().isEmpty())
				s.setName(nameTextField.getText());
			else
				throw new NoDataException();

			Optional<SponsorModel> sponsOpt = SponsorRepository.get(s.getName());
			if(sponsOpt.isPresent())
			    throw new UniqueKeyException();

			SponsorRepository.create(s);

			reset();
			Main.getAddSponsorStage().close();
			RootViewController.getMainViewController().addSponsorsToList();
			RootViewController.getAddContractViewController().addSponsorsToList();
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	private void reset() {
		nameTextField.setText("");
	}


	private void showEnterDataMessageDialog() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Brak nazwy");
		alert.setContentText("Musisz podać nazwę sponsora!");

		alert.showAndWait();
	}

	private void showNameTakenMessageDialog() {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText("Nazwa zajęta");
		alert.setContentText("Sponsor o takiej nazwie już istnieje!");

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
