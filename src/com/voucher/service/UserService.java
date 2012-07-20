/**
 * 
 */
package com.voucher.service;

import java.util.List;

import com.voucher.entity.User;
import com.voucher.pojo.ExtPager;

/**
 *
 */
public interface UserService {
	public void save(User user);
	public void update(User user);
	public void delete(User user);
	public User findUserById(int userId);
	public User findUserByPhoneNo(String phoneNo);
	public User findUserByPhoneAndPassword(String phoneNo, String password);
	public List<User> findUsersByPhoneNo(ExtPager pager, String phoneNo);
	public int getTotalCount();
	public void deleteById(int id);
}
