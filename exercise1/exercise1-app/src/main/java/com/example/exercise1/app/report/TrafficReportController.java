package com.example.exercise1.app.report;

import java.io.Writer;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.exercise1.app.report.writer.ReportFactory;
import com.example.exercise1.app.report.writer.ReportWriter;
import com.example.exercise1.lib.log.LoggerFactory;

public class TrafficReportController {
	private static final Logger log = LoggerFactory.getLogger(TrafficReportController.class);
	private final TrafficReportByIpService reportService;
	
	public TrafficReportController(TrafficReportByIpService reportService) {
		super();
		this.reportService = reportService;
	}

	public void report(Path requests, long from, long to, OutputMode mode, Writer writer) {
		log.log(Level.FINE, "report, requests: {0}, from: {1}, to: {2}, mode: {3}", new Object[] {requests, from, to, mode});
		ReportWriter reportWriter = ReportFactory.getReportWriter(mode, writer);
		reportWriter.start();
		reportService.doReport(requests, from, to, reportWriter::write);
		reportWriter.end();
	}
	
}
