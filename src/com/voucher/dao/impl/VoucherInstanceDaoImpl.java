package com.voucher.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.dao.DataAccessException;

import com.voucher.dao.VoucherInstanceDao;
import com.voucher.entity.VoucherInstance;
import com.voucher.exception.PersistenceConcurrentException;

public class VoucherInstanceDaoImpl extends BaseDaoImpl implements VoucherInstanceDao {

	@Override
	public int getActiveCountByVoucher(int vchId) {
		List<VoucherInstance> list = this.getActiveVoucherInstancesByVoucher(vchId);
		if(list != null) {
			return list.size();
		}
		return 0;
	}

	@Override
	public List<VoucherInstance> getActiveVoucherInstancesByVoucher(int vchId) {
		String hql = "from VoucherInstance vi where vi.voucher.id = :id and vi.isBought = :isBought";
		try {
			Query query = this.createQuery(hql);
			query.setParameter("id", vchId);
			query.setParameter("isBought", (short)0);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void save(VoucherInstance vi) {
		try {
			this.getJpaTemplate().persist(vi);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getVoucherInstanceCountByVoucher(int vchId) {
		List<VoucherInstance> list = this.getVoucherInstancesByVoucher(vchId);
		if(list != null) {
			return list.size();
		}
		return 0;
	}

	@Override
	public List<VoucherInstance> getVoucherInstancesByVoucher(int vchId) {
		String hql = "from VoucherInstance vi where vi.voucher.id = :id order by vi.isBought asc, vi.id asc";
		try {
			Query query = this.createQuery(hql);
			query.setParameter("id", vchId);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public VoucherInstance getVoucherInstancesById(int viId) {
		try {
			return this.getJpaTemplate().find(VoucherInstance.class, viId);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void update(VoucherInstance vchInst) throws PersistenceConcurrentException {
		try {
			this.getJpaTemplate().merge(vchInst);
		} catch (DataAccessException e) {
			throw new PersistenceConcurrentException();
		}
	}

}
