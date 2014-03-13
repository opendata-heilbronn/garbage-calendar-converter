package de.grundid.gcc;

import java.util.SortedSet;
import java.util.TreeSet;

public class GarbageCalendar {

	private String community;
	private String fileName;
	private SortedSet<GarbageEvent> dates = new TreeSet<>(new GarbageEventComparator());

	public GarbageCalendar(String community, String fileName) {
		this.community = community;
		this.fileName = fileName;
	}

	public void addEntry(GarbageEvent event) {
		dates.add(event);
	}

	public String getCommunity() {
		return community;
	}

	public String getFileName() {
		return fileName;
	}

	public SortedSet<GarbageEvent> getDates() {
		return dates;
	}
}
