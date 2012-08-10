package com.voucher.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.dao.DataAccessException;

import com.voucher.dao.VoucherDao;
import com.voucher.entity.Voucher;
import com.voucher.entity.sys.SysUser;
import com.voucher.pojo.ExtPager;

public class VoucherDaoImpl extends BaseDaoImpl implements VoucherDao {

	@Override
	public void create(Voucher voucher) {
		try {
			this.getJpaTemplate().persist(voucher);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Voucher voucher) {
		try {
			this.getJpaTemplate().remove(this.getJpaTemplate().merge(voucher));
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Voucher voucher) {
		try {
			this.getJpaTemplate().merge(voucher);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	public Voucher findVoucherById(int voucherId) {
		try {
			return this.getJpaTemplate().find(Voucher.class, voucherId);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Voucher> findAll() {
		String hql = "from Voucher v";
		try {
			Query query = this.createQuery(hql);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Voucher> getCurrentMerchantVouchers(ExtPager pager,
			SysUser merchant) {
		String hql = "from Voucher v where v.shop.merchant.id = :id";
		hql = this.createQueryString(Voucher.class, hql, pager);
		try {
			Query query = this.createQuery(hql);
			if(pager != null) {
				query.setFirstResult(pager.getStart()).setMaxResults(pager.getLimit());
			}
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
		hql = this.createQueryString(Voucher.class, hql, pager);
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
	public List<Voucher> getCurrentMerchantVouchers(SysUser merchant) {
		return this.getCurrentMerchantVouchers(null, merchant);
	}

	@Override
	public void deleteById(int id) {
		try {
			this.getJpaTemplate().remove(this.findVoucherById(id));
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void save(Voucher voucher) {
		try {
			this.getJpaTemplate().persist(voucher);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
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
