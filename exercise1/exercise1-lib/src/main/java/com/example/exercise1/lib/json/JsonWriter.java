package com.example.exercise1.lib.json;

import java.io.IOException;
import java.io.Writer;

public class JsonWriter {

	private final Writer writer;
	private int nTab;
	private boolean startingDocument;
	private boolean avoidComma;

	public JsonWriter(Writer writer) {
		super();
		this.writer = writer;
		nTab=0;
		startingDocument = true;
		avoidComma = true;
	}
	
	public void write(String key, Object value) throws IOException {
		String text = getJsonValue(key)+":"+getJsonValue(value);
		writeLine(text);
		avoidComma = false;
	}
	
	private String getJsonValue(Object value) {
		String surroundBy = value instanceof String ? Json.QUOTE : "";
		return surroundBy+escapeValue(String.valueOf(value))+surroundBy;
	}
	
	private String getComma() {
		return avoidComma ? "": Json.COMMA;
	}
	
	private String escapeValue(String value) {
		return value;
	}

	public void startArray() throws IOException {
		writeLine("[");
		nTab++;
		avoidComma = true;
	}
	
	public void endArray() throws IOException {
		avoidComma = true;
		nTab--;
		writeLine("]");
		avoidComma = false;
	}
	
	public void startObject() throws IOException {
		writeLine("{");
		nTab++;
		avoidComma = true;
	}
	
	public void endObject() throws IOException {
		avoidComma = true;
		nTab--;
		writeLine("}");
		avoidComma = false;
	}
	
	private void writeLine(String text) throws IOException {
		writer.write(getComma()+newLine()+getTabs()+text);
	}
	
	
	private String newLine() {
		if(startingDocument) {
			startingDocument = false;
			return "";
		}
		else
			return System.lineSeparator();
	}

	private String getTabs() {
		return Json.INDENT.repeat(nTab);
	}
}
