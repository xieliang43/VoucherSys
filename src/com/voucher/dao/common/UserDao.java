package com.voucher.dao.common;

import java.util.List;

import com.voucher.dao.BaseDao;
import com.voucher.entity.common.User;
import com.voucher.pojo.ExtPager;

public interface UserDao extends BaseDao<User> {
	public User findUserByPhoneNo(String phoneNo);
	public User findUserByPhoneAndPassword(String phoneNo, String password);
	public List<User> findUsersByPhoneNo(ExtPager pager, String phoneNo);
	public List<User> findUsers(ExtPager pager);
}
