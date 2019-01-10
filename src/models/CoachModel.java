package models;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
public @Data class CoachModel extends PersonModel {

	
	@Override
	public String toString() {
		return "Trener " + this.getName() + " " + this.getSurname();
	}
}
