/**
 * 
 */
package com.voucher.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.voucher.comparator.RegionalComparator;
import com.voucher.dao.RegionDao;
import com.voucher.entity.Region;
import com.voucher.exception.DataNotFoundException;
import com.voucher.exception.ServiceException;
import com.voucher.service.RegionService;

/**
 * @author LL
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
}
