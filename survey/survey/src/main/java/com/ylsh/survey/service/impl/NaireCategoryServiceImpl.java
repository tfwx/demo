package com.ylsh.survey.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ylsh.survey.mapper.TbNaireCategoryMapper;
import com.ylsh.survey.pojo.TbNaireCategory;
import com.ylsh.survey.pojo.TbNaireCategoryExample;
import com.ylsh.survey.service.NaireCategoryService;


/**
 * @description:问卷类型管理Service
 * @author ylsh
 * @date 2017年11月30日 下午4:40:47
 */
@Transactional(readOnly = true)
@Service
public class NaireCategoryServiceImpl implements NaireCategoryService {

	@Autowired
	private TbNaireCategoryMapper naireCategoryMapper;


	/**
	 * @description: 获取问卷所有的分类
	 * @author ylsh
	 * @date 2017年11月30日 下午4:50:04
	 * @return
	 * @see com.ylsh.survey.service.NaireCategoryService#getAllNaireCategory()
	 */
	@Override
	@Cacheable(value="NaireCache", key="'naireCategoryList'")
	public List<TbNaireCategory> getAll() {
		// 查询所有问卷分类信息
		TbNaireCategoryExample example = new TbNaireCategoryExample();
		List<TbNaireCategory> list = naireCategoryMapper.selectByExample(example);
		return list;
	}

	/**
	 * @description: 根据类型ID获取类型信息
	 * @author ylsh
	 * @date 2017年11月30日 下午4:50:19
	 * @param id
	 * @return
	 * @see com.ylsh.survey.service.NaireCategoryService#getCategoryById(java.lang.Byte)
	 */
	@Override
	public TbNaireCategory get(Byte id) {
		// 根据Id查询问卷分类信息
		TbNaireCategory category = naireCategoryMapper.selectByPrimaryKey(id);
		return category;
	}

	
	/**
	 * @description: 查询问卷所有的分类信息(并缓存)
	 * @author ylsh
	 * @date 2018年2月24日 下午6:00:32 
	 * @return
	 * @see com.ylsh.survey.service.NaireCategoryService#getAllAsMap()
	 */
	@Override
	@Cacheable(value="NaireCache", key="'naireCategoryMap'")
	public Map<String, TbNaireCategory> getAllAsMap() {
		// 查询问卷所有的分类信息
		List<TbNaireCategory> naireCategoryList = getAll();
		// 把List转为Map键值对存储(LinkedHashMap可以保证遍历顺序添加顺序一致)
		Map<String, TbNaireCategory> naireCategoryMap = new LinkedHashMap<String, TbNaireCategory>();
		for (TbNaireCategory category : naireCategoryList) {
			naireCategoryMap.put(category.getId().toString(), category);
		}
		return naireCategoryMap;
	}

	/**
	 * @description: 查询父分类下的所有子分类id集合
	 * @author ylsh
	 * @date 2018年4月9日 上午9:14:20 
	 * @param parentId
	 * @return
	 * @see com.ylsh.survey.service.NaireCategoryService#selectChildByParentId(java.lang.Byte)
	 */
	@Override
	public List<Byte> selectChildByParentId(Byte parentId) {
		return naireCategoryMapper.selectChildByParentId(parentId);
	}
	
	

}
