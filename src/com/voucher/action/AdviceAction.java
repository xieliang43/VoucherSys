package com.voucher.action;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.voucher.entity.Advice;
import com.voucher.pojo.ExtGridReturn;
import com.voucher.pojo.ExtPager;
import com.voucher.pojo.ExtReturn;
import com.voucher.pojo.JsonVO;
import com.voucher.service.AdviceService;

public class AdviceAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7630293551987547372L;
	
	private AdviceService adviceService;
	
	private int start;
	private int limit;
	private String dir;
	private String sort;
	
	private String id;
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
		advice.setCreateDate(new Date());
		adviceService.saveAdvice(advice);
		
		JsonVO jVO = new JsonVO("1", "发表建议成功，谢谢你的建议！", null);
		String json = this.convertToJson(jVO);
		sendJSonReturn(json);
	}
	
	public void loadAll() {
		ExtPager pager = new ExtPager(limit, start, dir, sort);
		List<Advice> list = adviceService.findAdviceByMsg(pager, msg);
		int total = adviceService.getTotalCount();
		
		sendExtGridReturn(new ExtGridReturn(total, list));
	}
	
	public void delete() {
		if (StringUtils.isBlank(id)) {
			sendExtReturn(new ExtReturn(false, "主键不能为空！"));
			return;
		}
		adviceService.deleteById(Integer.valueOf(id));
		sendExtReturn(new ExtReturn(true, "删除成功！"));
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
	 * @return the start
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * @param limit the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * @return the dir
	 */
	public String getDir() {
		return dir;
	}

	/**
	 * @param dir the dir to set
	 */
	public void setDir(String dir) {
		this.dir = dir;
	}

	/**
	 * @return the sort
	 */
	public String getSort() {
		return sort;
	}

	/**
	 * @param sort the sort to set
	 */
	public void setSort(String sort) {
		this.sort = sort;
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
