package com.example.exercise1.app.report.job;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.net.URI;

import org.junit.jupiter.api.Test;

import com.example.exercise1.app.report.OutputMode;

public class DailyTrafficReportByIpJobConfTest {

	@Test
	public void testJsonOutput() throws Exception {
		URI requestsPath = new File("/logfiles/requests.log").toURI();
		URI reportPath = new File("/reports/ipaddr.json").toURI();
		URI reportPathTmp = new File("/reports/ipaddr.tmp").toURI();
		OutputMode mode = OutputMode.JSON;
		int scheduledInSeconds = 1;
		DailyTrafficReportByIpJobConf expected = new DailyTrafficReportByIpJobConf(requestsPath, reportPath, reportPathTmp, mode, scheduledInSeconds);
		DailyTrafficReportByIpJobConf actual = new DailyTrafficReportByIpJobConf.Builder("reports/job/conf-json.properties").build();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testCsvOutput() throws Exception {
		URI requestsPath = new File("/logfiles/requests.log").toURI();
		URI reportPath = new File("/reports/ipaddr.csv").toURI();
		URI reportPathTmp = new File("/reports/ipaddr.tmp").toURI();
		OutputMode mode = OutputMode.CSV;
		int scheduledInSeconds = 1;
		DailyTrafficReportByIpJobConf expected = new DailyTrafficReportByIpJobConf(requestsPath, reportPath, reportPathTmp, mode, scheduledInSeconds);
		DailyTrafficReportByIpJobConf actual = new DailyTrafficReportByIpJobConf.Builder("reports/job/conf-csv.properties").build();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testBasePathRelative() throws Exception {
		URI requestsPath = new File("logfiles/requests.log").toURI();
		URI reportPath = new File("reports/ipaddr.csv").toURI();
		URI reportPathTmp = new File("reports/ipaddr.tmp").toURI();
		OutputMode mode = OutputMode.CSV;
		int scheduledInSeconds = 1;
		DailyTrafficReportByIpJobConf expected = new DailyTrafficReportByIpJobConf(requestsPath, reportPath, reportPathTmp, mode, scheduledInSeconds);
		DailyTrafficReportByIpJobConf actual = new DailyTrafficReportByIpJobConf.Builder("reports/job/conf-basepath-relative.properties").build();
		assertEquals(expected, actual);
	}
}
