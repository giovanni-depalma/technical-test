package com.example.exercise1.app.report.job;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.example.exercise1.app.report.OutputMode;
import com.example.exercise1.app.report.TrafficReportByIpService;
import com.example.exercise1.app.report.TrafficReportController;
import com.example.exercise1.app.request.RequestRepository;
import com.example.exercise1.app.test.util.ResourceLoader;

public class DailyTrafficReportByIpJobTaskTest {
	
	private DailyTrafficReportByIpJobTask service;
	private TrafficReportController controller;
	private Path requestsPath;
	private Path reportPath;
	private Path reportPathTmp;
	private int scheduledInSeconds;
	
	@TempDir
	Path tempDir;
	
	@BeforeEach
	private void init()  throws Exception{
		requestsPath = ResourceLoader.getPath("reports/job/requests.log");
		reportPath = tempDir.resolve("reportPath");
		reportPathTmp = tempDir.resolve("reportPath.tmp");
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
				Files.readAllLines(reportPath));
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
				Files.readAllLines(reportPath));
		String expected = String.join(System.lineSeparator(),
				Files.readAllLines(ResourceLoader.getPath("reports/job/expected.csv")));
		assertEquals(expected, actual);
	}
	
	@Test
	public void testReportAlreadyPresent() throws Exception {
		OutputMode mode = OutputMode.CSV;
		String expected = "expected";
		Files.writeString(reportPath, expected);//simulate content report, service won't do anything
		DailyTrafficReportByIpJobConf configuration = new DailyTrafficReportByIpJobConf(requestsPath, reportPath,
				reportPathTmp, mode, scheduledInSeconds);
		service = new DailyTrafficReportByIpJobTask(controller, configuration);
		service.run();
		String actual = String.join(System.lineSeparator(),
				Files.readAllLines(reportPath));
		assertEquals(expected, actual);
	}
	
	
	@Test
	public void testFailureWithoutException() throws Exception {
		OutputMode mode = OutputMode.CSV;
		Path requestsPath = null;
		DailyTrafficReportByIpJobConf configuration = new DailyTrafficReportByIpJobConf(requestsPath, reportPath,
				reportPathTmp, mode, scheduledInSeconds);
		service = new DailyTrafficReportByIpJobTask(controller, configuration);
		service.run();
	}
	
	@Test
	public void testNoRequestPath() throws Exception {
		OutputMode mode = OutputMode.CSV;
		Path requestsPath = Path.of("not-present");
		DailyTrafficReportByIpJobConf configuration = new DailyTrafficReportByIpJobConf(requestsPath, reportPath,
				reportPathTmp, mode, scheduledInSeconds);
		service = new DailyTrafficReportByIpJobTask(controller, configuration);
		service.run();
	}
	
	@Test
	public void testNoReportParentDir() throws Exception {
		OutputMode mode = OutputMode.CSV;
		reportPath = tempDir.resolve("not-present/reportPath");
		reportPathTmp = tempDir.resolve("not-present/reportPath.tmp");
		DailyTrafficReportByIpJobConf configuration = new DailyTrafficReportByIpJobConf(requestsPath, reportPath,
				reportPathTmp, mode, scheduledInSeconds);
		service = new DailyTrafficReportByIpJobTask(controller, configuration);
		service.run();
	}
}
