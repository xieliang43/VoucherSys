package com.voucher.listener;

import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.voucher.util.PropertiesLoader;

/**
 * 
 */
public class SystemProxyListener implements ServletContextListener {
	private static final Logger logger = Logger.getLogger(SystemProxyListener.class);
	
	private static final String PROXY_PORT = "proxyPort";
	private static final String PROXY_HOST = "proxyHost";
	
	private static final String PORT = PropertiesLoader.getInstance().getProxyPort();
	private static final String HOST = PropertiesLoader.getInstance().getProxyHost();

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("Set system proxy address");
		Properties prop = System.getProperties();
		// HTTP代理的IP设置
		prop.setProperty(PROXY_HOST, HOST);
		// HTTP代理的端口设置
		prop.setProperty(PROXY_PORT, PORT);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
