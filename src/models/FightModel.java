package models;

import java.util.Optional;

import lombok.Data;
import repositories.PlayerRepository;

public @Data class FightModel {

	private int player1;
	private int player2;
	private int event;
	private int judge;
	private int winner = 0;
	
	@Override
	public String toString() {
		Optional<PlayerModel> player1Opt = PlayerRepository.get(player1);
		Optional<PlayerModel> player2Opt = PlayerRepository.get(player2);
		String text = "";
		if(player1Opt.isPresent())
			text = text + player1Opt.get().toString() + " vs ";
		else
			text = text + "(Nie znaleziono gracza 1) vs ";
		
		if(player2Opt.isPresent())
			text = text + player2Opt.get().toString();
		else
			text = text + "(Nie znaleziono gracza 2)";
				
		return text;
	}
}
