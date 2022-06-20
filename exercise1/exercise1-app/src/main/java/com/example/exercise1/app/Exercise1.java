package com.example.exercise1.app;

import java.util.logging.Logger;

import com.example.exercise1.app.report.TrafficReportByIpService;
import com.example.exercise1.app.report.TrafficReportController;
import com.example.exercise1.app.report.job.DailyTrafficReportByIpJob;
import com.example.exercise1.app.report.job.DailyTrafficReportByIpJobConf;
import com.example.exercise1.app.request.RequestRepository;
import com.example.exercise1.lib.log.LoggerFactory;

public class Exercise1 {
	
	private static final Logger log = LoggerFactory.getLogger(Exercise1.class);
	private final DailyTrafficReportByIpJobConf configuration;
	
	public Exercise1(DailyTrafficReportByIpJobConf configuration) {
		this.configuration = configuration;
	}
	
	public void start() throws Exception {
		log.info("Starting app...");
		TrafficReportController controller = new TrafficReportController(new RequestRepository(), new TrafficReportByIpService());
		DailyTrafficReportByIpJob job = new DailyTrafficReportByIpJob(controller, configuration);
		job.start();
		log.info("App started, press Ctrl + C to terminate");
		Thread haltedHook = new Thread(() -> {
			System.out.println("Terminating app...");
			job.stop();
			System.out.println("Terminated");
			
		});
		Runtime.getRuntime().addShutdownHook(haltedHook);
	}

	public static void main(String[] args) throws Exception {
		new Exercise1(new DailyTrafficReportByIpJobConf.Builder().build()).start();
	}

	
}
