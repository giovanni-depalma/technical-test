package com.example.exercise1.app.request;

import java.net.URI;
import java.util.Arrays;
import java.util.stream.Stream;

import com.example.exercise1.app.domain.ServiceException;
import com.example.exercise1.lib.csv.Csv;
import com.example.exercise1.lib.csv.CsvReader;

public class RequestRepository {
	
	public Stream<Request> getAll(URI uri) {
		if (uri == null)
            throw new ServiceException("Missing uri");
		try{
			return new CsvReader(Csv.SEMI_COLON).stream(uri).map(this::parseRow);
		} catch (Exception e) {
			throw new ServiceException("problem reading uri: "+uri, e);
		}

	}
	
	private Request parseRow(String[] row) {
		try {
			long timestamp = Long.parseLong(row[0]);
			long bytes = Long.parseLong(row[1]);
			HttpStatus status = HttpStatus.valueOf(row[2]);
			String remoteAddr = row[3];
			return new Request(timestamp, bytes, status, remoteAddr);
		} catch (Exception e) {
			throw new ServiceException("not valid row: "+Arrays.toString(row), e);
		}
	}

}
