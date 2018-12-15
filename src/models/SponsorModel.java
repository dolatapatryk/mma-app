package models;

import lombok.Data;

public @Data class SponsorModel {

	private String name;
	
	@Override
	public String toString() {
		return this.name;
	}
}
