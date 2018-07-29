package com.ylsh.survey.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ylsh.survey.mapper.TbOptionMapper;
import com.ylsh.survey.service.OptionService;


/**
 * @description:选项管理Service
 * @author ylsh
 * @date 2017年11月30日 下午4:55:10
 */
@Transactional
@Service
public class OptionServiceImpl implements OptionService {
	
	@Autowired
	private TbOptionMapper optionMapper;

	
	/**
	 * @description:删除选项
	 * @author ylsh
	 * @date 2017年11月30日 下午4:56:01
	 * @param optionId
	 * @param naireId
	 * @see com.ylsh.survey.service.OptionService#delete(java.lang.Long, java.lang.Long)
	 */
	@Override
	@CacheEvict(value="NaireCache", key="'naire' + #naireId")
	public void delete(Long optionId, Long naireId) {
		optionMapper.deleteByPrimaryKey(optionId);
	}

}
