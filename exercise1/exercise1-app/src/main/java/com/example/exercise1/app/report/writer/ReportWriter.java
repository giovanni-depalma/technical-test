package com.example.exercise1.app.report.writer;

import com.example.exercise1.app.report.IpAddrReport;

public interface ReportWriter {
	
	void start();	
	
	void write(IpAddrReport report);	
	
	void end();	
}
