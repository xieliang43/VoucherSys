package com.voucher.dao.impl;

import org.springframework.dao.DataAccessException;

import com.voucher.dao.PositionDao;
import com.voucher.entity.Position;

public class PositionDaoImpl extends BaseDaoImpl implements PositionDao {

	@Override
	public void save(Position position) {
		try {
			this.getJpaTemplate().persist(position);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Position position) {
		try {
			this.getJpaTemplate().remove(position);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Position position) {
		try {
			this.getJpaTemplate().merge(position);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

}
