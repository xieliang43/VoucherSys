package com.voucher.service.impl;

import com.voucher.dao.UserDao;
import com.voucher.entity.User;
import com.voucher.service.UserService;

public class UserServiceImpl implements UserService {
	private UserDao userDao;

	@Override
	public void save(User user) {
		userDao.create(user);
	}

	@Override
	public void update(User user) {
		userDao.update(user);
	}

	@Override
	public void delete(User user) {
		userDao.delete(user);
	}

	@Override
	public User findUserById(int userId) {
		return userDao.findUserById(userId);
	}

	@Override
	public User findUserByPhoneNo(String phoneNo) {
		return userDao.findUserByPhoneNo(phoneNo);
	}

	@Override
	public User findUserByPhoneAndPassword(String phoneNo, String password) {
		return userDao.findUserByPhoneAndPassword(phoneNo, password);
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
