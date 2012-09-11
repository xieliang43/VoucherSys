/**
 * 
 */
package com.voucher.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.dao.DataAccessException;

import com.voucher.dao.UserDao;
import com.voucher.entity.User;
import com.voucher.pojo.ExtPager;

/**
 *
 */
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

	@Override
	public void create(User user) {
		try {
			this.getJpaTemplate().persist(user);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(User user) {
		try {
			this.getJpaTemplate().merge(user);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(User user) {
		try {
			this.getJpaTemplate().remove(user);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public User findUserById(int userId) {
		try {
			return this.getJpaTemplate().find(User.class, userId);
		} catch (DataAccessException e) {
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

	@Override
	public int getTotalCount() {
		return findAll().size();
	}

	@Override
	public List<User> findAll() {
		String hql = "from User u";
		try {
			Query query = this.createQuery(hql);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
