package com.ylsh.survey.service;

import java.util.List;
import java.util.Map;
import com.ylsh.survey.pojo.TbProvince;


public interface ProvinceService {
	
	Map<String, TbProvince> selectAllAsMap();
	
	TbProvince getByCode(String cityCode);
	
	public List<Map<String, Object>> getNaireAreaData(Long naireId);

}
