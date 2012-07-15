package com.voucher.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.dao.DataAccessException;

import com.voucher.dao.MerchantDao;
import com.voucher.entity.Merchant;

public class MerchantDaoImpl extends BaseDaoImpl implements MerchantDao {

	@Override
	public void create(Merchant merchant) {
		try {
			this.getJpaTemplate().persist(merchant);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Merchant merchant) {
		try {
			this.getJpaTemplate().merge(merchant);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Merchant merchant) {
		try {
			this.getJpaTemplate().remove(merchant);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Merchant findMerchantById(int merchantId) {
		try {
			return this.getJpaTemplate().find(Merchant.class, merchantId);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Merchant findMerchantByPhoneNo(String phoneNo) {
		String hql = "from Merchant m where m.phoneNo=:phoneNo";
		Query query = this.createQuery(hql);
		query.setParameter("phoneNo", phoneNo);
		try {
			List<Merchant> merchants = query.getResultList();
			if(merchants !=null && !merchants.isEmpty()) {
				return merchants.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Merchant findMerchantByPhoneAndPassword(String phoneNo,
			String password) {
		String hql = "from Merchant m where m.phoneNo=:phoneNo and m.password=:password";
		Query query = this.createQuery(hql);
		query.setParameter("phoneNo", phoneNo);
		query.setParameter("password", password);
		try {
			List<Merchant> merchants = query.getResultList();
			if(merchants !=null && !merchants.isEmpty()) {
				return merchants.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
