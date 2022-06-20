package com.example.exercise1.app;

import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.exercise1.app.report.OutputMode;
import com.example.exercise1.app.report.job.DailyTrafficReportByIpJobConf;
import com.example.exercise1.app.test.util.ResourceLoader;

public class Exercise1Test {

	private DailyTrafficReportByIpJobConf configuration;
	
	@BeforeEach
	private void init()  throws Exception{
		URI requestsPath = ResourceLoader.getURI("reports/job/requests.log");
		URI reportPath = File.createTempFile("reportPath", null).toURI();
		URI reportPathTmp = File.createTempFile("reportPath", "tmp").toURI();
		Files.deleteIfExists(Path.of(reportPath));
		int scheduledInSeconds = 1;
		OutputMode mode = OutputMode.JSON;
		configuration = new DailyTrafficReportByIpJobConf(requestsPath, reportPath,
				reportPathTmp, mode, scheduledInSeconds);
		
	}
	
	@Test
	public void test() throws Exception {
		Exercise1 app = new Exercise1(configuration);
		app.start();
	}
}
