package com.example.exercise1.app.test.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

public class ResourceLoader {

	public static URI getURI(String path) throws URISyntaxException {
		URL url = ResourceLoader.class.getClassLoader().getResource(path);
		return url == null ? null : url.toURI();
	}
	
	public static Path getPath(String path) throws URISyntaxException {
		return Path.of(getURI(path));
	}
}
