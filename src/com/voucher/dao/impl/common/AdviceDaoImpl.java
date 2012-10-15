package com.voucher.dao.impl.common;

import java.util.List;

import javax.persistence.Query;

import com.voucher.dao.common.AdviceDao;
import com.voucher.dao.impl.BaseDaoImpl;
import com.voucher.entity.common.Advice;
import com.voucher.pojo.ExtPager;

public class AdviceDaoImpl extends BaseDaoImpl<Advice> implements AdviceDao {

	@Override
	public List<Advice> findAdvicesByMsg(ExtPager pager, String msg) {
		String hql = "from Advice ad where ad.msg like :msg";
		
		try {
			Query query = this.createPagerQuery(Advice.class, hql, pager);
			query.setParameter("msg", "%" + msg + "%");
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Advice> findAdvices(ExtPager pager) {
		String hql = "from Advice ad";
		try {
			Query query = this.createPagerQuery(Advice.class, hql, pager);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
