package com.voucher.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
	private static final String INFO_NAME = "vchsys.properties";
	
	private static PropertiesLoader instance = new PropertiesLoader();
	
	private PropertiesLoader() {
		
	}
	
	public static PropertiesLoader getInstance() {
		return instance;
	}
	
	public String getShopImageBaseUrl() {
		Properties prop = loadProperties();
		String path = (String) prop.get("shop.img.url");
		return path;
	}
	
	public String getVoucherImageBaseUrl() {
		Properties prop = loadProperties();
		String path = (String) prop.get("voucher.img.url");
		return path;
	}
	
	private String getImagePathByKey(String key) {
		Properties prop = loadProperties();
		String path = (String) prop.get(key);
		return path;
	}
	
	private Properties loadProperties() {
		Properties prop = new Properties();
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(INFO_NAME);
		if(is != null) {
			try {
				prop.load(is);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return prop;
	}
}
