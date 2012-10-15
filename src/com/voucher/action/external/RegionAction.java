package com.voucher.action.external;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.voucher.action.BaseAction;
import com.voucher.service.common.RegionService;
import com.voucher.vo.AreaVO;
import com.voucher.vo.JsonVO;

public class RegionAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6157582358663584560L;
	
	private RegionService regionService;

	public void getCities() throws IOException {
		Map<String, List<AreaVO>> map = regionService.getCityPrefixMap();
		if(map == null || map.isEmpty()) {
			JsonVO jErrorVO = new JsonVO(ZERO, "载入城市失败", null);
			String json = this.convertToJson(jErrorVO);
			this.sendJSonReturn(json);
			return;
		}
		JsonVO vo = new JsonVO(ONE, "获取城市成功", map);
		String json = this.convertToJson(vo);
		this.sendJSonReturn(json);
	}

	/**
	 * @return the regionService
	 */
	public RegionService getRegionService() {
		return regionService;
	}

	/**
	 * @param regionService the regionService to set
	 */
	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}
}
