package com.example.exercise1.lib.config;

import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigHelper {
	
	private Properties props = new Properties();
	private Map<String, String> env;
	
	public ConfigHelper() {
		env = System.getenv();
	}
	
	public void setEnv(Map<String, String> env) {
		this.env = env;
	}

	public void load(String resourcePath) throws Exception {
		props.load(this.getClass().getClassLoader().getResourceAsStream(resourcePath));
	}

	public String getValue(String key, String defaultValue) {
		String text = props.getProperty(key, defaultValue);
		if(text == null)
			return null;
		else {
			String resolved = resolveValueWithEnvVars(text);
			return resolved.isBlank() ? defaultValue : resolved;
		}
	}
	
	public int getIntValue(String key, int defaultValue) {
		String text = getValue(key, null);
		return text == null ? defaultValue : Integer.parseInt(text);
	}
	
	private String resolveValueWithEnvVars(String value) {
	    Pattern p = Pattern.compile("\\$\\{(\\w+)\\}|\\$(\\w+)");
	    Matcher m = p.matcher(value);
	    StringBuilder sb = new StringBuilder();
	    while (m.find()) {
	        String envVarName = null == m.group(1) ? m.group(2) : m.group(1);
	        String envVarValue = env.get(envVarName);
	        m.appendReplacement(sb,
	            null == envVarValue ? "" : Matcher.quoteReplacement(envVarValue));
	     }
	    m.appendTail(sb);
	    return sb.toString();
	  }
}
