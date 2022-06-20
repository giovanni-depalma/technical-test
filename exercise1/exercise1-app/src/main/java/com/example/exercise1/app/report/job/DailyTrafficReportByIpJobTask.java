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
	
	protected DailyTrafficReportByIpJobTask(TrafficReportController controller, DailyTrafficReportByIpJobConf configuration) {
		this.configuration = configuration;
		this.controller = controller;
	}
	
	@Override
	public void run() {
		try {
			if(existRequestPath()) {
				Path reportPath = configuration.reportPath();
				if(Files.exists(reportPath)) {
					log.log(Level.FINE,"report already generated in path: {0}", reportPath);
					//Do nothing
				}
				else {
					Path reportPathTmp = configuration.reportPathTmp();
					checkParentPaths(reportPath, reportPathTmp);
					doReport(reportPath, reportPathTmp);
				}
			}
		} catch (Exception e) {
			log.log(Level.SEVERE, "error during task {0}", e);
		}
	}
	
	private boolean existRequestPath() {
		Path requestsPath = configuration.requestsPath();
		if(!Files.exists(requestsPath)) {
			log.log(Level.FINE,"request paths not present: {0}", requestsPath);
			return false;
		}
		else
			return true;
	}
	
	private void checkParentPaths(Path... paths) throws IOException {
		for(Path path: paths) {
			Path parent = path.getParent();
			if(!Files.exists(parent)) {
				log.log(Level.FINE,"creating path: {0}", parent);
				Files.createDirectories(parent);
			}
		}
	}
	
	private void doReport(Path reportPath, Path reportPathTmp) throws IOException {
		log.info(MessageFormat.format("generating report in path: {0}", reportPath));
		Files.deleteIfExists(reportPathTmp);
		doReportTmp(reportPathTmp);
		Files.move(reportPathTmp, reportPath);
		log.info(MessageFormat.format("generated report in path: {0}", reportPath));
	}
	
	private void doReportTmp(Path reportPathTmp) throws IOException {
		try(BufferedWriter writer = Files.newBufferedWriter(reportPathTmp, Charset.forName("UTF-8"))){
			long from = 0;
			long to = Long.MAX_VALUE;//include any values inside requests log
			controller.report(configuration.requestsPath(), from, to, configuration.mode(), writer);
		}
	}

	
}
