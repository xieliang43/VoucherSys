package com.voucher.action.sys;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.voucher.action.BasePagerAction;
import com.voucher.entity.common.Publish;
import com.voucher.pojo.ExtGridReturn;
import com.voucher.pojo.ExtPager;
import com.voucher.pojo.ExtReturn;
import com.voucher.service.common.PublishService;

public class SysPublishAction extends BasePagerAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7630293551987547372L;
	
	private PublishService publishService;
	
	private String id;
	private String phoneNo;
	private String msg;
	
	public void loadAll() {
		ExtPager pager = new ExtPager(limit, start, dir, sort);
		List<Publish> list = publishService.findPublishByMsg(pager, phoneNo);
		int total = publishService.getTotalCount();
		
		sendExtGridReturn(new ExtGridReturn(total, list));
	}
	
	public void delete() {
		if (StringUtils.isBlank(id)) {
			sendExtReturn(new ExtReturn(FALSE, "主键不能为空！"));
			return;
		}
		publishService.deletePublishById(Integer.valueOf(id));
		sendExtReturn(new ExtReturn(TRUE, "删除成功！"));
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

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

}
