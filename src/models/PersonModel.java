package models;

import lombok.Data;

public @Data class PersonModel {

	private int id;
	private String name;
	private String surname;
	
	@Override
	public String toString() {
		return this.getName() + " " + this.getSurname();
	}
}
