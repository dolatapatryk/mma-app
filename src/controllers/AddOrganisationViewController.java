package controllers;

import application.Main;
import exceptions.NoDataException;
import exceptions.UniqueKeyException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import models.OrganisationModel;
import repositories.OrganisationRepository;

import java.util.Optional;

public class AddOrganisationViewController {

	@FXML
	private TextField nameTextField;
	@FXML
	private TextField budgetTextField;
	@FXML
	private TextField addressTextField;
	@FXML
	private TextField cityTextField;

	
	@FXML
	private void handleOkButton() {
		try {
			OrganisationModel org = new OrganisationModel();

			if (nameTextField.getText().isEmpty() || budgetTextField.getText().isEmpty()
					|| addressTextField.getText().isEmpty() || cityTextField.getText().isEmpty()) {
				throw new NoDataException();
			} else {
				org.setName(nameTextField.getText());
				org.setAddress(addressTextField.getText());
				org.setCity(cityTextField.getText());
				try {
					String budgetString = budgetTextField.getText();
					budgetString = budgetString.replace(",", ".");
					double budget = Double.valueOf(budgetString);
					org.setBudget(budget);
				} catch (NumberFormatException e) {
					throw new NumberFormatException();
				}
			}

			Optional<OrganisationModel> orgOpt = OrganisationRepository.get(org.getName());
			if(orgOpt.isPresent())
				throw new UniqueKeyException();

			OrganisationRepository.create(org);

			reset();
			Main.getAddOrganisationStage().close();
			Main.getRootViewController().loadOrganisationsToMenu();
			RootViewController.getAddPlayerViewController().addOrgsToList();
			RootViewController.getMainViewController().addOrgsToList();
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	private void reset() {
		nameTextField.setText("");
		budgetTextField.setText("");
		addressTextField.setText("");
		cityTextField.setText("");
	}

	private void showNumberMessageDialog() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Błędna wartość!");
		alert.setContentText("Budżet musi być liczbą !");

		alert.showAndWait();
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
		alert.setContentText("Organizacja o takiej nazwie już istnieje!");

		alert.showAndWait();
	}

	private void handleException(Exception e) {
		if(e instanceof NoDataException)
			showEnterDataMessageDialog();
		else if(e instanceof NumberFormatException)
			showNumberMessageDialog();
		else if(e instanceof UniqueKeyException)
			showNameTakenMessageDialog();
		else
			e.printStackTrace();
	}
}
