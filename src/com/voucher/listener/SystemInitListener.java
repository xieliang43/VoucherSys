package com.voucher.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.voucher.constants.WebConstants;
import com.voucher.service.sys.SysFieldService;
import com.voucher.util.SpringContextHolder;

/**
 * 系统初始化监听器
 * 
 * @author chenxin
 * @date 2011-12-16 下午11:26:14
 */
public class SystemInitListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();
		SysFieldService sysFieldService = SpringContextHolder.getBean("sysFieldService");
		servletContext.setAttribute(WebConstants.FIELDS, sysFieldService.getAllEnabledSysFields());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
