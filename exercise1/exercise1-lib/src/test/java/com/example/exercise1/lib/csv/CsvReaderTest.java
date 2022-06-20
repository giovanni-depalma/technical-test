package com.example.exercise1.lib.csv;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.exercise1.app.test.util.ResourceLoader;

public class CsvReaderTest {
	
	private CsvReader service;
	
	@BeforeEach
	private void init() {
		service = new CsvReader(Csv.COMMA);
	}

	@Test
	public void simpleTest() throws IOException, URISyntaxException {
		var expected = List.of(new String[] {"month", "declared", "spring", "wave", "fresh", "somehow"},
				new String[] {"bat", "sometime","began","successful","stay","neighbor"},
				new String[] {"modern","month","everyone","meat","effect","applied"});
		var actual = service.stream(ResourceLoader.getURI("csv/simple.csv")).toList();
		assertEquals(expected.size(), actual.size());
		for(int i = 0; i < expected.size(); i++) {
			assertArrayEquals(expected.get(i), actual.get(i));
		}
	}
	
	@Test
	public void emptyTest() throws IOException, URISyntaxException {
		var actual = service.stream(ResourceLoader.getURI("csv/empty.csv")).toList();
		assertTrue(actual.isEmpty());
	}
	
}
