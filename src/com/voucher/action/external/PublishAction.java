package com.voucher.action.external;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.voucher.action.BaseAction;
import com.voucher.entity.common.Publish;
import com.voucher.service.common.PublishService;
import com.voucher.vo.JsonVO;

public class PublishAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7630293551987547372L;
	
	private PublishService publishService;
	
	private String phoneNo;
	private String msg;

	public void sendPublish() {
		if(StringUtils.isBlank(phoneNo)) {
			JsonVO jErrorVO = new JsonVO(ZERO, "请输入手机号码！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		if(StringUtils.isBlank(msg)) {
			JsonVO jErrorVO = new JsonVO(ZERO, "请输入建议信息！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		
		Publish publish = new Publish();
		publish.setPhoneNo(phoneNo);
		publish.setMsg(msg);
		publish.setCreateDate(new Date());
		publishService.save(publish);
		
		JsonVO jVO = new JsonVO(ONE, "发布成功，谢谢你的参与！", null);
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
	 * @return the publishService
	 */
	public PublishService getPublishService() {
		return publishService;
	}

	/**
	 * @param publishService the publishService to set
	 */
	public void setPublishService(PublishService publishService) {
		this.publishService = publishService;
	}

	/**
	 * @return the phoneNo
	 */
	public String getPhoneNo() {
		return phoneNo;
	}

	/**
	 * @param phoneNo the phoneNo to set
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
}
