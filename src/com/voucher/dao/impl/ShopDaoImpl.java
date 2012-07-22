package com.voucher.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.dao.DataAccessException;

import com.voucher.dao.ShopDao;
import com.voucher.entity.Shop;
import com.voucher.entity.sys.SysUser;
import com.voucher.pojo.ExtPager;

public class ShopDaoImpl extends BaseDaoImpl implements ShopDao {

	@Override
	public Shop findShopById(int shopId) {
		try {
			return this.getJpaTemplate().find(Shop.class, shopId);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void update(Shop shop) {
		try {
			this.getJpaTemplate().merge(shop);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void create(Shop shop) {
		try {
			this.getJpaTemplate().persist(shop);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Shop> getShops() {
		String hql = "from Shop sh";
		try {
			Query query = this.createQuery(hql);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Shop> getShopsByCityId(int cityId) {
		String hql ="from Shop s where s.merchant.cityId = :cityId";
		try {
			Query query = this.createQuery(hql);
			query.setParameter("cityId", cityId);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Shop> getAllShops(ExtPager pager, SysUser merchant) {
		String hql = "from Shop s where s.merchant.id = :id";
		hql = this.createQueryString(hql, pager);
		try {
			Query query = this.createQuery(hql).setFirstResult(pager.getStart()).setMaxResults(pager.getLimit());
			query.setParameter("id", merchant.getId());
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Shop> getAllShopsByShopName(ExtPager pager, SysUser merchant, String shopName) {
		String hql = "from Shop s where s.merchant.id = :id and s.shopName like :shopName";
		hql = this.createQueryString(hql, pager);
		try {
			Query query = this.createQuery(hql).setFirstResult(pager.getStart()).setMaxResults(pager.getLimit());
			query.setParameter("id", merchant.getId());
			query.setParameter("shopName", "%" + shopName + "%");
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Shop> getCurrentMerchantShops(SysUser merchant) {
		String hql = "from Shop s where s.merchant.id = :id";
		try {
			Query query = this.createQuery(hql);
			query.setParameter("id", merchant.getId());
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
