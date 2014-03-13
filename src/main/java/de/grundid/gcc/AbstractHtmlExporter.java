package de.grundid.gcc;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class AbstractHtmlExporter {

	protected String template;

	public AbstractHtmlExporter(String templateName) {
		try {
			template = FileUtils.readFileToString(new File(templateName), "utf8");
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
