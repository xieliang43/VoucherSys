package com.voucher.action.sys;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.voucher.action.BasePagerAction;
import com.voucher.entity.common.Distance;
import com.voucher.pojo.ExtGridReturn;
import com.voucher.pojo.ExtPager;
import com.voucher.pojo.ExtReturn;
import com.voucher.service.common.DistanceService;

public class SysDistanceAction extends BasePagerAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2515424587062549269L;
	
	private String id;
	private String name;
	private String enabled;
	
	private DistanceService distanceService;
	
	public void loadAll() {
		ExtPager pager = new ExtPager(limit, start, dir, sort);
		List<Distance> list = distanceService.getAllDistancesByName(pager, name);
		int total = distanceService.getTotalCount();
		
		sendExtGridReturn(new ExtGridReturn(total, list));
	}

	public void save() {
		if (StringUtils.isBlank(getName())) {
			sendExtReturn(new ExtReturn(FALSE, "距离不能为空！"));
			return;
		}
		Distance distance = new Distance(name, Short.valueOf(enabled));
		if(StringUtils.isBlank(id)) {
			distance.setCreateDate(new Date());
			distanceService.save(distance);
		} else {
			distance.setId(Integer.valueOf(id));
			distanceService.update(distance);
		}
		sendExtReturn(new ExtReturn(TRUE, "保存成功！"));
	}
	
	public void delete() {
		if(StringUtils.isBlank(id)) {
			sendExtReturn(new ExtReturn(FALSE, "主键不能为空！"));
			return;
		}
		distanceService.deleteDistanceById(Integer.valueOf(id));
		sendExtReturn(new ExtReturn(TRUE, "删除成功！"));
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

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the enabled
	 */
	public String getEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the distanceService
	 */
	public DistanceService getDistanceService() {
		return distanceService;
	}

	/**
	 * @param distanceService the distanceService to set
	 */
	public void setDistanceService(DistanceService distanceService) {
		this.distanceService = distanceService;
	}
}
