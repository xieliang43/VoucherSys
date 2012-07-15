package com.voucher.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.dao.DataAccessException;

import com.voucher.dao.ShopTypeDao;
import com.voucher.entity.ShopType;
import com.voucher.pojo.ExtPager;

public class ShopTypeDaoImpl extends BaseDaoImpl implements ShopTypeDao {

	@Override
	public ShopType findShopTypeById(int shopTypeId) {
		try {
			return this.getJpaTemplate().find(ShopType.class, shopTypeId);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ShopType> getShopTypes() {
		String hql = "from ShopType st";
		try {
			Query query = this.createQuery(hql);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ShopType> getShopTypes(ExtPager pager) {
		String hql = "from ShopType st";
		hql = this.createQueryString(hql, pager);
		try {
			Query query = this.createQuery(hql);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ShopType> getShopTypesByName(ExtPager pager, String name) {
		String hql = "from ShopType st where st.name = :name";
		hql = this.createQueryString(hql, pager);
		
		try {
			Query query = this.createQuery(hql).setFirstResult(pager.getStart()).setMaxResults(pager.getLimit());
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
