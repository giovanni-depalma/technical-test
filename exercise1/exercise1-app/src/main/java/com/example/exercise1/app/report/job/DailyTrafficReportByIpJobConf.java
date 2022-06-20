package com.example.exercise1.app.report.job;

import java.net.URISyntaxException;
import java.nio.file.Path;

import com.example.exercise1.app.report.OutputMode;
import com.example.exercise1.lib.config.ConfigHelper;

public record DailyTrafficReportByIpJobConf(Path requestsPath, Path reportPath, Path reportPathTmp, OutputMode mode,
		int scheduledInSeconds) {

	private static final int DEFAULT_SCHEDULED_IN_SECONDS = 10;
	private static final OutputMode DEFAULT_OUTPUT_MODE = OutputMode.CSV;
	private static final String DEFAULT_BASE_PATH = "";

	public static class Builder {
		
		private ConfigHelper helper;
		
		public Builder() throws Exception {
			this("application.properties");
		}
		
		public Builder(String properties) throws Exception {
			helper = new ConfigHelper();
			helper.load(properties);
		}

		public DailyTrafficReportByIpJobConf build() throws Exception {
			String basePath = helper.getValue("main.basePath", DEFAULT_BASE_PATH);
			String reportPath = helper.getValue("main.report.dailyTrafficByIp.reportPath", "");
			String reportPathTmp = helper.getValue("main.report.dailyTrafficByIp.reportPathTmp", "");
			String requestPath = helper.getValue("main.report.dailyTrafficByIp.requestPath", "");
			String outputMode = helper.getValue("main.report.dailyTrafficByIp.outputMode",
					DEFAULT_OUTPUT_MODE.name());
			int scheduledInSeconds = helper.getIntValue("main.report.dailyTrafficByIp.scheduledInSeconds",
					DEFAULT_SCHEDULED_IN_SECONDS);
			
			String reportPathWithExt = reportPath + "."+outputMode.toLowerCase(); 
			
			return new DailyTrafficReportByIpJobConf(buildPath(basePath, requestPath), buildPath(basePath, reportPathWithExt),
					buildPath(basePath, reportPathTmp), OutputMode.valueOf(outputMode), scheduledInSeconds);
		}

		private Path buildPath(String basePath, String other) throws URISyntaxException {
			return Path.of(basePath + other);
		}
	}
}
