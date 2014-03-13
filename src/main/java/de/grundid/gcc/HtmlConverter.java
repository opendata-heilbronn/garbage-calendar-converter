package de.grundid.gcc;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class HtmlConverter {

	private static final String baseDir = "/Users/adrian/Documents/DevSystem/projects/opendata/garbage-calendar-converter/garbage-calendar-converter/src/main/resources/";

	public static void main(String[] args) {
		List<GarbageCalendar> calendars = new Converter().getCommunityCalendars();
		HtmlListExporter htmlListExporter = new HtmlListExporter(baseDir + "vorlage.html");
		String result = htmlListExporter.convert(calendars);
		writeFile("/Users/adrian/Downloads/opendata/index.html", result);
		HtmlDetailsExporter htmlDetailsExporter = new HtmlDetailsExporter(baseDir + "vorlage-details.html");
		for (GarbageCalendar gc : calendars) {
			String gcData = htmlDetailsExporter.convert(gc);
			writeFile("/Users/adrian/Downloads/opendata/" + gc.getFileName() + ".html", gcData);
		}
	}

	private static void writeFile(String fileName, String data) {
		try {
			FileUtils.writeStringToFile(new File(fileName), data);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
