package com.voucher.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import com.voucher.pojo.ExtGridReturn;
import com.voucher.pojo.ExtReturn;
import com.voucher.pojo.JsonVO;

public class BaseAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8647134177875947159L;

	protected HttpServletResponse getHttpServletResponse() {
		return ServletActionContext.getResponse();
	}
	
	protected String convertToJson(Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	}
	
	protected void sendErrorResoponse(String msg) {
		JsonVO jErrorVO = new JsonVO("0", msg, null);
		String json = this.convertToJson(jErrorVO);
		getHttpServletResponse().setCharacterEncoding("UTF-8");
		getHttpServletResponse().setContentType("application/json;charset=UTF-8");
		try {
			getHttpServletResponse().getWriter().println(json);
			getHttpServletResponse().getWriter().flush();
			getHttpServletResponse().getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void sendExtReturn(ExtReturn extReturn){
		String json = this.convertToJson(extReturn);
		getHttpServletResponse().setCharacterEncoding("UTF-8");
		try {
			getHttpServletResponse().getWriter().println(json);
			getHttpServletResponse().getWriter().flush();
			getHttpServletResponse().getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void sendExtGridReturn(ExtGridReturn extGridReturn){
		String json = this.convertToJson(extGridReturn);
		getHttpServletResponse().setCharacterEncoding("UTF-8");
		try {
			getHttpServletResponse().getWriter().println(json);
			getHttpServletResponse().getWriter().flush();
			getHttpServletResponse().getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void sendJSonReturn(String json){
		getHttpServletResponse().setCharacterEncoding("UTF-8");
		getHttpServletResponse().setContentType("application/json;charset=UTF-8");
		try {
			getHttpServletResponse().getWriter().println(json);
			getHttpServletResponse().getWriter().flush();
			getHttpServletResponse().getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
