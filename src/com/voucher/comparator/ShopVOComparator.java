package com.voucher.comparator;

import java.util.Comparator;

import com.voucher.pojo.ShopVO;

public class ShopVOComparator implements Comparator<ShopVO> {

	@Override
	public int compare(ShopVO o1, ShopVO o2) {
		return o1.getDistance() - o2.getDistance();
	}

}
