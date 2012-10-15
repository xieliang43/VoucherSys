package com.voucher.action.external;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.voucher.action.BaseAction;
import com.voucher.entity.common.Advice;
import com.voucher.service.common.AdviceService;
import com.voucher.vo.JsonVO;

public class AdviceAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7630293551987547372L;
	
	private AdviceService adviceService;
	
	private String msg;

	public void sendAdvice() {
		if(StringUtils.isBlank(msg)) {
			JsonVO jErrorVO = new JsonVO(ZERO, "请输入建议信息！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		
		Advice advice = new Advice();
		advice.setMsg(msg);
		advice.setCreateDate(new Date());
		adviceService.save(advice);
		
		JsonVO jVO = new JsonVO(ONE, "发表建议成功，谢谢你的建议！", null);
		String json = this.convertToJson(jVO);
		sendJSonReturn(json);
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
