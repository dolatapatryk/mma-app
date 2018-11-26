package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
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
	private Button fightButton;	
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
	private Text winnerText;
	@FXML
	private Text judgeText;
	@FXML
	private ChoiceBox<String> fightModeChoiceBox = new ChoiceBox<>();
	private ObservableList<String> fightModeItems;
	@FXML
	private Label player1Label;
	
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
		
		weightClassChoiceBox.getSelectionModel().selectedItemProperty()
			.addListener(new ChangeListener<WeightClassModel>() {
				@Override
				public void changed(ObservableValue<? extends WeightClassModel> arg0, WeightClassModel arg1,
						WeightClassModel arg2) {
					addPlayersByWeightClass(arg2);
				}
			});
		
		playerListView.getSelectionModel().selectedItemProperty()
			.addListener(new ChangeListener<PlayerModel>() {
				@Override
				public void changed(ObservableValue<? extends PlayerModel> arg0, PlayerModel arg1, PlayerModel arg2) {
					addPlayerToFight(arg2);
				}	
			});
		
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
		playerItems.clear();
		List<PlayerModel> players = new ArrayList<>();	
		if(weightClass == null)
			players = PlayerRepository.getPlayersByOrganisation(event.getOrganisation());
		else
			players = PlayerRepository.getPlayersByOrganisationAndWeightClass(event.getOrganisation(), weightClass.getName());
		playerItems.addAll(players);
	}
	
	private void addPlayerToFight(PlayerModel player) {
		if(player1TextField.getText().isEmpty()) {
			player1TextField.setText(player.toString());
			player1 = player;
		}
		else if(player2TextField.getText().isEmpty()) {
			player2TextField.setText(player.toString());
			player2 = player;
		}		
	}
	
	@FXML
	private void handleFightButton() {
		FightModel fight = new FightModel();
		if(player1 != null)
			fight.setPlayer1(player1.getId());
		if(player2 != null)
			fight.setPlayer2(player2.getId());
		fight.setEvent(event.getId());
		if(judgeChoiceBox.getValue() != null)
			fight.setJudge(judgeChoiceBox.getValue().getId());
		int winner = doFight(player1, player2);
		fight.setWinner(winner);
		
		FightRepository.create(fight);
		logger.info("zakonczono walke");
		
		PlayerRepository.updateScore(fight);
		
		if(winner == 2 && fightModeChoiceBox.getValue().equals("Walka mistrzowska")) {
			ChampionModel champ = new ChampionModel();
			champ.setPlayer(player2.getId());
			champ.setOrganisation(event.getOrganisation());
			champ.setWeightClass(weightClassChoiceBox.getValue().getName());
			
			ChampionRepository.updateChamp(champ);
		}
		
		player1TextField.clear();
		player2TextField.clear();
		
		refresh();
	}
	
	public void refresh() {
		event = EventRepository.get(eventName).get();
		addPlayersToList();
		addFightsToList();
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
			
			PlayerModel winner = new PlayerModel();
			if(fight.getWinner() == 0)
				winnerText.setText("REMIS");
			else if(fight.getWinner() == 1) {
				winner = PlayerRepository.get(fight.getPlayer1()).get();
				winnerText.setText(winner.toString());
			} else if (fight.getWinner() == 2) {
				winner = PlayerRepository.get(fight.getPlayer2()).get();
				winnerText.setText(winner.toString());
			}
			
			JudgeModel judge = new JudgeModel();
			judge = PersonRepository.getJudge(fight.getJudge()).get();
			judgeText.setText(judge.toString());
		}
	}
	
	private void addChampionToLabel() {
		String organisation = event.getOrganisation();
		String weightClass = weightClassChoiceBox.getValue().getName();
		
		Optional<ChampionModel> champOpt = ChampionRepository.get(organisation, weightClass);
		if(champOpt.isPresent()) {
			player1 = PlayerRepository.get(champOpt.get().getPlayer()).get();
			player1TextField.setText(player1.toString());
			player1Label.setText("Mistrz");
		}
	}
}
