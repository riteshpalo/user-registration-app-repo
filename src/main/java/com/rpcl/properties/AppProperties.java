package com.rpcl.properties;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * This class is used to get String literal as a value from properties or yaml file
 * @author Ritesh
 *
 */
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {
	private Map<String, String> messages=new HashMap<>();

	public Map<String, String> getMessages() {
		return messages;
	}

	public void setMessages(Map<String, String> messages) {
		this.messages = messages;
	}

	
}
