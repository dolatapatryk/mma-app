package models;

import lombok.Data;

public @Data class PlayerModel {

	private int id;
	private String name;
	private String surname;
	private int wins;
	private int losses;
	private int draws;
	private String organisation;
	private String club;
	private String weightClass;
	private int standUp;
	private int grappling;
	private int wrestling;
	private int clinch;
	private int coach;
	
	@Override
	public String toString() {
		return this.name + " " + this.surname;
	}
}
