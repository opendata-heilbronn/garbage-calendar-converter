package de.grundid.gcc;

import java.text.SimpleDateFormat;

public class HtmlDetailsExporter extends AbstractHtmlExporter {

	public HtmlDetailsExporter(String templateName) {
		super(templateName);
	}

	public String convert(GarbageCalendar gc) {
		StringBuilder sb = new StringBuilder();
		String newTemplate = template.replace("<!-- replace.name -->", gc.getCommunity());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfVisible = new SimpleDateFormat("EEE, dd.MM.yyyy");
		for (GarbageEvent ge : gc.getDates()) {
			sb.append("<div itemscope itemtype=\"http://schema.org/Event\"><div>");
			sb.append("<time itemprop=\"startDate\" datetime=\"").append(sdf.format(ge.getDate().getTime()))
					.append("\">").append(sdfVisible.format(ge.getDate().getTime())).append("</time>");
			sb.append(" <span itemprop=\"name\">").append(ge.getType()).append("</span> (<span itemprop=\"location\">")
					.append(gc.getCommunity()).append("</span>)");
			sb.append("</div></div>");
		}
		return newTemplate.replace("<!-- replace -->", sb);
	}
}
