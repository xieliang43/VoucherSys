package com.voucher.action.sys;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.voucher.action.BasePagerAction;
import com.voucher.entity.common.Advice;
import com.voucher.pojo.ExtGridReturn;
import com.voucher.pojo.ExtPager;
import com.voucher.pojo.ExtReturn;
import com.voucher.service.common.AdviceService;

public class SysAdviceAction extends BasePagerAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7630293551987547372L;
	
	private AdviceService adviceService;
	
	private String id;
	private String msg;
	
	public void loadAll() {
		ExtPager pager = new ExtPager(limit, start, dir, sort);
		List<Advice> list = adviceService.findAdviceByMsg(pager, msg);
		int total = adviceService.getTotalCount();
		
		sendExtGridReturn(new ExtGridReturn(total, list));
	}
	
	public void delete() {
		if (StringUtils.isBlank(id)) {
			sendExtReturn(new ExtReturn(FALSE, "主键不能为空！"));
			return;
		}
		adviceService.deleteAdviceById(Integer.valueOf(id));
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
