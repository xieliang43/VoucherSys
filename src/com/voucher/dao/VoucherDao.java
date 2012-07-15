package com.voucher.dao;

import java.util.List;

import com.voucher.entity.Voucher;

public interface VoucherDao {
	public void create(Voucher voucher);
	public void delete(Voucher voucher);
	public void update(Voucher voucher);
	public Voucher findVoucherById(int voucherId);
	public List<Voucher> findAll();
}
