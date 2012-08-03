package com.voucher.dao;

import com.voucher.entity.Position;

public interface PositionDao {
	public void save(Position position);

	public void delete(Position position);

	public void update(Position position);
}
