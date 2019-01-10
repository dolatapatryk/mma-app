package models;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
public @Data class PlayerModel extends PersonModel {

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
	private Integer coach;
	
	
	@Override
	public String toString() {
		return super.toString();
	}

}
