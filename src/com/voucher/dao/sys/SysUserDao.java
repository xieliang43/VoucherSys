/**
 * 
 */
package com.voucher.dao.sys;

import java.util.List;

import com.voucher.dao.BaseDao;
import com.voucher.entity.sys.SysUser;
import com.voucher.pojo.ExtPager;


/**
 *
 */
public interface SysUserDao extends BaseDao<SysUser> {
	public SysUser findUserByAccountAndPassword(String account, String password);
	public List<SysUser> findUsersByRealName(ExtPager pager, String realName);
	public List<SysUser> findUsers(ExtPager pager);
	public SysUser findUserByAccount(String account);
	public SysUser findUserByPhoneNo(String mobile);
}
