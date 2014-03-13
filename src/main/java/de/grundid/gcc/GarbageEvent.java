package de.grundid.gcc;

import java.util.Calendar;

public class GarbageEvent {

	private String type;
	private Calendar date;

	public GarbageEvent(String type, Calendar date) {
		this.type = type;
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public Calendar getDate() {
		return date;
	}
}
