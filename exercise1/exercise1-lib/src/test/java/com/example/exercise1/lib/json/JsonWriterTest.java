package com.example.exercise1.lib.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.exercise1.app.test.util.ResourceLoader;

public class JsonWriterTest {
	
	private JsonWriter service;
	private StringWriter writer;
	
	@BeforeEach
	private void init() {
		writer = new StringWriter();
		service = new JsonWriter(writer);
	}
	

	@Test
	public void simpleTest() throws IOException, URISyntaxException  {
		var data = List.of(new TestData("month", 2, 2323l, "wave"),
				new TestData("bat", 3,22l,"successful"),
				new TestData("modern",5,33l,"meat"));
		service.startArray();
		for(TestData row: data) {
			service.startObject();
			service.write("v1",row.v1);
			service.write("v2",row.v2);
			service.write("v3",row.v3);
			service.write("v4",row.v4);
			service.endObject();
		}
		service.endArray();
		String actual = writer.toString();
		String expected = String.join(System.lineSeparator(),
				Files.readAllLines(ResourceLoader.getPath("json/simple-expected.json")));
		assertEquals(expected, actual);
	}
	
	
	private static record TestData(String v1, int v2, long v3, String v4) {
		
	}
}
