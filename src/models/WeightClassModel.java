package models;

import lombok.Data;

public @Data class WeightClassModel {

	private String name;
	
	@Override
	public String toString() {
		return this.name;
	}
}
