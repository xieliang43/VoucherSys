package com.voucher.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.SessionAware;

import com.voucher.entity.Region;
import com.voucher.entity.Shop;
import com.voucher.exception.ServiceDataAccessException;
import com.voucher.pojo.AreaVO;
import com.voucher.pojo.ExtGridReturn;
import com.voucher.pojo.ExtPager;
import com.voucher.pojo.ExtReturn;
import com.voucher.pojo.JsonVO;
import com.voucher.pojo.RegionVO;
import com.voucher.service.RegionService;
import com.voucher.service.ShopService;
import com.voucher.util.JackJson;

public class RegionAction extends BaseAction implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6157582358663584560L;
	
	private RegionService regionService;
	private ShopService shopService;
	
	private int start;
	private int limit;
	private String dir;
	private String sort;
	
	private String id;
	private String name;
	private String type;
	private String parentId;
	private String regionPrefix;
	private String enabled;
	private Date createDate;

	private Map<String, Object> session;
	
	public void getCities() throws IOException {
		List<Region> regions = regionService.getRegionByType(2);
		if(regions == null || regions.isEmpty()) {
			JsonVO jErrorVO = new JsonVO("0", "载入城市失败", null);
			String json = this.convertToJson(jErrorVO);
			this.sendJSonReturn(json);
			return;
		}
		Map<String, List<AreaVO>> map = new TreeMap<String, List<AreaVO>>();
		for(Region r : regions) {
			String prefix = r.getRegionPrefix();
			AreaVO area = new AreaVO(r.getId(), r.getName(), prefix);
			if(map.get(prefix) != null) {
				map.get(prefix).add(area);
			} else {
				List<AreaVO> areas = new ArrayList<AreaVO>();
				areas.add(area);
				map.put(r.getRegionPrefix(), areas);
			}
		}
		JsonVO vo = new JsonVO("1", "获取城市成功", map);
		String json = this.convertToJson(vo);
		this.sendJSonReturn(json);
	}
	
	public String area() {
		Map<String, Object> areaMap = regionService.getAllRegions();
		session.put("areaMap", JackJson.fromObjectToJson(areaMap));
		return SUCCESS;
	}
	
	public void loadAll() {
		ExtPager pager = new ExtPager(limit, start, dir, sort);
		List<RegionVO> list = regionService.getRegionsByName(pager, name);
		int total = regionService.getTotalCount();
		
		sendExtGridReturn(new ExtGridReturn(total, list));
	}
	
	public void save() {
		if (StringUtils.isBlank(getName())) {
			sendExtReturn(new ExtReturn(false, "地区名不能为空！"));
			return;
		}
		if (StringUtils.isBlank(getType())) {
			sendExtReturn(new ExtReturn(false, "地区类型不能为空！"));
			return;
		}
		if (StringUtils.isBlank(getParentId())) {
			sendExtReturn(new ExtReturn(false, "上级地区不能为空！"));
			return;
		}
		if (StringUtils.isBlank(getRegionPrefix())) {
			sendExtReturn(new ExtReturn(false, "地区编码不能为空！"));
			return;
		}
		Region parent = regionService.findRegionByParentId(Integer.valueOf(parentId));
		Region region = new Region(name, Integer.valueOf(type), regionPrefix, Short.valueOf(enabled), parent);
		if(StringUtils.isBlank(id)) {
			region.setCreateDate(new Date());
			regionService.saveRegion(region);
		} else {
			region.setId(Integer.valueOf(id));
			regionService.update(region);
		}
		sendExtReturn(new ExtReturn(true, "保存成功！"));
	}
	
	public void delete() {
		if (StringUtils.isBlank(id)) {
			sendExtReturn(new ExtReturn(false, "主键不能为空！"));
			return;
		}
		List<Shop> shop = shopService.getShopsByRegion(Integer.valueOf(id));
		if(shop != null && !shop.isEmpty()) {
			sendExtReturn(new ExtReturn(false, "此区域有商店关联，请先删除区域商店！"));
			return;
		}
		try {
			regionService.deleteById(Integer.valueOf(id));
		} catch (NumberFormatException e) {
			sendExtReturn(new ExtReturn(false, "主键格式错误！"));
			return;
		} catch (ServiceDataAccessException e) {
			sendExtReturn(new ExtReturn(false, "此区域有子区域，请先删除子区域！"));
			return;
		}
		sendExtReturn(new ExtReturn(true, "删除成功！"));
	}

	/**
	 * @return the regionService
	 */
	public RegionService getRegionService() {
		return regionService;
	}

	/**
	 * @param regionService the regionService to set
	 */
	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the regionPrefix
	 */
	public String getRegionPrefix() {
		return regionPrefix;
	}

	/**
	 * @param regionPrefix the regionPrefix to set
	 */
	public void setRegionPrefix(String regionPrefix) {
		this.regionPrefix = regionPrefix;
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
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	/**
	 * @return the shopService
	 */
	public ShopService getShopService() {
		return shopService;
	}

	/**
	 * @param shopService the shopService to set
	 */
	public void setShopService(ShopService shopService) {
		this.shopService = shopService;
	}
}
