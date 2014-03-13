package de.grundid.gcc;

import java.io.ByteArrayOutputStream;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Categories;
import net.fortuna.ical4j.model.property.Clazz;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Status;
import net.fortuna.ical4j.model.property.Transp;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.model.property.XProperty;

public class CalendarConverter {

	private SimpleDateFormat sdfMinutes = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

	public byte[] convert(GarbageCalendar gc) {
		try {
			Calendar calendar = new Calendar();
			calendar.getProperties().add(new ProdId("-//OpenDataLab Heilbronn//iCal4j 1.0//DE"));
			calendar.getProperties().add(Version.VERSION_2_0);
			calendar.getProperties().add(CalScale.GREGORIAN);
			calendar.getProperties().add(Method.PUBLISH);
			calendar.getProperties().add(new XProperty("X-WR-CALNAME", "Müllabfuhrtermine " + gc.getCommunity()));
			calendar.getProperties().add(new XProperty("X-WR-CALDESC", "Restmüll, Biomüll und Papier"));
			for (GarbageEvent entry : gc.getDates()) {
				calendar.getComponents().add(createEvent(entry));
			}
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			CalendarOutputter outputter = new CalendarOutputter();
			outputter.output(calendar, baos);
			return baos.toByteArray();
		}
		catch (Exception e) {
			throw new RuntimeException(gc.getCommunity() + "--" + e);
		}
	}

	private VEvent createEvent(GarbageEvent ge) throws URISyntaxException {
		String category = ge.getType();
		//		System.out.println(sdfMinutes.format(date.getTime()));
		VEvent event = new VEvent(new Date(ge.getDate().getTime()), category);
		//		System.out.println(event.getStartDate().toString());
		PropertyList properties = event.getProperties();
		properties.add(new Uid(category + "-" + ge.getDate().getTimeInMillis()));
		properties.add(new Organizer("OpenDataDay"));
		properties.add(new Categories(category));
		properties.add(Clazz.PUBLIC);
		properties.add(Transp.TRANSPARENT);
		properties.add(Status.VEVENT_CONFIRMED);
		return event;
	}
}
