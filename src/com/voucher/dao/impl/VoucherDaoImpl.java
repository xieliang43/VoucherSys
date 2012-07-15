package com.voucher.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.dao.DataAccessException;

import com.voucher.dao.VoucherDao;
import com.voucher.entity.Voucher;

public class VoucherDaoImpl extends BaseDaoImpl implements VoucherDao {

	@Override
	public void create(Voucher voucher) {
		try {
			this.getJpaTemplate().persist(voucher);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Voucher voucher) {
		try {
			this.getJpaTemplate().remove(voucher);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(Voucher voucher) {
		try {
			this.getJpaTemplate().merge(voucher);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Voucher findVoucherById(int voucherId) {
		try {
			return this.getJpaTemplate().find(Voucher.class, voucherId);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
