/**
 * 
 */
package com.voucher.service.sys;

import java.util.List;

import com.voucher.entity.sys.SysUser;
import com.voucher.pojo.ExtPager;
import com.voucher.service.BaseService;
import com.voucher.vo.MerchantVO;


/**
 *
 */
public interface SysUserService extends BaseService<SysUser> {
	public void register(SysUser user);
	public void save(SysUser user, List<String> roleIds);
	public void update(SysUser user, List<String> roleIds);
	public SysUser findUserByAccount(String account);
	public SysUser findUserByAccountAndPassword(String account, String password);
	public List<MerchantVO> findUsersByRealName(ExtPager pager, String realName);
	public int getTotalCount();
	public void resetPassword(SysUser user);
	public SysUser findUserByPhoneNo(String mobile);
	public String findPassword(SysUser user);
	public void deleteMerchantById(int id);
	public SysUser findMerchantById(int id);
}
