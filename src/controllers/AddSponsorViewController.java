package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.SponsorModel;
import repositories.SponsorRepository;

public class AddSponsorViewController {

	@FXML
	private TextField nameTextField;
	@FXML
	private Button okButton;
	
	@FXML
	private void handleOkButton() {
		SponsorModel s = new SponsorModel();
		if(!nameTextField.getText().isEmpty())
			s.setName(nameTextField.getText());
		
		SponsorRepository.create(s);
		
		reset();
		//Main.getAddClubStage().close();
		//RootViewController.getAddPlayerViewController().addClubsToList();
		//RootViewController.getMainViewController().addClubsToList();
	}
	
	private void reset() {
		nameTextField.setText("");
	}
}
