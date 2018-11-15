package controllers;

import java.util.List;
import java.util.stream.Collectors;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import models.ClubModel;
import models.CoachModel;
import models.JudgeModel;
import models.OrganisationModel;
import models.PersonModel;
import models.PlayerModel;
import repositories.ClubRepository;
import repositories.OrganisationRepository;
import repositories.PersonRepository;
import repositories.PlayerRepository;

public class MainViewController {

	@FXML
	private Button addPlayerButton;
	@FXML
	private Button addOrganisationButton;
	@FXML
	private Button addClubButton;
	@FXML
	private Button addJudgeCoachButton;
	@FXML
	private ListView<PlayerModel> playerListView;
	@FXML
	private ListView<OrganisationModel> orgListView;
	@FXML
	private ListView<ClubModel> clubListView;
	@FXML
	private ListView<PersonModel> judgeCoachListView;

	private ObservableList<PlayerModel> playerItems;
	private ObservableList<OrganisationModel> orgItems;
	private ObservableList<ClubModel> clubItems;
	private ObservableList<PersonModel> judgeCoachItems;
	
	@FXML
	private void initialize() {
		playerItems = FXCollections.observableArrayList();
		orgItems = FXCollections.observableArrayList();
		clubItems = FXCollections.observableArrayList();
		judgeCoachItems = FXCollections.observableArrayList();
		playerListView.setItems(playerItems);
		orgListView.setItems(orgItems);
		clubListView.setItems(clubItems);
		judgeCoachListView.setItems(judgeCoachItems);
		addPlayersToList();
		addOrgsToList();
		addClubsToList();
		addJudgesCoachesToList();
	}
	
	public void addPlayersToList() {
		playerItems.clear();
		List<PlayerModel> players = PlayerRepository.getPlayers();
		playerItems.addAll(players);
	}
	
	public void addOrgsToList() {
		orgItems.clear();
		List<OrganisationModel> orgs = OrganisationRepository.getOrganisations();
		orgItems.addAll(orgs);
	}
	
	public void addClubsToList() {
		clubItems.clear();
		List<ClubModel> clubs = ClubRepository.getClubs();
		clubItems.addAll(clubs);
	}
	
	public void addJudgesCoachesToList() {
		judgeCoachItems.clear();
		List<JudgeModel> judges = PersonRepository.getJudges();
		judgeCoachItems.addAll(judges);
		List<CoachModel> coaches = PersonRepository.getCoaches();
		judgeCoachItems.addAll(coaches);
		judgeCoachItems.sort((e, f) -> {
			if(e.getId() > f.getId())
				return 1;
			else if(e.getId() < f.getId())
				return -1;
			else return 0;
		});
	}
	
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
		Main.getAddClubStage().show();
	}
	
	@FXML
	private void handleAddJudgeCoachButton() {
		Main.getAddJudgeCoachStage().show();
	}
}
