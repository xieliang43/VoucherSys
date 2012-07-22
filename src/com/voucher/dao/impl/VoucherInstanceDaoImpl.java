package com.voucher.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.dao.DataAccessException;

import com.voucher.dao.VoucherInstanceDao;
import com.voucher.entity.VoucherInstance;

public class VoucherInstanceDaoImpl extends BaseDaoImpl implements VoucherInstanceDao {

	@Override
	public int getActiveCountByVoucher(int id) {
		List<VoucherInstance> list = this.getActiveVouchersById(id);
		if(list != null) {
			return list.size();
		}
		return 0;
	}

	@Override
	public List<VoucherInstance> getActiveVouchersById(int id) {
		String hql = "from VoucherInstance vi where vi.voucher.id = :id and vi.isBought = :isBought";
		try {
			Query query = this.createQuery(hql);
			query.setParameter("id", id);
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

}
