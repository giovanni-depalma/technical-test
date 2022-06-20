package com.example.exercise1.lib.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvWriterTest {
	
	private CsvWriter service;
	private StringWriter writer;
	
	@BeforeEach
	private void init() {
		writer = new StringWriter();
		service = new CsvWriter(Csv.COMMA, writer);
	}
	

	@Test
	public void simpleTest() throws IOException  {
		var data = List.of(new TestData("month", 2, 2323l, "wave"),
				new TestData("bat", 3,22l,"successful"),
				new TestData("modern",5,33l,"meat"));
		for(TestData row: data) {
			service.write(new String[] {row.v1, String.valueOf(row.v2), String.valueOf(row.v3), String.valueOf(row.v4)});
		}
		String actual = writer.toString();
		String expected = "month,2,2323,wave"+System.lineSeparator()
				+ "bat,3,22,successful"+System.lineSeparator()
				+ "modern,5,33,meat";
		assertEquals(expected, actual);
	}
	
	
	private static record TestData(String v1, int v2, long v3, String v4) {
		
	}
}
