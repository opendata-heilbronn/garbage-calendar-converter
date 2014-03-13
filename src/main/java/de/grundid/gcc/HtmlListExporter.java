package de.grundid.gcc;

import java.util.List;

public class HtmlListExporter extends AbstractHtmlExporter {

	public HtmlListExporter(String templateName) {
		super(templateName);
	}

	public String convert(List<GarbageCalendar> cals) {
		StringBuilder sb = new StringBuilder();
		for (GarbageCalendar gc : cals) {
			sb.append("<tr>");
			sb.append("<td><a href=\"").append(gc.getFileName()).append(".html\">").append(gc.getCommunity())
					.append("</a></td>");
			sb.append("<td><a href=\"http://www.landkreis-heilbronn.de/sixcms/media.php/103/").append(gc.getFileName())
					.append(".pdf\">PDF</a></td>");
			sb.append("<td><a href=\"").append(gc.getFileName()).append(".ics\">ICS</a></td>");
			sb.append("</tr>");
		}
		return template.replace("<!-- replace -->", sb);
	}
}
