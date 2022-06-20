package com.example.exercise1.app.report.writer;

import java.io.Writer;

import com.example.exercise1.app.report.OutputMode;

public class ReportFactory {

	public static ReportWriter getReportWriter(OutputMode mode, Writer writer) {
		return switch (mode) {
		case CSV -> new ReportCsvWriter(writer);
		case JSON -> new ReportJsonWriter(writer);
		};
	}
}
