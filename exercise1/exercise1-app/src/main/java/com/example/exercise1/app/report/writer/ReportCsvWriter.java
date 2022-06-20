package com.example.exercise1.app.report.writer;

import java.io.Writer;

import com.example.exercise1.app.domain.ServiceException;
import com.example.exercise1.app.report.IpAddrReport;
import com.example.exercise1.lib.csv.Csv;
import com.example.exercise1.lib.csv.CsvWriter;

public class ReportCsvWriter implements ReportWriter {
	
	private final CsvWriter writer;	

	protected ReportCsvWriter(Writer writer) {
		super();
		this.writer = new CsvWriter(Csv.SEMI_COLON, writer);
	}

	@Override
	public void write(IpAddrReport item){
		try {
			String[] row = new String[] {
					item.ipAddress(), 
					String.valueOf(item.nRequests()), 
					String.valueOf(item.percentageRequests()), 
					String.valueOf(item.totalBytes()), 
					String.valueOf(item.percentageBytes())
					};
			writer.write(row);
		} catch (Exception e) {
			throw new ServiceException("error with element: "+item,e);
		}
	}

	@Override
	public void start() {
		
	}

	@Override
	public void end() {
		
	}

}
