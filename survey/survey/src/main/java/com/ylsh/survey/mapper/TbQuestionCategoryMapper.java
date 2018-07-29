package com.ylsh.survey.mapper;

import com.ylsh.survey.pojo.TbQuestionCategory;
import com.ylsh.survey.pojo.TbQuestionCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbQuestionCategoryMapper {
    int countByExample(TbQuestionCategoryExample example);

    int deleteByExample(TbQuestionCategoryExample example);

    int deleteByPrimaryKey(Byte id);

    int insert(TbQuestionCategory record);

    int insertSelective(TbQuestionCategory record);

    List<TbQuestionCategory> selectByExample(TbQuestionCategoryExample example);

    TbQuestionCategory selectByPrimaryKey(Byte id);

    int updateByExampleSelective(@Param("record") TbQuestionCategory record, @Param("example") TbQuestionCategoryExample example);

    int updateByExample(@Param("record") TbQuestionCategory record, @Param("example") TbQuestionCategoryExample example);

    int updateByPrimaryKeySelective(TbQuestionCategory record);

    int updateByPrimaryKey(TbQuestionCategory record);
}