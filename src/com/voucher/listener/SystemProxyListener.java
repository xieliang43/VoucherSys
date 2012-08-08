package com.voucher.listener;

import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

/**
 * 
 */
public class SystemProxyListener implements ServletContextListener {
	
	private static final Logger logger = Logger.getLogger(SystemProxyListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("Set system proxy address");
		Properties prop = System.getProperties();
		// HTTP代理的IP设置
		prop.setProperty("http.proxyHost", "127.0.0.1");
		// HTTP代理的端口设置
		prop.setProperty("http.proxyPort", "8087");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
