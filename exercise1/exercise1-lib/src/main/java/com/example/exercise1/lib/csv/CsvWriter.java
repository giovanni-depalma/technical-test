package com.example.exercise1.lib.csv;

import java.io.IOException;
import java.io.Writer;

/**
 * Basic Implementation.
 * 
 * Do not use in production
 * @author giovanni.depalma
 *
 */
public class CsvWriter {
	private final String delimiter;
	private final Writer writer;
	private boolean startingDocument;

	public CsvWriter(String delimiter, Writer writer) {
		super();
		this.delimiter = delimiter;
		this.writer = writer;
		this.startingDocument = true;
	}
	
	public void write(String[] row) throws IOException {
		writer.write(newLine()+String.join(delimiter, row));
	}
	
	private String newLine() {
		if(startingDocument) {
			startingDocument = false;
			return "";
		}
		else
			return System.lineSeparator();
	}
	
}
