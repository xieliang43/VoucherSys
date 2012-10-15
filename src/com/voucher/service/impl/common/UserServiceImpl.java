package com.voucher.service.impl.common;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.voucher.dao.common.UserDao;
import com.voucher.dao.common.UserVoucherDao;
import com.voucher.entity.common.User;
import com.voucher.entity.common.UserVoucher;
import com.voucher.pojo.ExtPager;
import com.voucher.service.common.UserService;
import com.voucher.service.impl.BaseServiceImpl;

public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
	private UserDao userDao;
	private UserVoucherDao userVoucherDao;

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

	@Override
	public List<User> findUsersByPhoneNo(ExtPager pager, String phoneNo) {
		List<User> users = null;
		if(StringUtils.isBlank(phoneNo)) {
			users = userDao.findUsers(pager);
		} else {
			users = userDao.findUsersByPhoneNo(pager, phoneNo);
		}
		return users;
	}

	@Override
	public int getTotalCount() {
		return userDao.getTotalCount(User.class);
	}
	
	@Override
	public void deleteUserById(int id) {
		List<UserVoucher> userVouchers = userVoucherDao.findUserInstancese(id);
		if(userVouchers != null && !userVouchers.isEmpty()) {
			for(UserVoucher userVoucher : userVouchers) {
				userVoucherDao.delete(userVoucher);
			}
		}
		getBaseDao().deleteById(User.class, id);
	}

	public UserVoucherDao getUserVoucherDao() {
		return userVoucherDao;
	}

	public void setUserVoucherDao(UserVoucherDao userVoucherDao) {
		this.userVoucherDao = userVoucherDao;
	}

	@Override
	public User findUserById(int id) {
		return this.findById(User.class, id);
	}

}
