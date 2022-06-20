package com.example.exercise1.app.report.writer;

import java.io.IOException;
import java.io.Writer;

public class MockWriter extends Writer{
	private boolean closed = false;
	
	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		if(closed)
			throw new IOException("closed writer");
	}
	
	@Override
	public void flush() throws IOException {
		
	}
	
	@Override
	public void close() throws IOException {
		closed = true;
	}
}
