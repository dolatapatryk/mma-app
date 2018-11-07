package models;

import lombok.Data;

public @Data class ClubModel {

	private String name;
	
	@Override
	public String toString() {
		return this.name;
	}
}
