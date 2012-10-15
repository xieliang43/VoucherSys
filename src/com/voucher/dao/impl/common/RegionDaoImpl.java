/**
 * 
 */
package com.voucher.dao.impl.common;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.voucher.dao.common.RegionDao;
import com.voucher.dao.impl.BaseDaoImpl;
import com.voucher.entity.common.Region;
import com.voucher.exception.DataExistException;
import com.voucher.exception.DataNotFoundException;
import com.voucher.pojo.ExtPager;

/**
 *
 */
public class RegionDaoImpl extends BaseDaoImpl<Region> implements RegionDao {
	
	private static final Logger logger = Logger.getLogger(RegionDaoImpl.class);

	public List<Region> findRegionsByParent(int parentId) throws DataNotFoundException {
		String hql = "from Region r where r.parent.id = :pid";
		List<Region> regions = null;
		try {
			Query query = this.createQuery(hql);
			query.setParameter("pid", parentId);
			regions = query.getResultList();
		} catch (DataAccessException e) {
			throw new DataNotFoundException("Get regions failed by parent id: " + parentId);
		}
		return regions;
	}

	public List<Region> findRegionsByType(int type) throws DataNotFoundException {
		String hql = "from Region r where r.type = :type order by r.regionPrefix asc";
		List<Region> regions = null;
		try {
			Query query = this.createQuery(hql);
			query.setParameter("type", type);
			regions = query.getResultList();
		} catch (DataAccessException e) {
			throw new DataNotFoundException("Get regions failed by type, type is: " + type);
		}
		return regions;
	}

	@Override
	public Region findRegionByName(String name) throws DataNotFoundException {
		String hql = "from Region r where r.name = :name";
		List<Region> regions = null;
		try {
			Query query = this.createQuery(hql);
			query.setParameter("name", name);
			regions = query.getResultList();
			
			if (regions != null && regions.size() > 0){
				for(Region r : regions) {
					if(r.getType() == 2)
						return r;
				}
			}
		} catch (DataAccessException e) {
			throw new DataNotFoundException("Get region failed by region name: " + name);
		}
		
		return null;
	}

	@Override
	public List<Region> findRegionsByParentAndType(int parentId, int type) {
		String hql = "from Region r where r.parent.id = :pid and r.type = :type";
		List<Region> regions = null;
		try {
			Query query = this.createQuery(hql);
			query.setParameter("pid", parentId);
			query.setParameter("type", type);
			regions = query.getResultList();
			
			if (regions != null && regions.size() > 0){
				return regions;
			}
		} catch (DataAccessException e) {
			throw new DataNotFoundException("Get region failed by region parentId : " + parentId + ", and type: " + type);
		}
		
		return null;
	}

	@Override
	public List<Region> getAllRegions(ExtPager pager) {
		String hql = "from Region r";
		try {
			Query query = this.createPagerQuery(Region.class, hql, pager);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Region> getAllRegionsByName(ExtPager pager, String name) {
		String hql = "from Region r where r.name = :name";
		
		try {
			Query query = this.createPagerQuery(Region.class, hql, pager);
			query.setParameter("name", name);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void deleteById(int id) throws DataExistException {
		try {
			em.remove(em.merge(this.findById(Region.class, id)));
		} catch (DataAccessException e) {
			logger.error("Foreign key exist, cannot remove region by id: " + id);
			throw new DataExistException("Foreign key exist");
		} catch (DataNotFoundException e) {
			e.printStackTrace();
		}
	}

}
