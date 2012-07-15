/**
 * 
 */
package com.voucher.dao;

import java.util.List;

import com.voucher.entity.Region;

/**
 * @author LL
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
}
