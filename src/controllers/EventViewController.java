package controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import exceptions.NoDataException;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import application.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import lombok.Getter;
import models.ChampionModel;
import models.EventModel;
import models.FightModel;
import models.JudgeModel;
import models.PlayerModel;
import models.WeightClassModel;
import repositories.ChampionRepository;
import repositories.EventRepository;
import repositories.FightRepository;
import repositories.PersonRepository;
import repositories.PlayerRepository;
import repositories.WeightClassRepository;

public class EventViewController {
	
	Logger logger = LoggerFactory.getLogger(EventViewController.class);
	
	public static String eventName = "";
	private EventModel event = new EventModel();	
	private PlayerModel player1;
	private PlayerModel player2;

	@FXML
	private ChoiceBox<WeightClassModel> weightClassChoiceBox = new ChoiceBox<>();
	private ObservableList<WeightClassModel> weightClassItems;
	
	@FXML
	private ListView<PlayerModel> playerListView;
	private ObservableList<PlayerModel> playerItems;
	
	@FXML
	private TextField player1TextField;
	@FXML
	private TextField player2TextField;
	@FXML
	private @Getter Button fightButton;	
	@FXML
	private ChoiceBox<JudgeModel> judgeChoiceBox = new ChoiceBox<>();
	private ObservableList<JudgeModel> judgeItems;
	@FXML
	private ListView<FightModel> fightListView;
	private ObservableList<FightModel> fightItems;
	@FXML
	private Label winnerLabel;
	@FXML
	private Label judgeLabel;
	@FXML
	private Label weightLabel;
	@FXML
	private Text winnerText;
	@FXML
	private Text judgeText;
	@FXML
	private Text weightClassText;
	@FXML
	private ChoiceBox<String> fightModeChoiceBox = new ChoiceBox<>();
	private ObservableList<String> fightModeItems;
	@FXML
	private Label player1Label;
	@FXML
	private Label player2Label;
	@FXML
	private Button endEventButton;
	@FXML
	private Button clearButton;
	
	ChangeListener<PlayerModel> playerItemListener;

	private static final String HIGHLIGHTED_CONTROL_INNER_BACKGROUND = "derive(palegreen, 50%)";

	private Callback<ListView<PlayerModel>, ListCell<PlayerModel>> defaultCellFactory;

	
	@FXML
	private void initialize() {
		weightClassItems = FXCollections.observableArrayList();
		playerItems = FXCollections.observableArrayList();
		judgeItems = FXCollections.observableArrayList();
		fightItems = FXCollections.observableArrayList();
		fightModeItems = FXCollections.observableArrayList();
		playerListView.setItems(playerItems);
		weightClassChoiceBox.setItems(weightClassItems);
		judgeChoiceBox.setItems(judgeItems);
		fightListView.setItems(fightItems);
		fightModeChoiceBox.setItems(fightModeItems);
		fightModeItems.addAll(Arrays.asList("Walka", "Walka mistrzowska"));
		fightModeChoiceBox.getSelectionModel().selectFirst();
		addWeightClassesToList();
		addJudgesToList();
		defaultCellFactory = playerListView.getCellFactory();

		weightClassChoiceBox.getSelectionModel().selectedItemProperty()
			.addListener(new ChangeListener<WeightClassModel>() {
				@Override
				public void changed(ObservableValue<? extends WeightClassModel> arg0, WeightClassModel arg1,
						WeightClassModel arg2) {
					addPlayersByWeightClass(arg2);
				}
			});
		
		playerItemListener = new ChangeListener<PlayerModel>() {
			public void changed(ObservableValue<? extends PlayerModel> observable,
    				PlayerModel oldValue, PlayerModel newValue) {
    			addPlayerToFight(newValue);
    		}
		};
		playerListView.getSelectionModel().selectedItemProperty()
    		.addListener(playerItemListener);
		
		fightListView.getSelectionModel().selectedItemProperty()
			.addListener(new ChangeListener<FightModel>() {
				@Override
				public void changed(ObservableValue<? extends FightModel> arg0, FightModel arg1, FightModel arg2) {
					displayFightInfo(arg2);
				}
			});
		
		fightModeChoiceBox.getSelectionModel().selectedItemProperty()
			.addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
					if(arg2.equals("Walka mistrzowska"))
						addChampionToLabel();
					if(arg2.equals("Walka"))
						setNormalFightMode();
				}
			});
	}
	
	private void addPlayersToList() {
		playerItems.clear();
		List<PlayerModel> players = PlayerRepository.getPlayersByOrganisation(event.getOrganisation());
		playerItems.addAll(players);
	}
	
	private void addWeightClassesToList() {
		weightClassItems.clear();
		List<WeightClassModel> weightClasses = WeightClassRepository.getWeightClasses();
		weightClassItems.addAll(weightClasses);
	}
	
	private void addJudgesToList() {
		judgeItems.clear();
		List<JudgeModel> judges = PersonRepository.getJudges();
		judgeItems.addAll(judges);
	}
	
	private void addFightsToList() {
		fightItems.clear();
		List<FightModel> fights = FightRepository.getFightsByEvent(event.getId());
		fightItems.addAll(fights);
	}
	
	private void addPlayersByWeightClass(WeightClassModel weightClass) {
		if(weightClass != null) {
			clearListenerPlayerListItemSelected();
			playerItems.clear();
			playerListView.setCellFactory(defaultCellFactory);

			List<PlayerModel> players;
			if (weightClass == null)
				players = PlayerRepository.getPlayersByOrganisation(event.getOrganisation());
			else
				players = PlayerRepository.getPlayersByOrganisationAndWeightClass(event.getOrganisation(), weightClass.getName());
			playerItems.addAll(players);
			fightModeChoiceBox.setValue("Walka");
			player1TextField.setText("");
			player2TextField.setText("");
			player1Label.setText("Zawodnik 1");

			if(weightClass != null) {
				Optional<ChampionModel> champOpt = ChampionRepository.get(event.getOrganisation(), weightClass.getName());
				if(champOpt.isPresent()) {
					setColorToChampionCell(playerListView, champOpt.get().getPlayer());
				}
			}

			addListenerPlayerListItemSelected();
		}
	}
	
	private void addPlayerToFight(PlayerModel player) {
		if(player != null && weightClassChoiceBox.getValue() != null) {
			if (player1TextField.getText().isEmpty()) {
				player1TextField.setText(player.toString());
				player1 = player;
			} else if (player2TextField.getText().isEmpty()) {
				player2TextField.setText(player.toString());
				player2 = player;
				fightButton.setDisable(false);
			}
		}
	}
	
	@FXML
	private void handleFightButton() {
		try {
			FightModel fight = new FightModel();
			if (player1 != null)
				fight.setPlayer1(player1.getId());
			if (player2 != null)
				fight.setPlayer2(player2.getId());
			fight.setEvent(event.getId());

			if (judgeChoiceBox.getValue() == null || weightClassChoiceBox.getValue() == null)
				throw new NoDataException();
			else
				fight.setJudge(judgeChoiceBox.getValue().getId());
			if (judgeChoiceBox.getValue() != null)
				fight.setJudge(judgeChoiceBox.getValue().getId());
			int winner = doFight(player1, player2);
			fight.setWinner(winner);

			FightRepository.create(fight);
			logger.info("zakonczono walke");

			PlayerRepository.updateScore(fight);

			if (fightModeChoiceBox.getValue().equals("Walka mistrzowska")) {
				Optional<ChampionModel> champOpt = ChampionRepository.get(event.getOrganisation(), weightClassChoiceBox.getValue().getName());
				ChampionModel champTmp = new ChampionModel();
				if (winner == 1)
					champTmp.setPlayer(player1.getId());
				if (winner == 2)
					champTmp.setPlayer(player2.getId());
				champTmp.setOrganisation(event.getOrganisation());
				champTmp.setWeightClass(weightClassChoiceBox.getValue().getName());

				if (champOpt.isPresent())
					ChampionRepository.updateChamp(champTmp);
				else
					ChampionRepository.create(champTmp);
			}

			player1TextField.clear();
			player2TextField.clear();

			refresh(false);
			fightButton.setDisable(true);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void refresh(boolean refreshPlayers) {
		clearListenerPlayerListItemSelected();
		event = EventRepository.getByName(eventName).get();
		if(refreshPlayers) {
			addPlayersToList();
			weightClassChoiceBox.setValue(null);
		} else {
			addPlayersByWeightClass(weightClassChoiceBox.getValue());
		}
		addFightsToList();
		addJudgesToList();
		winnerLabel.setVisible(false);
		judgeLabel.setVisible(false);
		weightLabel.setVisible(false);
		winnerText.setText("");
		judgeText.setText("");
		weightClassText.setText("");
		addListenerPlayerListItemSelected();
		if(refreshPlayers)
			playerListView.setCellFactory(defaultCellFactory);
	}
	
	private int doFight(PlayerModel player1, PlayerModel player2) {
		int player1Score = player1.getStandUp() + player1.getGrappling() 
			+ player1.getWrestling() + player1.getClinch();
		int player2Score = player2.getStandUp() + player2.getGrappling() 
		+ player2.getWrestling() + player2.getClinch();
		
		if(player1Score > player2Score)
			return 1;
		else if(player1Score < player2Score)
			return 2;
		else
			return 0;
	}
	
	private void displayFightInfo(FightModel fight) {
		if(fight != null) {
			winnerLabel.setVisible(true);
			judgeLabel.setVisible(true);
			weightLabel.setVisible(true);
			
			PlayerModel winner;
			if(fight.getWinner() == 0)
				winnerText.setText("REMIS");
			else if(fight.getWinner() == 1) {
				Optional<PlayerModel> winnerOpt = PlayerRepository.get(fight.getPlayer1());
				if(winnerOpt.isPresent()) {
					winner = winnerOpt.get();
					winnerText.setText(winner.toString());
					String weightClass = winner.getWeightClass();
					weightClass = weightClass.substring(5);
					weightClassText.setText(weightClass);
				} else 
					winnerText.setText("Nie znaleziono informacji o zwycięzcy");
			} else if (fight.getWinner() == 2) {
				Optional<PlayerModel> winnerOpt = PlayerRepository.get(fight.getPlayer2());
				if(winnerOpt.isPresent()) {
					winner = winnerOpt.get();
					winnerText.setText(winner.toString());
					String weightClass = winner.getWeightClass();
					weightClass = weightClass.substring(5);
					weightClassText.setText(weightClass);
				} else 
					winnerText.setText("Nie znaleziono informacji o zwycięzcy");
			}
			Optional<JudgeModel> judgeOpt = PersonRepository.getJudge(fight.getJudge());
			if(judgeOpt.isPresent())
				judgeText.setText(judgeOpt.get().toString());
			else
				judgeText.setText("Nie znaleziono informacji o sędzi");
		}
	}
	
	private void addChampionToLabel() {
		player1TextField.clear();
		player2TextField.clear();
		String organisation = event.getOrganisation();
		WeightClassModel weightClass = weightClassChoiceBox.getValue();
		if(weightClass != null) {
			String weightClassName = weightClass.getName();
			Optional<ChampionModel> champOpt = ChampionRepository.get(organisation, weightClassName);
			if (champOpt.isPresent()) {
				player1 = PlayerRepository.get(champOpt.get().getPlayer()).get();
				player1TextField.setText(player1.toString());
				player1Label.setText("Mistrz");
			}
		}
	}
	
	@FXML
	private void handleEndEventButton() {
		EventRepository.endEvent(event.getId());
		Main.getRootLayout().setCenter(Main.getRootViewController().getMainView());
		Main.getRootViewController().loadEventsToMenu();
	}

	@FXML
	private void handleClearButton() {
		player1TextField.clear();
		player2TextField.clear();
		weightClassChoiceBox.setValue(null);
		judgeChoiceBox.setValue(null);
		addPlayersToList();
		playerListView.setCellFactory(defaultCellFactory);
	}
	
	private void clearListenerPlayerListItemSelected() {
		playerListView.getSelectionModel().selectedItemProperty()
			.removeListener(playerItemListener);
	}
	
	private void addListenerPlayerListItemSelected() {
		playerListView.getSelectionModel().selectedItemProperty()
			.addListener(playerItemListener);
	}

	private void setNormalFightMode() {
		player1TextField.clear();
		player2TextField.clear();
		player1Label.setText("Zawodnik 1");
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

	private void showEnterDataMessageDialog() {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText("Brak danych");
		alert.setContentText("Wybierz kategorię wagową i/lub sędziego");

		alert.showAndWait();
	}

	private void handleException(Exception e) {
		if(e instanceof NoDataException)
			showEnterDataMessageDialog();
		else
			e.printStackTrace();
	}

	public boolean checkIfEventIsActive() {
		if(event.getActive() == 0)
			return false;
		else
			return true;
	}

	public void enableEvent(boolean enable) {
		fightModeChoiceBox.setDisable(!enable);
		judgeChoiceBox.setDisable(!enable);
		endEventButton.setDisable(!enable);
		player1Label.setDisable(!enable);
		player2Label.setDisable(!enable);
		clearButton.setDisable(!enable);
		playerListView.setDisable(!enable);
	}
}
