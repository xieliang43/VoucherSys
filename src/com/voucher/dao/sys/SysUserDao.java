/**
 * 
 */
package com.voucher.dao.sys;

import java.util.List;

import com.voucher.entity.sys.SysUser;
import com.voucher.pojo.ExtPager;


/**
 * @author LL
 *
 */
public interface SysUserDao {
	public void save(SysUser user);
	public void update(SysUser user);
	public void delete(SysUser user);
	public SysUser findUserById(int userId);
	public SysUser findUserByAccountAndPassword(String account, String password);
	public List<SysUser> findUsersByRealName(ExtPager pager, String realName);
	public List<SysUser> findUsers(ExtPager pager);
	public List<SysUser> findUsers();
	public int getTotalCount();
}
