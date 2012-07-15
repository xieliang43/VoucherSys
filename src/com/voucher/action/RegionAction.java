package com.voucher.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.voucher.entity.Region;
import com.voucher.pojo.AreaVO;
import com.voucher.pojo.JsonVO;
import com.voucher.service.RegionService;

public class RegionAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6157582358663584560L;
	
	private RegionService regionService;
	
	public void getCities() throws IOException {
		List<Region> regions = regionService.getRegionByType(2);
		if(regions == null || regions.isEmpty()) {
			JsonVO jErrorVO = new JsonVO("0", "No city found", null);
			String json = this.convertToJson(jErrorVO);
			getHttpServletResponse().getWriter().println(json);
			return;
		}
		List<AreaVO> areas = new ArrayList<AreaVO>();
		for(Region r : regions) {
			AreaVO area = new AreaVO(r.getId(), r.getName(), r.getRegionPrefix());
			areas.add(area);
		}
		JsonVO vo = new JsonVO("1", "Get cities successfully", areas);
		String json = this.convertToJson(vo);
		getHttpServletResponse().setCharacterEncoding("UTF-8");
		getHttpServletResponse().getWriter().println(json);
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
