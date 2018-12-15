package controllers;

import application.Main;
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
		Main.getAddSponsorStage().close();
		RootViewController.getMainViewController().addSponsorsToList();
		RootViewController.getAddContractViewController().addSponsorsToList();
	}
	
	private void reset() {
		nameTextField.setText("");
	}
}
