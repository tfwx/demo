package com.ylsh.survey.service;

import java.util.List;
import java.util.Map;

import com.ylsh.survey.pojo.TbNaireCategory;


/**
 * @description:问卷分类Service接口
 * @author ylsh
 * @date 2017年11月30日 下午4:53:27
 */
public interface NaireCategoryService {
	
	List<TbNaireCategory> getAll();
	
	Map<String, TbNaireCategory> getAllAsMap();
	
	TbNaireCategory get(Byte id);
	
	List<Byte> selectChildByParentId(Byte parentId);
	
}
