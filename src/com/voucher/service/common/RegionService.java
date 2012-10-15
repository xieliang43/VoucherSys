/**
 * 
 */
package com.voucher.service.common;

import java.util.List;
import java.util.Map;

import com.voucher.entity.common.Region;
import com.voucher.exception.ServiceDataAccessException;
import com.voucher.exception.ServiceException;
import com.voucher.pojo.ExtPager;
import com.voucher.service.BaseService;
import com.voucher.vo.AreaVO;
import com.voucher.vo.RegionVO;

/**
 *
 */
public interface RegionService extends BaseService<Region> {
	public List<Region> getRegionByParent(int parentId) throws ServiceException;
	public List<Region> getRegionByType(int type) throws ServiceException;
	public Region getRegionByName(String name) throws ServiceException;
	public List<Region> findRegionsByParentAndType(int parentId, int type);
	public List<RegionVO> getRegionsByName(ExtPager pager, String name);
	public Map<String, Object> getAllRegions();
	public Map<String, Object> getAllEnabledCities();
	public Map<String, Object> getAllEnabledDistricts();
	public Map<String, Object> getAllCities();
	public int getTotalCount();
	public Region findRegionByParentId(int parentId);
	public void deleteRegionById(int id) throws ServiceDataAccessException;
	public Map<String, Object> getAllEnabledDistrictsByCity(int cityId);
	public List<AreaVO> getAreasByParent(int cityId);
	public Map<String, List<AreaVO>> getCityPrefixMap();
	public Region findRegionById(int id);
}
