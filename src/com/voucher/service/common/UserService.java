/**
 * 
 */
package com.voucher.service.common;

import java.util.List;

import com.voucher.entity.common.User;
import com.voucher.pojo.ExtPager;
import com.voucher.service.BaseService;

/**
 *
 */
public interface UserService extends BaseService<User> {
	public User findUserByPhoneNo(String phoneNo);
	public User findUserByPhoneAndPassword(String phoneNo, String password);
	public List<User> findUsersByPhoneNo(ExtPager pager, String phoneNo);
	public int getTotalCount();
	public void deleteUserById(int id);
	public User findUserById(int id);
}
