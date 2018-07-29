package com.ylsh.survey.mapper;

import com.ylsh.survey.pojo.TbQuestion;
import com.ylsh.survey.pojo.TbQuestionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbQuestionMapper {
    int countByExample(TbQuestionExample example);

    int deleteByExample(TbQuestionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbQuestion record);
    
    int batchInsert(List<TbQuestion> questionList);

    int insertSelective(TbQuestion record);

    List<TbQuestion> selectByExample(TbQuestionExample example);

    TbQuestion selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbQuestion record, @Param("example") TbQuestionExample example);

    int updateByExample(@Param("record") TbQuestion record, @Param("example") TbQuestionExample example);

    int updateByPrimaryKeySelective(TbQuestion record);

    int updateByPrimaryKey(TbQuestion record);
}