/**
 * 
 */
package com.voucher.dao;

import java.util.List;

import com.voucher.entity.ShopType;

/**
 *
 */
public interface VoucherTypeDao {
	public void create(ShopType voucherType);
	public void delete(ShopType voucherType);
	public void update(ShopType voucherType);
	public ShopType findVoucherTypeById(int vTypeId);
	public List<ShopType> findAll();
}
