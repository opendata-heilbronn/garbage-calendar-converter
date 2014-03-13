package de.grundid.gcc;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.google.common.base.Predicate;

public class IcsExporter implements Predicate<GarbageCalendar> {

	private CalendarConverter cc = new CalendarConverter();
	private String basePath = "/Users/adrian/Downloads/opendata/";

	public IcsExporter(String basePath) {
		this.basePath = basePath;
	}

	@Override
	public boolean apply(GarbageCalendar gc) {
		try {
			byte[] bs = cc.convert(gc);
			FileUtils.writeByteArrayToFile(new File(basePath + gc.getCommunity() + ".ics"), bs);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		return true;
	}
}
