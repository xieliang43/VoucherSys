package com.voucher.service;

import java.util.List;

import com.voucher.entity.Voucher;
import com.voucher.entity.sys.SysUser;
import com.voucher.pojo.ExtPager;
import com.voucher.pojo.VoucherVO;

public interface VoucherService {

	public List<VoucherVO> getCurrentMerchantVouchersByShopName(ExtPager pager,
			SysUser merchant, String shopName);

	public int getCurrentMerchantTotalCount(SysUser merchant);

	public void saveVoucher(Voucher voucher);

	public void updateVoucher(Voucher voucher);

	public void deleteById(int id);

}
