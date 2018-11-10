package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.OrganisationModel;
import repositories.OrganisationRepository;

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
	private Button okButton;
	
	@FXML
	private void handleOkButton() {
		OrganisationModel org = new OrganisationModel();
		if(!nameTextField.getText().isEmpty())
			org.setName(nameTextField.getText());
		if(!budgetTextField.getText().isEmpty())
			org.setBudget(Double.valueOf(budgetTextField.getText()));
		if(!addressTextField.getText().isEmpty())
			org.setAddress(addressTextField.getText());
		if(!cityTextField.getText().isEmpty())
			org.setCity(cityTextField.getText());
		
		OrganisationRepository.create(org);
		
		reset();
	}
	
	private void reset() {
		nameTextField.setText("");
		budgetTextField.setText("");
		addressTextField.setText("");
		cityTextField.setText("");
	}
}
