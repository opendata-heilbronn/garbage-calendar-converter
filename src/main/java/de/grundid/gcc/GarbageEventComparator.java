package de.grundid.gcc;

import java.util.Comparator;

public class GarbageEventComparator implements Comparator<GarbageEvent> {

	@Override
	public int compare(GarbageEvent o1, GarbageEvent o2) {
		int c = o1.getDate().compareTo(o2.getDate());
		if (c == 0)
			c = o1.getType().compareTo(o2.getType());
		return c;
	}
}
