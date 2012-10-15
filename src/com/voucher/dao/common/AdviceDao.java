package com.voucher.dao.common;

import java.util.List;

import com.voucher.dao.BaseDao;
import com.voucher.entity.common.Advice;
import com.voucher.pojo.ExtPager;

public interface AdviceDao extends BaseDao<Advice> {

	List<Advice> findAdvicesByMsg(ExtPager pager, String msg);

	List<Advice> findAdvices(ExtPager pager);

}
