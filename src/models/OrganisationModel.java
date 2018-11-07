package models;

import lombok.Data;

public @Data class OrganisationModel {

	private String name;
	private double budget;
	
	@Override
	public String toString() {
		return this.name;
	}
}
