package com.voucher.dao.impl.common;

import java.util.List;

import javax.persistence.Query;

import com.voucher.dao.common.VoucherDao;
import com.voucher.dao.impl.BaseDaoImpl;
import com.voucher.entity.common.Voucher;
import com.voucher.entity.sys.SysUser;
import com.voucher.pojo.ExtPager;

public class VoucherDaoImpl extends BaseDaoImpl<Voucher> implements VoucherDao {

	@Override
	public List<Voucher> getCurrentMerchantVouchers(ExtPager pager,
			SysUser merchant) {
		String hql = "from Voucher v where v.shop.merchant.id = :id";
		try {
			Query query = this.createPagerQuery(Voucher.class, hql, pager);
			query.setParameter("id", merchant.getId());
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Voucher> getCurrentMerchantVouchersByShopName(ExtPager pager,
			SysUser merchant, String shopName) {
		String hql = "from Voucher v where v.shop.merchant.id = :id and v.shop.shopName like :shopName";
		try {
			Query query = this.createPagerQuery(Voucher.class, hql, pager);
			query.setParameter("id", merchant.getId());
			query.setParameter("shopName", "%" + shopName + "%");
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Voucher> findAllEnabledVouchers() {
		String hql = "from Voucher v where v.enabled = :enabled";
		try {
			Query query = this.createQuery(hql);
			query.setParameter("enabled", (short)1);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Voucher> getEnabledVouchersByShop(int shopId) {
		String hql = "from Voucher v where v.enabled = :enabled and v.shop.id = :shopId";
		try {
			Query query = this.createQuery(hql);
			query.setParameter("enabled", (short)1);
			query.setParameter("shopId", shopId);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Voucher> getCurrentMerchantVouchersByShopName(SysUser merchant,
			String shopName) {
		String hql = "from Voucher v where v.shop.merchant.id = :id and v.shop.shopName like :shopName";
		try {
			Query query = this.createQuery(hql);
			query.setParameter("id", merchant.getId());
			query.setParameter("shopName", "%" + shopName + "%");
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Voucher> getVouchersByShopId(int shopId) {
		String hql = "from Voucher v where v.shop.id = :shopId";
		try {
			Query query = this.createQuery(hql);
			query.setParameter("shopId", shopId);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
