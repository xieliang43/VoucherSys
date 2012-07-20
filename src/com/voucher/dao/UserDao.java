package com.voucher.dao;

import java.util.List;

import com.voucher.entity.User;
import com.voucher.pojo.ExtPager;

public interface UserDao {
	public void create(User user);
	public void update(User user);
	public void delete(User user);
	public User findUserById(int userId);
	public User findUserByPhoneNo(String phoneNo);
	public User findUserByPhoneAndPassword(String phoneNo, String password);
	public List<User> findUsersByPhoneNo(ExtPager pager, String phoneNo);
	public List<User> findUsers(ExtPager pager);
	public int getTotalCount();
	public List<User> findAll();
}
