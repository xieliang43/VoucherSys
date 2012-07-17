package com.voucher.action;

import org.apache.commons.lang.StringUtils;

import com.voucher.entity.Advice;
import com.voucher.pojo.JsonVO;
import com.voucher.service.AdviceService;

public class AdviceAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7630293551987547372L;
	
	private AdviceService adviceService;
	
	private String msg;

	public void sendAdvice() {
		if(StringUtils.isBlank(msg)) {
			JsonVO jErrorVO = new JsonVO("0", "请输入建议信息！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		
		Advice advice = new Advice();
		advice.setMsg(msg);
		adviceService.saveAdvice(advice);
		
		JsonVO jVO = new JsonVO("1", "发表建议成功，谢谢你的建议！", null);
		String json = this.convertToJson(jVO);
		sendJSonReturn(json);
	}
	
	public void loadAll() {
		
	}
	
	public void delete() {
		
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the adviceService
	 */
	public AdviceService getAdviceService() {
		return adviceService;
	}

	/**
	 * @param adviceService the adviceService to set
	 */
	public void setAdviceService(AdviceService adviceService) {
		this.adviceService = adviceService;
	}
}
