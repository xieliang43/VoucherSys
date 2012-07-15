package com.voucher.pojo;

public class JsonVO {
	private String resultCode;
	private String resultInfo;
	private Object info;
	
	public JsonVO(){
		
	}
	
	
	/**
	 * @param resultCode
	 * @param resultInfo
	 * @param info
	 */
	public JsonVO(String resultCode, String resultInfo, Object info) {
		this.resultCode = resultCode;
		this.resultInfo = resultInfo;
		this.info = info;
	}


	/**
	 * @return the resultCode
	 */
	public String getResultCode() {
		return resultCode;
	}
	/**
	 * @param resultCode the resultCode to set
	 */
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	/**
	 * @return the resultInfo
	 */
	public String getResultInfo() {
		return resultInfo;
	}
	/**
	 * @param resultInfo the resultInfo to set
	 */
	public void setResultInfo(String resultInfo) {
		this.resultInfo = resultInfo;
	}
	/**
	 * @return the info
	 */
	public Object getInfo() {
		return info;
	}
	/**
	 * @param info the info to set
	 */
	public void setInfo(Object info) {
		this.info = info;
	}
}
