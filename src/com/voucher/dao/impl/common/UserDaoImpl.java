/**
 * 
 */
package com.voucher.dao.impl.common;

import java.util.List;

import javax.persistence.Query;

import com.voucher.dao.common.UserDao;
import com.voucher.dao.impl.BaseDaoImpl;
import com.voucher.entity.common.User;
import com.voucher.pojo.ExtPager;

/**
 *
 */
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	@Override
	public User findUserByPhoneNo(String phoneNo) {
		String hql = "from User u where u.phoneNo=:phoneNo";
		try {
			Query query = this.createQuery(hql);
			query.setParameter("phoneNo", phoneNo);
			List<User> users = query.getResultList();
			if(users !=null && !users.isEmpty()) {
				return users.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User findUserByPhoneAndPassword(String phoneNo, String password) {
		String hql = "from User u where u.phoneNo=:phoneNo and u.password=:password";
		Query query = this.createQuery(hql);
		query.setParameter("phoneNo", phoneNo);
		query.setParameter("password", password);
		try {
			List<User> users = query.getResultList();
			if(users !=null && !users.isEmpty()) {
				return users.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<User> findUsersByPhoneNo(ExtPager pager, String phoneNo) {
		String hql = "from User u where u.phoneNo like :phoneNo";
		try {
			Query query = this.createPagerQuery(User.class, hql, pager);
			query.setParameter("phoneNo", "%" + phoneNo + "%");
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<User> findUsers(ExtPager pager) {
		String hql = "from User u";
		try {
			Query query = this.createPagerQuery(User.class, hql, pager);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
