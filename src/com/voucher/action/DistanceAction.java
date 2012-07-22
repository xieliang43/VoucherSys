package com.voucher.action;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.voucher.entity.Distance;
import com.voucher.pojo.ExtGridReturn;
import com.voucher.pojo.ExtPager;
import com.voucher.pojo.ExtReturn;
import com.voucher.service.DistanceService;

public class DistanceAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2515424587062549269L;
	
	private int start;
	private int limit;
	private String dir;
	private String sort;
	
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
			sendExtReturn(new ExtReturn(false, "距离不能为空！"));
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
		sendExtReturn(new ExtReturn(true, "保存成功！"));
	}
	
	public void delete() {
		if(StringUtils.isBlank(id)) {
			sendExtReturn(new ExtReturn(false, "主键不能为空！"));
			return;
		}
		distanceService.deleteById(Integer.valueOf(id));
		sendExtReturn(new ExtReturn(true, "删除成功！"));
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
