package com.voucher.dao.impl.common;

import java.util.List;

import javax.persistence.Query;

import com.voucher.dao.common.PublishDao;
import com.voucher.dao.impl.BaseDaoImpl;
import com.voucher.entity.common.Publish;
import com.voucher.pojo.ExtPager;

public class PublishDaoImpl extends BaseDaoImpl<Publish> implements PublishDao {

	@Override
	public List<Publish> findPublishes(ExtPager pager) {
		String hql = "from Publish pub";
		try {
			Query query = this.createPagerQuery(Publish.class, hql, pager);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Publish> findPublishesByPhoneNo(ExtPager pager, String phoneNo) {
		String hql = "from Publish pb where pb.phoneNo like :phoneNo";
		
		try {
			Query query = this.createPagerQuery(Publish.class, hql, pager);
			query.setParameter("phoneNo", "%" + phoneNo + "%");
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
