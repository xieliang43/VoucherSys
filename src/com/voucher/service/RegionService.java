/**
 * 
 */
package com.voucher.service;

import java.util.List;

import com.voucher.entity.Region;
import com.voucher.exception.ServiceException;

/**
 * @author weilin
 *
 */
public interface RegionService {
	public List<Region> getRegionByParent(int parentId) throws ServiceException;
	public List<Region> getRegionByType(int type) throws ServiceException;
	public Region getRegionByName(String name) throws ServiceException;
	public Region getRegionById(int id) throws ServiceException;
	public List<Region> findRegionsByParentAndType(int parentId, int type);
}
