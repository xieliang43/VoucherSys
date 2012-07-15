/**
 * 
 */
package com.voucher.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.dao.DataAccessException;

import com.voucher.dao.UserDao;
import com.voucher.entity.User;

/**
 * @author LL
 *
 */
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

	@Override
	public void create(User user) {
		try {
			this.getJpaTemplate().persist(user);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(User user) {
		try {
			this.getJpaTemplate().merge(user);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delete(User user) {
		try {
			this.getJpaTemplate().remove(user);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public User findUserById(int userId) {
		try {
			return this.getJpaTemplate().find(User.class, userId);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

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
			// TODO Auto-generated catch block
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
	
}
