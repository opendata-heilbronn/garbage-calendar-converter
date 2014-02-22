package de.grundid.gcc;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GarbageCalendar {

	private String community;
	private Map<String, List<Calendar>> entryMap = new HashMap<String, List<Calendar>>();

	public GarbageCalendar(String community) {
		this.community = community;
	}

	public void addEntry(String category, List<Calendar> entries) {
		entryMap.put(category, entries);
	}

	public String getCommunity() {
		return community;
	}

	public Map<String, List<Calendar>> getEntryMap() {
		return entryMap;
	}
}
