package com.example.exercise1.app.report.writer;

import java.io.IOException;
import java.io.Writer;

import com.example.exercise1.app.domain.ServiceException;
import com.example.exercise1.app.report.IpAddrReport;
import com.example.exercise1.lib.json.JsonWriter;

public class ReportJsonWriter implements ReportWriter {
	
	private final JsonWriter writer;	

	protected ReportJsonWriter(Writer writer) {
		super();
		this.writer = new JsonWriter(writer);
	}

	@Override
	public void write(IpAddrReport item){
		try {
			writer.startObject();
			writer.write("ip", item.ipAddress());
			writer.write("nRequests", item.nRequests());
			writer.write("percentageRequests", item.percentageRequests());
			writer.write("totalBytes", item.totalBytes());
			writer.write("percentageBytes", item.percentageBytes());
			writer.endObject();
		} catch (Exception e) {
			throw new ServiceException("error with element: "+item,e);
		}
	}

	@Override
	public void start() {
		try {
			writer.startArray();
		} catch (IOException e) {
			throw new ServiceException("error init report",e);
		}
	}

	@Override
	public void end() {
		try {
			writer.endArray();
		} catch (IOException e) {
			throw new ServiceException("error end report",e);
		}
	}

}
