package com.example.exercise1.app.report;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.exercise1.app.request.RequestRepository;
import com.example.exercise1.app.test.util.ResourceLoader;

public class TrafficReportControllerTest {

	private RequestRepository repository;
	private TrafficReportByIpService service;
	private TrafficReportController controller;

	@BeforeEach
	private void init() {
		repository = new RequestRepository();
		service = new TrafficReportByIpService();
		controller = new TrafficReportController(repository, service);
	}

	@Test
	public void simpleTestCsv() throws URISyntaxException, IOException {
		long from = 1655552184l;
		long to = 1655552185;
		URI requests = ResourceLoader.getURI("requests/simple.log");
		StringWriter writer = new StringWriter();
		controller.report(requests, from, to, OutputMode.CSV, writer);
		String expected = String.join(System.lineSeparator(),
				Files.readAllLines(ResourceLoader.getPath("reports/simple-expected.csv")));
		String actual = writer.toString();
		assertEquals(expected, actual);
	}

	@Test
	public void simpleTestJson() throws URISyntaxException, IOException {
		long from = 1655552184l;
		long to = 1655552185;
		URI requests = ResourceLoader.getURI("requests/simple.log");
		StringWriter writer = new StringWriter();
		controller.report(requests, from, to, OutputMode.JSON, writer);
		String expected = String.join(System.lineSeparator(),
				Files.readAllLines(ResourceLoader.getPath("reports/simple-expected.json")));
		String actual = writer.toString();
		assertEquals(expected, actual);
	}

	@Test
	public void simple2TestJson() throws URISyntaxException, IOException {
		long from = 1655552184l;// exclude items with timestamp 1655552183;
		long to = 1655552190l;// exclude items with timestamp 1655552191;
		URI requests = ResourceLoader.getURI("requests/simple2.log");
		StringWriter writer = new StringWriter();
		controller.report(requests, from, to, OutputMode.JSON, writer);
		String expected = String.join(System.lineSeparator(),
				Files.readAllLines(ResourceLoader.getPath("reports/simple2-expected.json")));
		String actual = writer.toString();
		assertEquals(expected, actual);
	}
}
