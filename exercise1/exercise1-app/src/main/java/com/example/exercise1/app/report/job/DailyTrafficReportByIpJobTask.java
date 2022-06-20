package com.example.exercise1.app.report.job;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.exercise1.app.report.TrafficReportController;
import com.example.exercise1.lib.log.LoggerFactory;

public class DailyTrafficReportByIpJobTask implements Runnable{
	private static final Logger log = LoggerFactory.getLogger(DailyTrafficReportByIpJobTask.class);
	private DailyTrafficReportByIpJobConf configuration;
	private TrafficReportController controller;
	
	public DailyTrafficReportByIpJobTask(TrafficReportController controller, DailyTrafficReportByIpJobConf configuration) {
		this.configuration = configuration;
		this.controller = controller;
	}
	
	@Override
	public void run() {
		try {
			Path reportPath = configuration.reportPath();
			if(Files.exists(reportPath)) {
				log.fine(MessageFormat.format("report already generated in path: {0}", reportPath));
				//Do nothing
			}
			else {
				doReport(reportPath);
			}
		} catch (Exception e) {
			log.log(Level.SEVERE, "error during task {0}", e);
		}
	}
	
	private void doReport(Path reportPath) throws IOException {
		log.info(MessageFormat.format("generating report in path: {0}", reportPath));
		Path reportPathTmp = configuration.reportPathTmp();
		Files.deleteIfExists(reportPathTmp);
		doReportTmp(reportPathTmp);
		Files.move(reportPathTmp, reportPath);
		log.info(MessageFormat.format("generated report in path: {0}", reportPath));
	}
	
	private void doReportTmp(Path reportPathTmp) throws IOException {
		try(BufferedWriter writer = Files.newBufferedWriter(reportPathTmp, Charset.forName("UTF-8"))){
			long from = 0;
			long to = Long.MAX_VALUE;//include any values inside requests log
			controller.report(configuration.requestsPath().toUri(), from, to, configuration.mode(), writer);
		}
	}

	
}
