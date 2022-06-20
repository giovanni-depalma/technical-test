package com.example.exercise1.app.request;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import com.example.exercise1.app.test.util.faker.Faker;
import com.example.exercise1.lib.csv.Csv;
import com.example.exercise1.lib.csv.CsvWriter;

public class RequestFakeGenerator {
	
	private static final int N_REQUESTS = 1000;
	private static final int N_IP = 20;
	
	public static void generate(File file) throws IOException {
		try (FileWriter writer = new FileWriter(file)) {
			var faker = new Faker();
			var validDayFrom = 5;
			var validDayTo = 4;
			var from = faker.date().past(validDayFrom, validDayTo, TimeUnit.DAYS);
			var to = faker.date().past(validDayTo, TimeUnit.DAYS);
			var builder = new RequestFakeBuilder(from, to, N_IP);
			CsvWriter csvWriter = new CsvWriter(Csv.SEMI_COLON, writer);
			for (Request req : builder.buildRequest(N_REQUESTS)) {
				String[] row = new String[] { String.valueOf(req.timestamp()), String.valueOf(req.bytes()),
						req.status().name(), req.remoteAddr() };
				csvWriter.write(row);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		Path path = Path.of("logfiles/requests.log");
		Files.deleteIfExists(path);
		generate(path.toFile());
		System.out.println("File generated: "+path);
	}
}
