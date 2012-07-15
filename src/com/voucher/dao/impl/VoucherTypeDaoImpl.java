package com.voucher.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.dao.DataAccessException;

import com.voucher.dao.VoucherTypeDao;
import com.voucher.entity.ShopType;

public class VoucherTypeDaoImpl extends BaseDaoImpl implements VoucherTypeDao {

	@Override
	public void create(ShopType voucherType) {
		try {
			this.getJpaTemplate().persist(voucherType);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delete(ShopType voucherType) {
		try {
			this.getJpaTemplate().remove(voucherType);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(ShopType voucherType) {
		try {
			this.getJpaTemplate().merge(voucherType);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public ShopType findVoucherTypeById(int vTypeId) {
		try {
			return this.getJpaTemplate().find(ShopType.class, vTypeId);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ShopType> findAll() {
		String hql = "from VoucherType vt";
		try {
			Query query = this.createQuery(hql);
			return query.getResultList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
