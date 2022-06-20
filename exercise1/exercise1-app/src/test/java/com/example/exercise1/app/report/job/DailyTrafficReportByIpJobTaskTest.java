package com.example.exercise1.app.report.job;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.exercise1.app.report.OutputMode;
import com.example.exercise1.app.report.TrafficReportByIpService;
import com.example.exercise1.app.report.TrafficReportController;
import com.example.exercise1.app.request.RequestRepository;
import com.example.exercise1.app.test.util.ResourceLoader;

public class DailyTrafficReportByIpJobTaskTest {
	
	private DailyTrafficReportByIpJobTask service;
	private TrafficReportController controller;
	private URI requestsPath;
	private URI reportPath;
	private URI reportPathTmp;
	private int scheduledInSeconds;
	
	@BeforeEach
	private void init()  throws Exception{
		requestsPath = ResourceLoader.getURI("reports/job/requests.log");
		reportPath = File.createTempFile("reportPath", null).toURI();
		reportPathTmp = File.createTempFile("reportPath", "tmp").toURI();
		Files.deleteIfExists(Path.of(reportPath));
		scheduledInSeconds = 0;
		controller = new TrafficReportController(new RequestRepository(),
				new TrafficReportByIpService());
		
	}

	@Test
	public void testJson() throws Exception {
		OutputMode mode = OutputMode.JSON;
		DailyTrafficReportByIpJobConf configuration = new DailyTrafficReportByIpJobConf(requestsPath, reportPath,
				reportPathTmp, mode, scheduledInSeconds);
		service = new DailyTrafficReportByIpJobTask(controller, configuration);
		service.run();
		String actual = String.join(System.lineSeparator(),
				Files.readAllLines(Path.of(reportPath)));
		String expected = String.join(System.lineSeparator(),
				Files.readAllLines(ResourceLoader.getPath("reports/job/expected.json")));
		assertEquals(expected, actual);
	}
	
	@Test
	public void testCsv() throws Exception {
		OutputMode mode = OutputMode.CSV;
		DailyTrafficReportByIpJobConf configuration = new DailyTrafficReportByIpJobConf(requestsPath, reportPath,
				reportPathTmp, mode, scheduledInSeconds);
		service = new DailyTrafficReportByIpJobTask(controller, configuration);
		service.run();
		String actual = String.join(System.lineSeparator(),
				Files.readAllLines(Path.of(reportPath)));
		String expected = String.join(System.lineSeparator(),
				Files.readAllLines(ResourceLoader.getPath("reports/job/expected.csv")));
		assertEquals(expected, actual);
	}
	
	@Test
	public void testReportAlreadyPresent() throws Exception {
		OutputMode mode = OutputMode.CSV;
		String expected = "expected";
		Files.writeString(Path.of(reportPath), expected);//simulate content report, service won't do anything
		DailyTrafficReportByIpJobConf configuration = new DailyTrafficReportByIpJobConf(requestsPath, reportPath,
				reportPathTmp, mode, scheduledInSeconds);
		service = new DailyTrafficReportByIpJobTask(controller, configuration);
		service.run();
		String actual = String.join(System.lineSeparator(),
				Files.readAllLines(Path.of(reportPath)));
		assertEquals(expected, actual);
	}
	
	
	@Test
	public void testFailureWithoutException() throws Exception {
		OutputMode mode = OutputMode.CSV;
		URI requestsPath = null;
		DailyTrafficReportByIpJobConf configuration = new DailyTrafficReportByIpJobConf(requestsPath, reportPath,
				reportPathTmp, mode, scheduledInSeconds);
		service = new DailyTrafficReportByIpJobTask(controller, configuration);
		service.run();
	}
}
