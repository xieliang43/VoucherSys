package com.voucher.dao;

import com.voucher.entity.User;

public interface UserDao {
	public void create(User user);
	public void update(User user);
	public void delete(User user);
	public User findUserById(int userId);
	public User findUserByPhoneNo(String phoneNo);
	public User findUserByPhoneAndPassword(String phoneNo, String password);
}
