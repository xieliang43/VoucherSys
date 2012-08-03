package com.voucher.service;

import java.util.List;

import com.voucher.entity.Voucher;
import com.voucher.entity.sys.SysUser;
import com.voucher.exception.DataExistException;
import com.voucher.exception.DataNotFoundException;
import com.voucher.exception.ServiceConcurrentException;
import com.voucher.pojo.ExtPager;
import com.voucher.pojo.VchInstVO;
import com.voucher.pojo.VoucherVO;

public interface VoucherService {

	public List<VoucherVO> getCurrentMerchantVouchersByShopName(ExtPager pager,
			SysUser merchant, String shopName);

	public int getCurrentMerchantTotalCount(SysUser merchant);

	public void saveVoucher(Voucher voucher);

	public void updateVoucher(Voucher voucher);

	public void deleteById(int id);

	public List<Voucher> getEnabledVouchersLessThanCurrentDate();

	public VchInstVO getEnabledVouchersByShop(int shoId);

	public void purchaseVoucher(int userId, int viId)
			throws ServiceConcurrentException, DataExistException,
			DataNotFoundException;

	public Voucher findVoucherById(int id);

	public int getCurrentMerchantTotalCountByShopName(SysUser merchant,
			String shopName);

}
