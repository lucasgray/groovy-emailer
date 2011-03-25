package com.airdrawn.emailer.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesManager {
	
	private Map<String, String> props;

	private static PropertiesManager instance = null;

	private static final Logger logger = LoggerFactory.getLogger(PropertiesManager.class);
	
	private PropertiesManager() {
		ResourceBundle emailPropsFile;
		
		try {
			//http://jira.codehaus.org/browse/GROOVY-3274
			//http://stackoverflow.com/questions/2456541/grails-domain-class-properties-from-properties-file/2456869#2456869
			emailPropsFile = ResourceBundle.getBundle("emailer",Locale.getDefault(),Thread.currentThread().contextClassLoader);
		} catch (MissingResourceException e) {
			logger.error("you forgot to include emailer.properties somewhere.  This is needed to use the emailer.");
			throw e;
		}
		
		props = new HashMap<String,String>();
		
		Enumeration<String> propsEnumEmail = emailPropsFile.getKeys();
		while (propsEnumEmail.hasMoreElements()) {
			String key = propsEnumEmail.nextElement();
			String value = emailPropsFile.getString(key);
			
			logger.debug("setting up default properties: key: "+key+" value: "+value);
			
			props.put(key,value);
		}
	}

	private static void initialize() {
		instance = new PropertiesManager();
	}

	public static PropertiesManager getInstance() {
		if (instance == null)
			initialize();
		return instance;
	}

	/**
	 * Retrieves the property value
	 * 
	 * @param propertyKey
	 *            the property to lookup
	 * @return the property value
	 */
	public String getProperty(String propertyKey) {
		
		return props.get(propertyKey);
	}
	
	/**
	 * Retrieves a Map of the app properties
	 * @return the properties map
	 */
	public static Map<String,String> getProperties() {
		return getInstance().props;
	}
	
}
