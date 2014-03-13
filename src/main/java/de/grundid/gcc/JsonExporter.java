package de.grundid.gcc;

import java.io.FileOutputStream;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonExporter {

	private ObjectMapper om = new ObjectMapper();

	public void export(List<GarbageCalendar> cals) {
		try {
			FileOutputStream json = new FileOutputStream("/Users/adrian/Downloads/opendata/garbage.data.js");
			ObjectWriter ow = om.writerWithDefaultPrettyPrinter();
			json.write(ow.writeValueAsString(cals).getBytes("utf8"));
			json.flush();
			json.close();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
