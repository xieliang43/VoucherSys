/**
 * 
 */
package com.voucher.dao.common;

import java.util.List;

import com.voucher.dao.BaseDao;
import com.voucher.entity.common.Region;
import com.voucher.exception.DataExistException;
import com.voucher.pojo.ExtPager;

/**
 *
 */
public interface RegionDao extends BaseDao<Region> {
	public List<Region> findRegionsByParent(int parentId);
	public List<Region> findRegionsByType(int type);
	public List<Region> findRegionsByParentAndType(int parentId, int type);
	public Region findRegionByName(String name);
	public List<Region> getAllRegions(ExtPager pager);
	public List<Region> getAllRegionsByName(ExtPager pager, String name);
	public void deleteById(int id) throws DataExistException;
}
