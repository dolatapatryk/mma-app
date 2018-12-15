package models;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.Data;

public @Data class ContractModel {

	private int player;
	private String sponsor;
	private double payment;
	private Date dateFrom;
	private Date dateTo;
}
