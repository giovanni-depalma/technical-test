package com.example.exercise1.app.report.job;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.exercise1.app.report.TrafficReportController;
import com.example.exercise1.lib.log.LoggerFactory;

public class DailyTrafficReportByIpJob {
	private static final Logger log = LoggerFactory.getLogger(DailyTrafficReportByIpJob.class);
	private DailyTrafficReportByIpJobConf configuration;
	private DailyTrafficReportByIpJobTask task;
	private ScheduledFuture<?> scheduledFuture;
	
	public DailyTrafficReportByIpJob(TrafficReportController controller, DailyTrafficReportByIpJobConf configuration) {
		this.configuration = configuration;
		this.task = new DailyTrafficReportByIpJobTask(controller, configuration);
		this.scheduledFuture = null;
	}
	
	public void start() {
		log.log(Level.INFO, "Starting scheduled report, configuration: {0}", configuration);
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		scheduledFuture = executor.scheduleWithFixedDelay(task, 0, configuration.scheduledInSeconds(), TimeUnit.SECONDS);
	}
	

	public void stop() {
		log.log(Level.INFO, "Stop scheduled report, configuration: {0}", configuration);
		scheduledFuture.cancel(true);
	}
}
