/**
 * 
 */
package com.voucher.dao;

import java.util.List;

import com.voucher.entity.Region;
import com.voucher.exception.DataExistException;
import com.voucher.pojo.ExtPager;

/**
 *
 */
public interface RegionDao {
	public void create(Region region);
	public void update(Region region);
	public void delete(Region region);
	public Region findRegionById(int regionId);
	public List<Region> findRegionsByParent(int parentId);
	public List<Region> findRegionsByType(int type);
	public List<Region> findRegionsByParentAndType(int parentId, int type);
	public Region findRegionByName(String name);
	public List<Region> getAllRegions(ExtPager pager);
	public List<Region> getAllRegionsByName(ExtPager pager, String name);
	public List<Region> getAllRegions();
	public void deleteById(int id) throws DataExistException;
}
