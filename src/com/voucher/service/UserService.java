/**
 * 
 */
package com.voucher.service;

import com.voucher.entity.User;

/**
 * @author LL
 *
 */
public interface UserService {
	public void save(User user);
	public void update(User user);
	public void delete(User user);
	public User findUserById(int userId);
	public User findUserByPhoneNo(String phoneNo);
	public User findUserByPhoneAndPassword(String phoneNo, String password);
}
