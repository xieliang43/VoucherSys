package com.voucher.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.voucher.dao.UserVoucherDao;
import com.voucher.entity.UserVoucher;
import com.voucher.entity.Voucher;
import com.voucher.entity.VoucherInstance;
import com.voucher.entity.sys.SysUser;
import com.voucher.pojo.UserVoucherVO;
import com.voucher.service.UserVoucherService;
import com.voucher.util.DateUtil;
import com.voucher.util.PropertiesLoader;

public class UserVoucherServiceImpl implements UserVoucherService {

	private UserVoucherDao userVoucherDao;

	@Override
	public List<UserVoucherVO> getUserInstances(int userId) {
		List<UserVoucherVO> list = new ArrayList<UserVoucherVO>();
		List<UserVoucher> userVouchers = userVoucherDao
				.findUserInstancese(userId);
		if (userVouchers != null && !userVouchers.isEmpty()) {
			for (UserVoucher userVoucher : userVouchers) {
				VoucherInstance vi = userVoucher.getVoucherInstance();
				Voucher vch = vi.getVoucher();
				String baseVoucherImageUrl = PropertiesLoader.getInstance().getVoucherImageBaseUrl();
				UserVoucherVO viVo = new UserVoucherVO(userVoucher.getId(),
						baseVoucherImageUrl + vch.getImage(), vi.getVchKey() + vi.getId(), DateUtil
								.getInstance().getStringDateShort(
										vch.getEndDate()), vch.getPrice(),
						userVoucher.getIsUsed(), userVoucher.getIsActive());
				list.add(viVo);
			}
		}
		return list;
	}

	/**
	 * @return the userVoucherDao
	 */
	public UserVoucherDao getUserVoucherDao() {
		return userVoucherDao;
	}

	/**
	 * @param userVoucherDao
	 *            the userVoucherDao to set
	 */
	public void setUserVoucherDao(UserVoucherDao userVoucherDao) {
		this.userVoucherDao = userVoucherDao;
	}

	@Override
	public List<UserVoucher> getActiveUserVouchersLessThanCurrentDate() {
		List<UserVoucher> userVouchers = userVoucherDao
				.findActiveUserVouchers();
		if (userVouchers != null && !userVouchers.isEmpty()) {
			Iterator<UserVoucher> itr = userVouchers.iterator();
			while (itr.hasNext()) {
				UserVoucher uv = itr.next();
				if (uv.getVoucherInstance().getVoucher().getEndDate()
						.compareTo(new Date()) > 0) {
					itr.remove();
				}
			}
		}
		return userVouchers;
	}

	@Override
	public void updateUserVoucher(UserVoucher userVoucher) {
		userVoucherDao.update(userVoucher);
	}

	@Override
	public SysUser findMerchantByUserVoucher(int uvId) {
		return userVoucherDao.findMerchantByUserVoucher(uvId);
	}

	@Override
	public String useUserVoucher(int uvId) {
		UserVoucher userVoucher = userVoucherDao.findUserVoucherById(uvId);
		if(userVoucher == null) {
			return "00";
		}
		if(userVoucher.getIsUsed() == 1) {
			return "01";
		}
		userVoucher.setIsUsed((short)1);
		return "02";
	}

}
