package controllers;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainViewController {

	@FXML
	private Button addPlayerButton;
	@FXML
	private Button addOrganisationButton;
	@FXML
	private Button addClubButton;
	
	
	@FXML
	private void handleAddPlayerButton() {
		Main.getAddPlayerStage().show();
	}
	
	@FXML
	private void handleAddOrganisationButton() {
		Main.getAddOrganisationStage().show();
	}
	
	@FXML
	private void handleAddClubButton() {
		
	}
}
