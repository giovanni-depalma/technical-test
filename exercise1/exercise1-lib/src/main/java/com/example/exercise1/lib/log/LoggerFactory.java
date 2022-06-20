package com.example.exercise1.lib.log;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

public class LoggerFactory {
	private static final String LOG_FILENAME = "logging.properties";
	static {
		init();
	}
	public static Logger getLogger(Class<?> clazz) {
		return Logger.getLogger(clazz.getName());
	}
	
	public static Logger getLogger(String name) {
		return Logger.getLogger(name);
	}
	
	private static void init() {
		try {
			List<File> files = List.of(new File(LOG_FILENAME),
					new File(LoggerFactory.class.getClassLoader().getResource(LOG_FILENAME).getFile()));
			for (File file : files) {
				if (file.exists()) {
					System.setProperty("java.util.logging.config.file", file.getAbsolutePath());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
