package com.example.exercise1.app;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.example.exercise1.app.report.OutputMode;
import com.example.exercise1.app.report.job.DailyTrafficReportByIpJobConf;
import com.example.exercise1.app.test.util.ResourceLoader;

public class Exercise1Test {

	private DailyTrafficReportByIpJobConf configuration;
	
	@TempDir
	Path tempDir;
	
	@BeforeEach
	private void init()  throws Exception{
		Path requestsPath = ResourceLoader.getPath("reports/job/requests.log");
		Path reportPath = tempDir.resolve("reportPath");
		Path reportPathTmp = tempDir.resolve("reportPath.tmp");
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
