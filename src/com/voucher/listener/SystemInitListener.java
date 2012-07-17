package com.voucher.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.voucher.constants.WebConstants;
import com.voucher.service.RegionService;
import com.voucher.service.sys.SysFieldService;
import com.voucher.util.SpringContextHolder;

/**
 * 系统初始化监听器
 * 
 */
public class SystemInitListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();
		SysFieldService sysFieldService = SpringContextHolder.getBean("sysFieldService");
		RegionService regionService = SpringContextHolder.getBean("regionService");
		servletContext.setAttribute(WebConstants.FIELDS, sysFieldService.getAllEnabledSysFields());
		servletContext.setAttribute(WebConstants.CITYMAP, regionService.getAllEnabledCities());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
