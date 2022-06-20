package com.example.exercise1.lib.csv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Basic Implementation.
 * 
 * Do not use in production, more sophisticated CSVs (e.g quoting or including commas as values) will not be parsed as intended
 * @author giovanni.depalma
 *
 */
public class CsvReader {

	private final String delimiter;

	public CsvReader(String delimiter) {
		super();
		this.delimiter = delimiter;
	}
	
	public Stream<String[]> stream(Path path) throws IOException{
		return Files.lines(path).map(line -> line.split(delimiter));
	}

}
