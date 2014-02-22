package de.grundid.gcc;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Converter {

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

	public void run() {
		CalendarConverter cc = new CalendarConverter();
		try {
			InputStream inp = new FileInputStream("/Users/adrian/Downloads/daten_muell_abfuhr.xlsx");
			Workbook wb = WorkbookFactory.create(inp);
			for (int sheetNo = 0; sheetNo < wb.getNumberOfSheets(); sheetNo++) {
				Sheet sheet = wb.getSheetAt(sheetNo);
				String community = sheet.getSheetName();
				GarbageCalendar gc = new GarbageCalendar(community);
				for (int x = 0; x < 3; x++) {
					String category = sheet.getRow(0).getCell(x).getStringCellValue();
					List<Calendar> entries = processRow(sheet, 1, x);
					if (!entries.isEmpty()) {
						gc.addEntry(category, entries);
						System.out.println(community + " " + category + " " + entries.size());
					}
				}
				try {
					byte[] bs = cc.convert(gc);
					FileOutputStream fos = new FileOutputStream("/Users/adrian/Downloads/opendata/" + community
							+ ".ics");
					fos.write(bs);
					fos.flush();
					fos.close();
				}
				catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Converter().run();
	}
}
