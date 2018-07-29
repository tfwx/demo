package com.ylsh.survey.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.ylsh.survey.pojo.TbNaire;
import com.ylsh.survey.pojo.TbNaireExample;

public interface TbNaireMapper {
    int countByExample(TbNaireExample example);

    int deleteByExample(TbNaireExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbNaire record);

    int insertSelective(TbNaire record);

    List<TbNaire> selectByExample(TbNaireExample example);

    TbNaire selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbNaire record, @Param("example") TbNaireExample example);

    int updateByExample(@Param("record") TbNaire record, @Param("example") TbNaireExample example);

    int updateByPrimaryKeySelective(TbNaire record);

    int updateByPrimaryKey(TbNaire record);

	List<TbNaire> list(@Param("queryParameter")Map<String, Object> queryParameter);
	
	TbNaire getStatisticsData(Long id);
	
	List<TbNaire> getRecycledData(Long userId);
	
	TbNaire test(Long naireId);

}