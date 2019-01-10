package controllers;

import application.Main;
import exceptions.NoDataException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
		PersonModel p = null;
		try {
			p = readTextFields();
			PersonRepository.create(p, false);

			reset();
			Main.getAddJudgeCoachStage().close();
			RootViewController.getMainViewController().addJudgesCoachesToList();
		} catch (NoDataException e) {
			handleException(e);
		}
	}
	
	@FXML
	private void handleCoachButton() {
		PersonModel p = null;
		try {
			p = readTextFields();
			PersonRepository.create(p, true);

			reset();
			Main.getAddJudgeCoachStage().close();
			RootViewController.getAddPlayerViewController().addCoachesToList();
			RootViewController.getMainViewController().addJudgesCoachesToList();
		} catch (NoDataException e) {
			handleException(e);
		}
	}
	
	private PersonModel readTextFields() throws NoDataException {
		PersonModel p = new PersonModel();

		if (nameTextField.getText().isEmpty() || surnameTextField.getText().isEmpty())
			throw new NoDataException();
		else {
			p.setName(nameTextField.getText());
			p.setSurname(surnameTextField.getText());
		}

		return p;
	}
	
	private void reset() {
		nameTextField.setText("");
		surnameTextField.setText("");
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
