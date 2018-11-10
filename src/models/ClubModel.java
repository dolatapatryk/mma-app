package models;

import lombok.Data;

public @Data class ClubModel {

	private String name;
	private String address;
	private String city;
	
	@Override
	public String toString() {
		return this.name;
	}
}
