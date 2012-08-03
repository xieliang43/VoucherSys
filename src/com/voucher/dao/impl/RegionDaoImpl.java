/**
 * 
 */
package com.voucher.dao.impl;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.dao.DataAccessException;

import com.voucher.dao.RegionDao;
import com.voucher.entity.Region;
import com.voucher.exception.DataNotFoundException;
import com.voucher.pojo.ExtPager;

/**
 *
 */
public class RegionDaoImpl extends BaseDaoImpl implements RegionDao {

	public void create(Region region) throws PersistenceException {
		try {
			this.getJpaTemplate().persist(region);
		} catch (DataAccessException e) {
			throw new PersistenceException("Cannot persist region: " + region.getName());
		}
	}

	public Region findRegionById(int regionId) throws DataNotFoundException {
		try {
			return this.getJpaTemplate().find(Region.class, regionId);
		} catch (DataAccessException e) {
			throw new DataNotFoundException("Get region failed by id: " + regionId);
		}
	}

	public void update(Region region) throws PersistenceException {
		try {
			this.getJpaTemplate().merge(region);
		} catch (DataAccessException e) {
			throw new PersistenceException("Cannot update region: " + region.getName());
		}
	}

	public void delete(Region region) throws PersistenceException {
		try {
			this.getJpaTemplate().remove(region);
		} catch (DataAccessException e) {
			throw new PersistenceException("Cannot remove region: " + region.getName());
		}
	}

	public List<Region> findRegionsByParent(int parentId) throws DataNotFoundException {
		String hql = "from Region r where r.parent.id = ?";
		List<Region> regions = null;
		try {
			regions = this.getJpaTemplate().find(hql, parentId);
		} catch (DataAccessException e) {
			throw new DataNotFoundException("Get regions failed by parent id: " + parentId);
		}
		return regions;
	}

	public List<Region> findRegionsByType(int type) throws DataNotFoundException {
		String hql = "from Region r where r.type = ? order by r.regionPrefix asc";
		List<Region> regions = null;
		try {
			regions = this.getJpaTemplate().find(hql, type);
		} catch (DataAccessException e) {
			throw new DataNotFoundException("Get regions failed by type, type is: " + type);
		}
		return regions;
	}

	@Override
	public Region findRegionByName(String name) throws DataNotFoundException {
		String hql = "from Region r where r.name = ?";
		List<Region> regions = null;
		try {
			regions = this.getJpaTemplate().find(hql, name);
			
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
		String hql = "from Region r where r.parent.id = ? and r.type = ?";
		List<Region> regions = null;
		try {
			regions = this.getJpaTemplate().find(hql, parentId, type);
			
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
		hql = this.createQueryString(Region.class, hql, pager);
		try {
			Query query = this.createQuery(hql).setFirstResult(pager.getStart()).setMaxResults(pager.getLimit());
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Region> getAllRegionsByName(ExtPager pager, String name) {
		String hql = "from Region r where r.name = :name";
		hql = this.createQueryString(Region.class, hql, pager);
		
		try {
			Query query = this.createQuery(hql).setFirstResult(pager.getStart()).setMaxResults(pager.getLimit());
			query.setParameter("name", name);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Region> getAllRegions() {
		String hql = "from Region r";
		try {
			Query query = this.createQuery(hql);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void deleteById(int id) {
		try {
			this.getJpaTemplate().remove(this.getJpaTemplate().merge(this.findRegionById(id)));
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (DataNotFoundException e) {
			e.printStackTrace();
		}
	}

}
