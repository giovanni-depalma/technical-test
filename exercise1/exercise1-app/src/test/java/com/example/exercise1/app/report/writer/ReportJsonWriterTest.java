package com.example.exercise1.app.report.writer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.nio.file.Files;

import org.junit.jupiter.api.Test;

import com.example.exercise1.app.domain.ServiceException;
import com.example.exercise1.app.report.IpAddrReport;
import com.example.exercise1.app.test.util.ResourceLoader;

public class ReportJsonWriterTest {

	@Test
    public void testSuccess() throws IOException, URISyntaxException{
		StringWriter writer = new StringWriter();
		ReportJsonWriter jsonWriter = new ReportJsonWriter(writer);
		jsonWriter.start();
		jsonWriter.write(new IpAddrReport("ip1", 1, 2, 3, 4 ));
		jsonWriter.write(new IpAddrReport("ip2", 11, 22, 33, 44 ));
		jsonWriter.end();
		String actual = writer.toString();
		String expected = String.join(System.lineSeparator(),
				Files.readAllLines(ResourceLoader.getPath("reports/writer/expected.json")));
		assertEquals(expected, actual);
	}
	
	@Test
    public void testFailStart() {
		assertThrows(ServiceException.class, () -> {
			MockWriter writer = new MockWriter();
			ReportJsonWriter jsonWriter = new ReportJsonWriter(writer);
			writer.close();
			jsonWriter.start();
		});
	}
	
	@Test
    public void testFailClose() {
		assertThrows(ServiceException.class, () -> {
			MockWriter writer = new MockWriter();
			ReportJsonWriter jsonWriter = new ReportJsonWriter(writer);
			jsonWriter.start();
			writer.close();
			jsonWriter.end();
		});
	}
	
	@Test
    public void testFailWrite(){
		assertThrows(ServiceException.class, () -> {
			MockWriter writer = new MockWriter();
			ReportJsonWriter jsonWriter = new ReportJsonWriter(writer);
			jsonWriter.start();
			writer.close();
			jsonWriter.write(new IpAddrReport("ip1", 1, 2, 3, 4 ));
		});
	}
	
	
}
