package com.example.exercise1.app.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.exercise1.app.domain.ServiceException;
import com.example.exercise1.app.test.util.ResourceLoader;

public class RequestRepositoryTest {
	
	private RequestRepository service;
	
	@BeforeEach
    private void init(){
		service = new RequestRepository();
    }

	@Test
	public void getAll() throws URISyntaxException {
		URI uri = ResourceLoader.getURI("requests/simple.log");
		var expected = List.of(new Request(1655552184l, 2453l, HttpStatus.OK, "192.168.174.100"),
				new Request(1655550184l, 122453l, HttpStatus.BAD_REQUEST, "192.168.174.101"),
				new Request(1655552185l, 1023233l, HttpStatus.OK, "192.168.174.100"));
		var actual = service.getAll(uri).toList();
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
			URI uri = new URI("xxxxx");
			service.getAll(uri);
		});
	}
	
	
	@Test
	public void getAllNotValidCsv() {
		assertThrows(ServiceException.class, ()->{
			URI uri = ResourceLoader.getURI("requests/not-valid.log");
			service.getAll(uri).toList();
		});
	}
}
