package com.voucher.service.common;

import java.util.List;

import com.voucher.entity.common.Shop;
import com.voucher.entity.common.Voucher;
import com.voucher.entity.sys.SysUser;
import com.voucher.exception.DataExistException;
import com.voucher.exception.DataNotFoundException;
import com.voucher.exception.ServiceConcurrentException;
import com.voucher.pojo.ExtPager;
import com.voucher.service.BaseService;
import com.voucher.vo.ShopVoucherInstanceVO;
import com.voucher.vo.VchInstVO;
import com.voucher.vo.VoucherVO;

public interface VoucherService extends BaseService<Voucher> {

	public List<VoucherVO> getCurrentMerchantVouchersByShopName(ExtPager pager,
			SysUser merchant, String shopName);

	public int getCurrentMerchantTotalCount(SysUser merchant);

	public void deleteVoucherById(int id);
	
	public void deleteByShop(Shop shop);

	public List<Voucher> getEnabledVouchersLessThanCurrentDate();

	public VchInstVO getEnabledVouchersByShop(int shoId);

	public void purchaseVoucher(int userId, int viId)
			throws ServiceConcurrentException, DataExistException,
			DataNotFoundException;

	public Voucher findVoucherById(int id);

	public int getCurrentMerchantTotalCountByShopName(SysUser merchant,
			String shopName);

	public List<ShopVoucherInstanceVO> getEnabledShopVouchersByShop(
			int shopId);
	
	public void deleteVoucherInstancesByVoucher(int vchId);

}
