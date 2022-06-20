package com.example.exercise1.app.request;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

import com.example.exercise1.app.domain.ServiceException;
import com.example.exercise1.lib.csv.Csv;
import com.example.exercise1.lib.csv.CsvReader;

public class RequestRepositoryImp implements RequestRepository{
	
	protected RequestRepositoryImp() {
		
	}
	
	@Override
	public Stream<Request> getAll(Path path) {
		if (path == null)
            throw new ServiceException("Missing path");
		try{
			return new CsvReader(Csv.SEMI_COLON).stream(path).map(this::parseRow);
		} catch (Exception e) {
			throw new ServiceException("problem reading path: "+path, e);
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
