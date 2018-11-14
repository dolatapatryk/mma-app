package controllers;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.ClubModel;
import repositories.ClubRepository;

public class AddClubViewController {

	@FXML
	private TextField nameTextField;
	@FXML
	private TextField addressTextField;
	@FXML
	private TextField cityTextField;
	@FXML
	private Button okButton;
	
	@FXML
	private void handleOkButton() {
		ClubModel club = new ClubModel();
		if(!nameTextField.getText().isEmpty())
			club.setName(nameTextField.getText());
		if(!addressTextField.getText().isEmpty())
			club.setAddress(addressTextField.getText());
		if(!cityTextField.getText().isEmpty())
			club.setCity(cityTextField.getText());
		
		ClubRepository.create(club);
		
		reset();
		Main.getAddClubStage().close();
		RootViewController.getAddPlayerViewController().addClubsToList();
		RootViewController.getMainViewController().addClubsToList();
	}
	
	private void reset() {
		nameTextField.setText("");
		addressTextField.setText("");
		cityTextField.setText("");
	}
}
