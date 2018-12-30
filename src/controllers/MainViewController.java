package controllers;

import java.util.List;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import models.ClubModel;
import models.CoachModel;
import models.JudgeModel;
import models.OrganisationModel;
import models.PersonModel;
import models.PlayerModel;
import models.SponsorModel;
import repositories.ClubRepository;
import repositories.OrganisationRepository;
import repositories.PersonRepository;
import repositories.PlayerRepository;
import repositories.SponsorRepository;

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
	private Button addSponsorButton;
	@FXML
	private Button addContractButton;
	@FXML
	private Button deleteButton;
	@FXML
	private ListView<PlayerModel> playerListView;
	@FXML
	private ListView<OrganisationModel> orgListView;
	@FXML
	private ListView<ClubModel> clubListView;
	@FXML
	private ListView<PersonModel> judgeCoachListView;
	@FXML
	private ListView<SponsorModel> sponsorListView;

	private ObservableList<PlayerModel> playerItems;
	private ObservableList<OrganisationModel> orgItems;
	private ObservableList<ClubModel> clubItems;
	private ObservableList<PersonModel> judgeCoachItems;
	private ObservableList<SponsorModel> sponsorItems;
	
	@FXML
	private void initialize() {
		playerItems = FXCollections.observableArrayList();
		orgItems = FXCollections.observableArrayList();
		clubItems = FXCollections.observableArrayList();
		judgeCoachItems = FXCollections.observableArrayList();
		sponsorItems = FXCollections.observableArrayList();
		playerListView.setItems(playerItems);
		orgListView.setItems(orgItems);
		clubListView.setItems(clubItems);
		judgeCoachListView.setItems(judgeCoachItems);
		sponsorListView.setItems(sponsorItems);
		addPlayersToList();
		addOrgsToList();
		addClubsToList();
		addJudgesCoachesToList();
		addSponsorsToList();
	}
	
	public void addPlayersToList() {
		playerItems.clear();
		List<PlayerModel> players = PlayerRepository.getPlayers();
		playerItems.addAll(players);
		playerListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}
	
	public void addOrgsToList() {
		orgItems.clear();
		List<OrganisationModel> orgs = OrganisationRepository.getOrganisations();
		orgItems.addAll(orgs);
		orgListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}
	
	public void addClubsToList() {
		clubItems.clear();
		List<ClubModel> clubs = ClubRepository.getClubs();
		clubItems.addAll(clubs);
		clubListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}
	
	public void addSponsorsToList() {
		sponsorItems.clear();
		List<SponsorModel> sponsors = SponsorRepository.get();
		sponsorItems.addAll(sponsors);
		sponsorListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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
		judgeCoachListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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
	
	@FXML
	private void handleAddSponsorButton() {
		Main.getAddSponsorStage().show();
	}
	
	@FXML
	private void handleAddContractButton() {
		Main.getAddContractStage().show();
	}
	
	@FXML
	private void handleDeleteButton() {
		if(playerListView.getSelectionModel().getSelectedItem() != null) {
			for(PlayerModel p : playerListView.getSelectionModel().getSelectedItems()) {
				PlayerRepository.delete(p.getId());
			}
			RootViewController.getOrganisationViewController().refresh();
			addPlayersToList();
			RootViewController.getAddContractViewController().addPlayersToList();
		}
		
		if(orgListView.getSelectionModel().getSelectedItem() != null) {
			for(OrganisationModel o : orgListView.getSelectionModel().getSelectedItems()) {
				OrganisationRepository.delete(o.getName());
			}
			Main.getRootViewController().loadOrganisationsToMenu();
			RootViewController.getAddPlayerViewController().addOrgsToList();
			addOrgsToList();
			addPlayersToList();
		}
		
		if(clubListView.getSelectionModel().getSelectedItem() != null) {
			for(ClubModel c : clubListView.getSelectionModel().getSelectedItems()) {
				ClubRepository.delete(c.getName());
			}
			RootViewController.getAddPlayerViewController().addClubsToList();
			addClubsToList();
		}
		
		if(judgeCoachListView.getSelectionModel().getSelectedItem() != null) {
			for(PersonModel jc : judgeCoachListView.getSelectionModel().getSelectedItems()) {
				if(jc instanceof JudgeModel) {
					PersonRepository.deleteJudge(jc.getId());
				} else if(jc instanceof CoachModel) {
					PersonRepository.deleteCoach(jc.getId());
					RootViewController.getAddPlayerViewController().addCoachesToList();
				}
			}
			addJudgesCoachesToList();
		}
		
		if(sponsorListView.getSelectionModel().getSelectedItem() != null) {
			for(SponsorModel s : sponsorListView.getSelectionModel().getSelectedItems()) {
				SponsorRepository.delete(s.getName());
			}
			addSponsorsToList();
			RootViewController.getAddContractViewController().addSponsorsToList();
		}
		
		
	}
}
