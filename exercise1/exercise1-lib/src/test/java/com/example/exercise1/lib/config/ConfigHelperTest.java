package com.example.exercise1.lib.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConfigHelperTest {
	
	private ConfigHelper helper;
	
	@BeforeEach
	private void init() {
		helper = new ConfigHelper();
	}

	@Test
	public void configTest() throws Exception {
		helper.load("config/application.properties");
		assertEquals("reports/ipaddr.csv", helper.getValue("testStringValue", ""));
		assertEquals(10, helper.getIntValue("testIntValue", -1));
	}

	
	@Test
	public void configTestWithDefault() throws Exception {
		helper.load("config/application.properties");
		assertEquals("def", helper.getValue("not.present", "def"));
		assertEquals(-1, helper.getIntValue("not.present", -1));
	}
	
	
	@Test
	public void configTestWithEnv() throws Exception {
		helper.load("config/application.properties");
		var map = new HashMap<String, String>();
		map.put("home1", "value1");
		map.put("home2", "value2");
		helper.setEnv(map);
		assertEquals("value1", helper.getValue("testEnv1", ""));
		assertEquals("value2", helper.getValue("testEnv2", ""));
		assertEquals("value1-value2", helper.getValue("testEnv3", ""));
	}
	
	@Test
	public void configTestWithEnvAndDefault() throws Exception {
		helper.load("config/application.properties");
		var map = new HashMap<String, String>();
		map.put("home1", "value1");
		helper.setEnv(map);
		assertEquals("value1", helper.getValue("testEnv1", ""));
		assertEquals("defValue", helper.getValue("testEnv2", "defValue"));
	}
	
	@Test
	public void configTestWithNotValidInt() {
		assertThrows(NumberFormatException.class, ()->{
			helper.load("config/application.properties");
			assertEquals(10, helper.getIntValue("testStringValue", 10));
		});
	}
	
	@Test
	public void configTestWrongPath()  {
		assertThrows(Exception.class, ()->{
			helper.load("xx");
		});
	}
}
