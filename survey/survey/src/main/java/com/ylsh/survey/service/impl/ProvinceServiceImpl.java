package com.ylsh.survey.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ylsh.survey.mapper.TbProvinceMapper;
import com.ylsh.survey.pojo.TbProvince;
import com.ylsh.survey.pojo.TbProvinceExample;
import com.ylsh.survey.service.ProvinceService;

/**
 * @description: 省（市）数据管理Service
 * @author ylsh
 * @date 2018年4月19日 下午5:55:48
 */
@Service
public class ProvinceServiceImpl implements ProvinceService {
	
	@Autowired
	private TbProvinceMapper provinceMapper;

	
	/**
	 * @description: 查询所有省（市）数据，key:cid, value:TbProvince
	 * @author ylsh
	 * @date 2018年4月18日 下午8:26:44 
	 * @return
	 * @see com.ylsh.survey.service.ProvinceService#getProvinceAsMap()
	 */
	@Transactional(readOnly = true)
	@Cacheable(value="NaireCache", key="'provinceData'")
	@Override
	public Map<String, TbProvince> selectAllAsMap() {
		return provinceMapper.selectAllAsMap();
	}


	/**
	 * @description: 根据cityCode查询省（市）区域信息
	 * @author ylsh
	 * @date 2018年4月25日 下午7:38:27 
	 * @param code
	 * @return
	 * @see com.ylsh.survey.service.ProvinceService#getByCode(java.lang.String)
	 */
	@Transactional(readOnly = true)
	@Override
	public TbProvince getByCode(String cityCode) {
		TbProvinceExample example = new TbProvinceExample();
		TbProvinceExample.Criteria criteria= example.createCriteria();
		criteria.andCodeEqualTo(cityCode);
		List<TbProvince> list = provinceMapper.selectByExample(example);
		if (list != null && !list.isEmpty()) {
			return provinceMapper.selectByExample(example).get(0);
		}
		TbProvince province = new TbProvince();
		province.setId(1);
		return province;
	}

	
	/**
	 * @description: 查询该问卷所有省（市）来源数据
	 * @author ylsh
	 * @date 2018年4月19日 下午6:08:44 
	 * @param naireId
	 * @return
	 * @see com.ylsh.survey.service.ProvinceRecordService#staticAreaByNaireId(java.lang.Long)
	 */
	@Transactional(readOnly = true)
	@Cacheable(value="NaireCache", key="'areaStatistic' + #naireId")
	@Override
	public List<Map<String, Object>> getNaireAreaData(Long naireId) {
		return provinceMapper.getNaireAreaData(naireId);
	}

}
