package com.ylsh.survey.mapper;

import com.ylsh.survey.pojo.TbOption;
import com.ylsh.survey.pojo.TbOptionExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface TbOptionMapper {
    int countByExample(TbOptionExample example);

    int deleteByExample(TbOptionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbOption record);
    
    int batchInsert(List<TbOption> optionList);

    int insertSelective(TbOption record);

    List<TbOption> selectByExample(TbOptionExample example);

    TbOption selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbOption record, @Param("example") TbOptionExample example);

    int updateByExample(@Param("record") TbOption record, @Param("example") TbOptionExample example);

    int updateByPrimaryKeySelective(TbOption record);

    int updateByPrimaryKey(TbOption record);
}