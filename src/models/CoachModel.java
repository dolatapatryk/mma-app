package models;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
public @Data class CoachModel extends PersonModel {

	
	@Override
	public String toString() {
		return this.getName() + " " + this.getSurname() + " - Trener";
	}
}
