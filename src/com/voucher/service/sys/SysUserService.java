/**
 * 
 */
package com.voucher.service.sys;

import java.util.List;

import com.voucher.entity.sys.SysUser;
import com.voucher.pojo.ExtPager;
import com.voucher.pojo.MerchantVO;


/**
 *
 */
public interface SysUserService {
	public void save(SysUser user);
	public void register(SysUser user);
	public void save(SysUser user, List<String> roleIds);
	public void update(SysUser user);
	public void update(SysUser user, List<String> roleIds);
	public void delete(SysUser user);
	public SysUser findUserById(int userId);
	public SysUser findUserByAccount(String account);
	public SysUser findUserByAccountAndPassword(String account, String password);
	public List<MerchantVO> findUsersByRealName(ExtPager pager, String realName);
	public int getTotalCount();
	public void deleteById(int userId);
	public void resetPassword(SysUser user);
	public SysUser findUserByPhoneNo(String mobile);
	public String findPassword(SysUser user);
}
