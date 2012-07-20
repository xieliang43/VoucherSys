/**
 * 
 */
package com.voucher.service;

import java.util.List;
import java.util.Map;

import com.voucher.entity.Region;
import com.voucher.exception.ServiceException;
import com.voucher.pojo.ExtPager;
import com.voucher.pojo.RegionVO;

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
	public List<RegionVO> getRegionsByName(ExtPager pager, String name);
	public Map<String, Object> getAllRegions();
	public Map<String, Object> getAllEnabledCities();
	public Map<String, Object> getAllCities();
	public int getTotalCount();
	public Region findRegionByParentId(int parentId);
	public void saveRegion(Region region);
	public void update(Region region);
	public void deleteById(int id);
}
