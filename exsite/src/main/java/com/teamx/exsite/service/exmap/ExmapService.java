package com.teamx.exsite.service.exmap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teamx.exsite.model.mapper.exmap.ExmapMapper;
import com.teamx.exsite.model.vo.exmap.ExmapCoordinate;
import com.teamx.exsite.model.vo.exmap.ExmapExhibition;

@Service
public class ExmapService {

	@Autowired
	private ExmapMapper exmapMapper;
	
	public List<ExmapCoordinate> selectAllExmapCoordinates(String guname){
		return exmapMapper.selectAllExmapCoordinates(guname);
	}

	public List<String> getDistinctGunameList() {
		return exmapMapper.selectDistinctGuname();
	}

	public ExmapExhibition getRecentExhibitionInfo(String lat, String lot) {
        Map<String, String> params = new HashMap<>();
        params.put("lat", lat);
        params.put("lot", lot);
		return exmapMapper.findRecentExhibitionByCoordinates(lat, lot);
	}
	
}
