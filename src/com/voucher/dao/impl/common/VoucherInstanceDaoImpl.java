package com.voucher.dao.impl.common;

import java.util.List;

import javax.persistence.Query;

import org.springframework.dao.DataAccessException;

import com.voucher.dao.common.VoucherInstanceDao;
import com.voucher.dao.impl.BaseDaoImpl;
import com.voucher.entity.common.VoucherInstance;
import com.voucher.exception.PersistenceConcurrentException;

public class VoucherInstanceDaoImpl extends BaseDaoImpl<VoucherInstance> implements VoucherInstanceDao {

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
	public void update(VoucherInstance vchInst) throws PersistenceConcurrentException {
		try {
			em.merge(vchInst);
		} catch (DataAccessException e) {
			throw new PersistenceConcurrentException();
		}
	}

}
