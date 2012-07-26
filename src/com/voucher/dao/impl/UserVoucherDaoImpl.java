package com.voucher.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.dao.DataAccessException;

import com.voucher.dao.UserVoucherDao;
import com.voucher.entity.UserVoucher;

public class UserVoucherDaoImpl extends BaseDaoImpl implements UserVoucherDao {

	@Override
	public UserVoucher findInstance(int userId, int viId) {
		String hql = "from UserVoucher uv where uv.user.id = :userId and uv.voucherInstance.id = :viId";
		try {
			Query query = this.createQuery(hql);
			query.setParameter("userId", userId);
			query.setParameter("viId", viId);
			List<UserVoucher> list = query.getResultList();
			if(list != null && !list.isEmpty()) {
				return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void save(UserVoucher userVoucher) {
		try {
			this.getJpaTemplate().persist(userVoucher);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<UserVoucher> findUserInstancese(int userId) {
		String hql = "from UserVoucher uv where uv.user.id = :userId";
		try {
			Query query = this.createQuery(hql);
			query.setParameter("userId", userId);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
