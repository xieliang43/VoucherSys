package com.voucher.service.sys;

import java.util.List;

import com.voucher.vo.UserRoleVO;

public interface SysUserRoleService {
	public List<UserRoleVO> getUserRoleByUserId(int userId);
}
