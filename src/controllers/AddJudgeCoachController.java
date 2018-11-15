package controllers;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.PersonModel;
import repositories.PersonRepository;

public class AddJudgeCoachController {

	@FXML
	private TextField nameTextField;
	@FXML
	private TextField surnameTextField;
	@FXML
	private Button judgeButton;
	@FXML
	private Button coachButton;
	
	@FXML
	private void handleJudgeButton() {
		PersonRepository.create(readTextFields(), false);
		
		reset();
		Main.getAddJudgeCoachStage().close();
		RootViewController.getMainViewController().addJudgesCoachesToList();
	}
	
	@FXML
	private void handleCoachButton() {
		PersonRepository.create(readTextFields(), true);
		
		reset();
		Main.getAddJudgeCoachStage().close();
		RootViewController.getAddPlayerViewController().addCoachesToList();
		RootViewController.getMainViewController().addJudgesCoachesToList();
	}
	
	private PersonModel readTextFields() {
		PersonModel p = new PersonModel();
		if(!nameTextField.getText().isEmpty())
			p.setName(nameTextField.getText());
		if(!surnameTextField.getText().isEmpty())
			p.setSurname(surnameTextField.getText());
		
		return p;
	}
	
	private void reset() {
		nameTextField.setText("");
		surnameTextField.setText("");
	}
}
