package models;

import lombok.Data;

public @Data class PlayerModel {

	private int id;
	private String name;
	private String surname;
	private int wins;
	private int losses;
	private int ties;
	private String organisation;
	private String club;
}
