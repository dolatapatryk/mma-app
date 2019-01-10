package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javafx.scene.control.*;
import javafx.util.Callback;
import models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import application.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import repositories.*;

public class OrganisationViewController {

	Logger logger = LoggerFactory.getLogger(RootViewController.class);	
	
	public static String organisation = "";
	
	@FXML
	private Text organisationNameText;
	@FXML
	private Text organisationBudgetText;
	@FXML
	private Text organisationAddressText;
	@FXML
	private Text organisationCityText;
	//@FXML
	//@Getter private Button refreshButton;
	@FXML
	private Button addPlayerButton;
	@FXML
	private ChoiceBox<WeightClassModel> weightClassChoiceBox = new ChoiceBox<>();
	private ObservableList<WeightClassModel> weightClassItems;
	
	@FXML
	private ChoiceBox<String> clubChoiceBox = new ChoiceBox<>();
	private ObservableList<String> clubItems;
	
	@FXML
	private ChoiceBox<PersonModel> coachChoiceBox = new ChoiceBox<>();
	private ObservableList<PersonModel> coachItems;
	
	@FXML
	private TextField nameTextField;
	@FXML
	private TextField surnameTextField;
	@FXML
	private Text winText;
	@FXML
	private Text lossText;	
	@FXML
	private Text drawText;
	@FXML
	private TextField standUpTextField;
	@FXML
	private TextField grapplingTextField;
	@FXML
	private TextField wrestlingTextField;
	@FXML
	private TextField clinchTextField;
	@FXML
	private Button updateButton;
	@FXML
	private Label idLabel;	
	@FXML
	private Text sponsorText;
	@FXML
	private Text paymentText;
	
	ChangeListener<PlayerModel> playerItemListener;
	ChangeListener<WeightClassModel> weightClassItemListener;

	
	@FXML
	private ListView<PlayerModel> playerList;
	
	private ObservableList<PlayerModel> playerItems;

	private static final String HIGHLIGHTED_CONTROL_INNER_BACKGROUND = "derive(palegreen, 50%)";

	private Callback<ListView<PlayerModel>, ListCell<PlayerModel>> defaultCellFactory;
	
	@FXML
	private void initialize() {
		weightClassItems = FXCollections.observableArrayList();
		playerItems = FXCollections.observableArrayList();
		clubItems = FXCollections.observableArrayList();
		coachItems = FXCollections.observableArrayList();
		playerList.setItems(playerItems);
		weightClassChoiceBox.setItems(weightClassItems);
		clubChoiceBox.setItems(clubItems);
		coachChoiceBox.setItems(coachItems);
		defaultCellFactory = playerList.getCellFactory();

		playerItemListener = new ChangeListener<PlayerModel>() {
			public void changed(ObservableValue<? extends PlayerModel> observable,
    				PlayerModel oldValue, PlayerModel newValue) {
    			handlePlayerItemSelected(newValue);
    		}
		};
		playerList.getSelectionModel().selectedItemProperty()
    		.addListener(playerItemListener);
		
		weightClassItemListener = new ChangeListener<WeightClassModel>() {
				@Override
				public void changed(ObservableValue<? extends WeightClassModel> observable, WeightClassModel oldValue,
						WeightClassModel newValue) {
					addPlayersByWeightClass(newValue);
				}	
			};
		weightClassChoiceBox.getSelectionModel().selectedItemProperty()
			.addListener(weightClassItemListener);
		
		refresh();
	}
	
	private void addPlayersToList() {
		playerItems.clear();
		List<PlayerModel> players = PlayerRepository.getPlayersByOrganisation(organisation);
		playerItems.addAll(players);
	}
	
	public void addWeightClassesToList() {
		weightClassItems.clear();
		List<WeightClassModel> weightClasses = WeightClassRepository.getWeightClasses();
		weightClassItems.addAll(weightClasses);
	}
	
	private void addClubsToList() {
		clubItems.clear();
		List<String> clubs = ClubRepository.getClubs().stream().map(c -> c.getName()).collect(Collectors.toList());
		clubItems.addAll(clubs);
	}
	
	private void addCoachesToList() {
		coachItems.clear();
		List<CoachModel> coaches = PersonRepository.getCoaches();
		coachItems.addAll(coaches);
	}
	
	private void addPlayersByWeightClass(WeightClassModel weightClass) {
		if(weightClass != null) {
			clearListenerPlayerListItemSelected();
			playerItems.clear();
			playerList.setCellFactory(defaultCellFactory);

			List<PlayerModel> players;
			if (weightClass == null)
				players = PlayerRepository.getPlayersByOrganisation(organisation);
			else
				players = PlayerRepository.getPlayersByOrganisationAndWeightClass(organisation, weightClass.getName());
			playerItems.addAll(players);

			if(weightClass != null) {
				Optional<ChampionModel> champOpt = ChampionRepository.get(organisation, weightClass.getName());
				if(champOpt.isPresent()) {
					setColorToChampionCell(playerList, champOpt.get().getPlayer());
				}
			}

			addListenerPlayerListItemSelected();
		}
	}
	
	
	public void refresh() {
		clearListenerPlayerListItemSelected();
		clearListenerWeightClassItemSelected();
		Optional<OrganisationModel> organisationModel = OrganisationRepository.get(organisation);
		if(organisationModel.isPresent()) {
			organisationNameText.setText(organisationModel.get().getName());
			organisationBudgetText.setText(String.valueOf(organisationModel.get().getBudget()));
			organisationAddressText.setText(organisationModel.get().getAddress());
			organisationCityText.setText(organisationModel.get().getCity());
		}
		clearPlayerInfo();
		addPlayersToList();
		addWeightClassesToList();
		addClubsToList();
		addCoachesToList();
		addListenerPlayerListItemSelected();
		addListenerWeightClassItemSelected();
		playerList.setCellFactory(defaultCellFactory);
	}
	
	@FXML
	private void handleAddPlayerButton() {
		Main.getAddPlayerStage().show();
	}
	
	private void handlePlayerItemSelected(PlayerModel player) {
		clearPlayerInfo();
		idLabel.setText(String.valueOf(player.getId()));
		nameTextField.setText(player.getName());
		surnameTextField.setText(player.getSurname());
		clubChoiceBox.setValue(player.getClub());
		Optional<CoachModel> coachOpt = PersonRepository.getCoach(player.getCoach());
		if(coachOpt.isPresent())
			coachChoiceBox.setValue(coachOpt.get());
		else
			coachChoiceBox.setValue(null);
		winText.setText(String.valueOf(player.getWins()));
		lossText.setText(String.valueOf(player.getLosses()));
		drawText.setText(String.valueOf(player.getDraws()));
		standUpTextField.setText(String.valueOf(player.getStandUp()));
		grapplingTextField.setText(String.valueOf(player.getGrappling()));
		wrestlingTextField.setText(String.valueOf(player.getWrestling()));
		clinchTextField.setText(String.valueOf(player.getClinch()));
		List<SponsorModel> sponsors = SponsorRepository.getSponsorsByPlayerContracts(player.getId());
		String txt = "";
		for(int i = 0; i < sponsors.size(); i++) {
			if(i == sponsors.size() - 1)
				txt = txt + sponsors.get(i);
			else
				txt = txt + sponsors.get(i) + ", ";
		}
		sponsorText.setText(txt);
		paymentText.setText("0");
		if(!sponsors.isEmpty())
			paymentText.setText(String.format("%.2f zł/msc", PlayerRepository.calculatePayment(player.getId())));
	}
	
	@FXML
	private void handleUpdateButton() {
		PlayerModel player = new PlayerModel();
		player.setName(nameTextField.getText());
		player.setSurname(surnameTextField.getText());
		try {
			player.setId(Integer.valueOf(idLabel.getText()));
			player.setStandUp(Integer.valueOf(standUpTextField.getText()));
			player.setGrappling(Integer.valueOf(grapplingTextField.getText()));
			player.setWrestling(Integer.valueOf(wrestlingTextField.getText()));
			player.setClinch(Integer.valueOf(clinchTextField.getText()));
		} catch (NumberFormatException e) {
			logger.info(e.getMessage());
		}
		if(clubChoiceBox.getValue() != null)
			player.setClub(clubChoiceBox.getValue());
		if(coachChoiceBox.getValue() != null)
			player.setCoach(coachChoiceBox.getValue().getId());
		
		PlayerRepository.update(player);
		refresh();
		
		logger.info("Zaktualizowano playera o id {}", player.getId());
	}
	
	private void clearPlayerInfo() {
		idLabel.setText("");
		nameTextField.setText("");
		surnameTextField.setText("");
		winText.setText("");
		lossText.setText("");
		drawText.setText("");
		standUpTextField.setText("");
		grapplingTextField.setText("");
		wrestlingTextField.setText("");
		clinchTextField.setText("");
		sponsorText.setText("");
		paymentText.setText("");
		clubChoiceBox.setValue(null);
		coachChoiceBox.setValue(null);
	}
	
	private void clearListenerPlayerListItemSelected() {
		playerList.getSelectionModel().selectedItemProperty()
			.removeListener(playerItemListener);
	}
	
	private void addListenerPlayerListItemSelected() {
		playerList.getSelectionModel().selectedItemProperty()
			.addListener(playerItemListener);
	}
	
	private void clearListenerWeightClassItemSelected() {
		weightClassChoiceBox.getSelectionModel().selectedItemProperty()
			.removeListener(weightClassItemListener);
	}
	
	private void addListenerWeightClassItemSelected() {
		weightClassChoiceBox.getSelectionModel().selectedItemProperty()
			.addListener(weightClassItemListener);
	}

	private void setColorToChampionCell(ListView<PlayerModel> listView, int championId) {
		listView.setCellFactory(new Callback<ListView<PlayerModel>, ListCell<PlayerModel>>() {
			@Override
			public ListCell<PlayerModel> call(ListView<PlayerModel> param) {
				return new ListCell<PlayerModel>() {
					@Override
					protected void updateItem(PlayerModel item, boolean empty) {
						super.updateItem(item, empty);

						if (item == null || empty) {
							setText(null);
						} else {
							setText(item.toString());
							if (item.getId() == championId) {
								setStyle("-fx-control-inner-background: " + HIGHLIGHTED_CONTROL_INNER_BACKGROUND + ";");
							}
						}
					}
				};
			}
		});
	}
	
}
