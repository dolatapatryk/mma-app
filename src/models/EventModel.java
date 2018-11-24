package models;

import java.sql.Date;

import lombok.Data;

public @Data class EventModel {

	private int id;
	private String name;
	private String country;
	private String city;
	private String arena;
	private Date date;
	private String organisation;
	private int active;
}
