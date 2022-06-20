package com.example.exercise1.app.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.exercise1.app.domain.ServiceException;
import com.example.exercise1.app.test.util.ResourceLoader;

public class RequestRepositoryTest {
	
	private RequestRepositoryImp service;
	
	@BeforeEach
    private void init(){
		service = new RequestRepositoryImp();
    }

	@Test
	public void getAll() throws URISyntaxException {
		var path = ResourceLoader.getPath("requests/simple.log");
		var expected = List.of(new Request(1655552184l, 2453l, HttpStatus.OK, "192.168.174.100"),
				new Request(1655550184l, 122453l, HttpStatus.BAD_REQUEST, "192.168.174.101"),
				new Request(1655552185l, 1023233l, HttpStatus.OK, "192.168.174.100"));
		var actual = service.getAll(path).toList();
		assertEquals(expected, actual);
	}
	
	@Test
	public void getAllNotNullUri() {
		assertThrows(ServiceException.class, ()->{
			service.getAll(null);
		});
	}
	
	@Test
	public void getAllNotValidUri() {
		assertThrows(ServiceException.class, ()->{
			var path = Path.of("xxxxx");
			service.getAll(path);
		});
	}
	
	
	@Test
	public void getAllNotValidCsv() {
		assertThrows(ServiceException.class, ()->{
			var path = ResourceLoader.getPath("requests/not-valid.log");
			service.getAll(path).toList();
		});
	}
}
