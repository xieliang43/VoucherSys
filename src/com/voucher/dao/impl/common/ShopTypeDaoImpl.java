package com.voucher.dao.impl.common;

import java.util.List;

import javax.persistence.Query;

import com.voucher.dao.common.ShopTypeDao;
import com.voucher.dao.impl.BaseDaoImpl;
import com.voucher.entity.common.ShopType;
import com.voucher.pojo.ExtPager;

public class ShopTypeDaoImpl extends BaseDaoImpl<ShopType> implements ShopTypeDao {

	@Override
	public List<ShopType> getShopTypes(ExtPager pager) {
		String hql = "from ShopType st";
		try {
			Query query = this.createPagerQuery(ShopType.class, hql, pager);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ShopType> getShopTypesByName(ExtPager pager, String name) {
		String hql = "from ShopType st where st.name = :name";
		
		try {
			Query query = this.createPagerQuery(ShopType.class, hql, pager);
			query.setParameter("name", name);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ShopType> getEnabledShopTypes() {
		String hql = "from ShopType st where st.enabled = 1";
		try {
			Query query = this.createQuery(hql);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
