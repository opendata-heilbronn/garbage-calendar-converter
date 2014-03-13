package de.grundid.gcc;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Converter {

	private static Map<String, String> fileNameToCommunity = new HashMap<>();
	static {
		fileNameToCommunity.put("Abstatt", "Abstatt");
		fileNameToCommunity.put("Bad Friedrichshall", "Bad Friedrichshall");
		fileNameToCommunity.put("Bad Rappenau", "Bad Rappenau");
		fileNameToCommunity.put("Bad Wimpfen", "Bad Wimpfen");
		fileNameToCommunity.put("Beilstein", "Beilstein");
		fileNameToCommunity.put("Brackenheim", "Brackenheim");
		fileNameToCommunity.put("Cleebronn", "Cleebronn");
		fileNameToCommunity.put("Eberstadt", "Eberstadt");
		fileNameToCommunity.put("Ellhofen", "Ellhofen");
		fileNameToCommunity.put("Eppingen Stadt", "Eppingen Stadt");
		fileNameToCommunity.put("Erlenbach", "Erlenbach");
		fileNameToCommunity.put("Flein", "Flein");
		fileNameToCommunity.put("Gemmingen", "Gemmingen");
		fileNameToCommunity.put("Gueglingen.33076", "Güglingen");
		fileNameToCommunity.put("Gundelsheim", "Gundelsheim");
		fileNameToCommunity.put("Hardthausen", "Hardthausen");
		fileNameToCommunity.put("Ilsfeld", "Ilsfeld");
		fileNameToCommunity.put("Ittlingen", "Ittlingen");
		fileNameToCommunity.put("Jagsthausen", "Jagsthausen");
		fileNameToCommunity.put("Kirchardt", "Kirchardt");
		fileNameToCommunity.put("Langenbrettach", "Langenbrettach");
		fileNameToCommunity.put("Lauffenneu", "Lauffen");
		fileNameToCommunity.put("Lehrensteinsfeld", "Lehrensteinsfeld");
		fileNameToCommunity.put("Leingarten", "Leingarten");
		fileNameToCommunity.put("Loewenstein", "Löwenstein");
		fileNameToCommunity.put("Massenbachhausen", "Massenbachhausen");
		fileNameToCommunity.put("Moeckmuehl", "Möckmühl");
		fileNameToCommunity.put("Neckarsulmneu", "Neckarsulm");
		fileNameToCommunity.put("Neckarwestheim", "Neckarwestheim");
		fileNameToCommunity.put("Neudenau", "Neudenau");
		fileNameToCommunity.put("Neuenstadt", "Neuenstadt");
		fileNameToCommunity.put("Nordheim", "Nordheim");
		fileNameToCommunity.put("Obersulm", "Obersulm");
		fileNameToCommunity.put("Oedheim", "Oedheim");
		fileNameToCommunity.put("Offenau", "Offenau");
		fileNameToCommunity.put("Pfaffenhofen", "Pfaffenhofen");
		fileNameToCommunity.put("Roigheim", "Roigheim");
		fileNameToCommunity.put("Schwaigern.33052", "Schwaigern");
		fileNameToCommunity.put("Siegelsbach", "Siegelsbach");
		fileNameToCommunity.put("Talheim", "Talheim");
		fileNameToCommunity.put("Untereisesheim", "Untereisesheim");
		fileNameToCommunity.put("Untergruppenbach", "Untergruppenbach");
		fileNameToCommunity.put("Weinsbergneu", "Weinsberg");
		fileNameToCommunity.put("Widdern", "Widdern");
		fileNameToCommunity.put("Wuestenrot", "Wüstenrot");
		fileNameToCommunity.put("Zaberfeld", "Zaberfeld");
	}
	private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	private SimpleDateFormat sdfMinutes = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

	private List<Calendar> processRow(Sheet sheet, int startRow, int columnNo) {
		List<Calendar> result = new ArrayList<>();
		Row row = sheet.getRow(startRow);
		while (row != null) {
			Cell cell = row.getCell(columnNo);
			if (cell == null) {
				break;
			}
			Date value = null;
			try {
				value = cell.getDateCellValue();
			}
			catch (Exception e) {
				String str = cell.getStringCellValue();
				try {
					if (str != null) {
						str = str.trim().replace(',', '.');
						if (str.endsWith(".2014"))
							value = parseDate(str);
						else if (str.endsWith("."))
							value = parseDate(str + "2014");
						else
							value = parseDate(str + ".2014");
					}
				}
				catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
			if (value != null) {
				Calendar c = Calendar.getInstance();
				c.setTime(value);
				result.add(c);
			}
			startRow++;
			row = sheet.getRow(startRow);
		}
		return result;
	}

	private Date parseDate(String str) throws ParseException {
		return sdf.parse(str);
	}

	public List<GarbageCalendar> getCommunityCalendars() {
		List<GarbageCalendar> cals = new ArrayList<>();
		try {
			InputStream inp = new FileInputStream("/Users/adrian/Downloads/daten_muell_abfuhr.xlsx");
			Workbook wb = WorkbookFactory.create(inp);
			for (int sheetNo = 0; sheetNo < wb.getNumberOfSheets(); sheetNo++) {
				Sheet sheet = wb.getSheetAt(sheetNo);
				String fileName = sheet.getSheetName();
				String community = fileNameToCommunity.get(fileName);
				GarbageCalendar gc = new GarbageCalendar(community, fileName);
				for (int x = 0; x < 3; x++) {
					String category = sheet.getRow(0).getCell(x).getStringCellValue();
					List<Calendar> entries = processRow(sheet, 1, x);
					if (!entries.isEmpty()) {
						for (Calendar calendar : entries) {
							gc.addEntry(new GarbageEvent(category, calendar));
						}
						System.out.println(community + " " + category + " " + entries.size());
					}
				}
				cals.add(gc);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return cals;
	}
}
