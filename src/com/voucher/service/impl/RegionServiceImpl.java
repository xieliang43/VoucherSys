/**
 * 
 */
package com.voucher.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.voucher.comparator.RegionalComparator;
import com.voucher.dao.RegionDao;
import com.voucher.entity.Region;
import com.voucher.exception.DataNotFoundException;
import com.voucher.exception.ServiceException;
import com.voucher.pojo.ExtPager;
import com.voucher.pojo.RegionVO;
import com.voucher.service.RegionService;

/**
 *
 */
public class RegionServiceImpl implements RegionService {
	private static final Logger logger = Logger.getLogger(RegionServiceImpl.class);
	
	private RegionDao regionDao;
	
	/**
	 * @return the regionDao
	 */
	public RegionDao getRegionDao() {
		return regionDao;
	}

	/**
	 * @param regionDao the regionDao to set
	 */
	public void setRegionDao(RegionDao regionDao) {
		this.regionDao = regionDao;
	}

	public List<Region> getRegionByParent(int parentId) throws ServiceException {
		logger.info("start to find regions by parent id: " + parentId);
		try {
			List<Region> regions = regionDao.findRegionsByParent(parentId);
			if(regions != null){
				Collections.sort(regions, new RegionalComparator());
			}else{
				regions = new ArrayList<Region>();
			}
			return regions;
		} catch (DataNotFoundException e) {
			logger.error("error occurred when try to find region by parent id: " + parentId);
			throw new ServiceException("Error occurred, cannot find regions by the id: " + parentId);
		}
	}

	public List<Region> getRegionByType(int type) throws ServiceException {
		logger.info("start to find regions by the type: " + type);
		try {
			List<Region> regions = regionDao.findRegionsByType(type);
			if(regions != null){
				Collections.sort(regions, new RegionalComparator());
			}else{
				regions = new ArrayList<Region>();
			}
			return regions;
		} catch (DataNotFoundException e) {
			logger.error("error occurred when try to find regions by type id: " + type);
			throw new ServiceException("Error occurred, cannot find region by the type id: " + type);
		}
	}

	@Override
	public Region getRegionByName(String name) throws ServiceException {
		logger.info("start to find regions by the name: " + name);
		try {
			return regionDao.findRegionByName(name);
		} catch (DataNotFoundException e) {
			logger.error("error occurred when try to find regions by name: " + name);
			throw new ServiceException("Error occurred, cannot find region by name: " + name);
		}
	}

	@Override
	public Region getRegionById(int id) throws ServiceException {
		logger.info("start to find regions by the id: " + id);
		try {
			return regionDao.findRegionById(id);
		} catch (DataNotFoundException e) {
			logger.error("error occurred when try to find regions by id: " + id);
			throw new ServiceException("Error occurred, cannot find region by id: " + id);
		}
	}

	@Override
	public List<Region> findRegionsByParentAndType(int parentId, int type) {
		return regionDao.findRegionsByParentAndType(parentId, type);
	}

	@Override
	public List<RegionVO> getRegionsByName(ExtPager pager, String name) {
		List<RegionVO> list = new ArrayList<RegionVO>();
		List<Region> regions = null;
		if(StringUtils.isBlank(name)) {
			regions = regionDao.getAllRegions(pager);
		} else {
			regions = regionDao.getAllRegionsByName(pager, name);
		}
		for(Region region : regions) {
			int id = region.getId();
			String rname = region.getName();
			int type = region.getType();
			int parentId = region.getParent() == null ? -1 : region.getParent().getId();
			String regionPrefix = region.getRegionPrefix();
			short enabled = region.getEnabled();
			Date createDate = region.getCreateDate();
			
			RegionVO rvo = new RegionVO(id, rname, type, parentId, regionPrefix, enabled, createDate);
			list.add(rvo);
		}
		return list;
	}

	@Override
	public Map<String, Object> getAllRegions() {
		Map<String, Object> map = new TreeMap<String, Object>();
		List<Region> regions = regionDao.getAllRegions();
		if(regions != null && !regions.isEmpty()) {
			for(Region r : regions) {
				map.put(String.valueOf(r.getId()), r.getName());
			}
		}
		return map;
	}

	@Override
	public int getTotalCount() {
		return regionDao.getAllRegions().size();
	}

	@Override
	public Region findRegionByParentId(int parentId) {
		return regionDao.findRegionById(parentId);
	}

	@Override
	public void saveRegion(Region region) {
		regionDao.create(region);
	}

	@Override
	public void update(Region region) {
		try {
			Region oldRegion = regionDao.findRegionById(region.getId());
			region.setCreateDate(oldRegion.getCreateDate());
			regionDao.update(region);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteById(int id) {
		regionDao.deleteById(id);
	}

	@Override
	public Map<String, Object> getAllEnabledCities() {
		Map<String, Object> map = new TreeMap<String, Object>();
		List<Region> regions = regionDao.findRegionsByType(2);
		if(regions != null && !regions.isEmpty()) {
			for(Region r : regions) {
				if(r.getEnabled() == 1){
					map.put(String.valueOf(r.getId()), r.getName());
				}
			}
		}
		return map;
	}
}
