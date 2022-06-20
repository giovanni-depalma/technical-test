package com.example.exercise1.app.report.job;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import com.example.exercise1.app.report.OutputMode;

public class DailyTrafficReportByIpJobConfTest {

	@Test
	public void testJsonOutput() throws Exception {
		Path requestsPath = Path.of("/logfiles/requests.log");
		Path reportPath = Path.of("/reports/ipaddr.json");
		Path reportPathTmp = Path.of("/reports/ipaddr.tmp");
		OutputMode mode = OutputMode.JSON;
		int scheduledInSeconds = 1;
		DailyTrafficReportByIpJobConf expected = new DailyTrafficReportByIpJobConf(requestsPath, reportPath, reportPathTmp, mode, scheduledInSeconds);
		DailyTrafficReportByIpJobConf actual = new DailyTrafficReportByIpJobConf.Builder("reports/job/conf-json.properties").build();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testCsvOutput() throws Exception {
		Path requestsPath = Path.of("/logfiles/requests.log");
		Path reportPath = Path.of("/reports/ipaddr.csv");
		Path reportPathTmp = Path.of("/reports/ipaddr.tmp");
		OutputMode mode = OutputMode.CSV;
		int scheduledInSeconds = 1;
		DailyTrafficReportByIpJobConf expected = new DailyTrafficReportByIpJobConf(requestsPath, reportPath, reportPathTmp, mode, scheduledInSeconds);
		DailyTrafficReportByIpJobConf actual = new DailyTrafficReportByIpJobConf.Builder("reports/job/conf-csv.properties").build();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testBasePathRelative() throws Exception {
		Path requestsPath = Path.of("logfiles/requests.log");
		Path reportPath = Path.of("reports/ipaddr.csv");
		Path reportPathTmp = Path.of("reports/ipaddr.tmp");
		OutputMode mode = OutputMode.CSV;
		int scheduledInSeconds = 1;
		DailyTrafficReportByIpJobConf expected = new DailyTrafficReportByIpJobConf(requestsPath, reportPath, reportPathTmp, mode, scheduledInSeconds);
		DailyTrafficReportByIpJobConf actual = new DailyTrafficReportByIpJobConf.Builder("reports/job/conf-basepath-relative.properties").build();
		assertEquals(expected, actual);
	}
}
