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
	
	public String getSmsPrefix() {
		return getImagePathByKey("sms.msg.prefix");
	}
	
	public String getShopImageBaseUrl() {
		return getImagePathByKey("shop.img.url");
	}
	
	public String getVoucherImageBaseUrl() {
		return getImagePathByKey("voucher.img.url");
	}
	
	public String getCompanyId() {
		return getImagePathByKey("com.id");
	}
	public String getUserName() {
		return getImagePathByKey("user.name");
	}
	public String getUserPwd() {
		return getImagePathByKey("user.pwd");
	}
	public String getSmsNumber() {
		return getImagePathByKey("sms.number");
	}
	
	public String getImagePathByKey(String key) {
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
