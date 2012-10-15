package com.voucher.action;

public class BasePagerAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1723307566210136794L;
	
	protected int start;
	protected int limit;
	protected String dir;
	protected String sort;

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
}
