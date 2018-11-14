package models;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
public @Data class JudgeModel extends PersonModel {

	@Override
	public String toString() {
		return this.getName() + " " + this.getSurname() + " - Sędzia";
	}
}
