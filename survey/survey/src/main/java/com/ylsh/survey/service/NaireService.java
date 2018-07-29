package com.ylsh.survey.service;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import com.github.pagehelper.PageInfo;
import com.ylsh.survey.pojo.TbNaire;


/**
 * @description:问卷Service接口
 * @author ylsh
 * @date 2017年11月30日 下午4:52:57
 */
public interface NaireService {
	
	PageInfo<TbNaire> list(Map<String, Object> queryParameter);
	
	TbNaire get(Long naireId);
	
	void save(TbNaire naire);
	
	void delete(Long[] naireIds);
	
	void update(TbNaire naire);
	
	TbNaire getStatisticsData(Long naireId);

	void downloadNaire(HttpServletResponse response, Long naireId);
	
	void copyNaire(Long userId, Long naireId);
	
	void recycle(Long naireId);

	void restore(Long[] naireIds);
	
	List<TbNaire> getRecycledData(Long userId);
	
	boolean isNaireBelongToUser(Long userId, Long naireId);
}
