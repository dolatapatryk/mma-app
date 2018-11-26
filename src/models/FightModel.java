package models;

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
		PlayerModel player1Model = PlayerRepository.get(player1).get();
		PlayerModel player2Model = PlayerRepository.get(player2).get();
		
		String text = player1Model.toString() + " vs " + player2Model.toString();
		
		return text;
	}
}
