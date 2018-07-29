package com.ylsh.survey.mapper;

import com.ylsh.survey.pojo.TbBriefAnswer;
import com.ylsh.survey.pojo.TbBriefAnswerExample;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface TbBriefAnswerMapper {
    int countByExample(TbBriefAnswerExample example);

    int deleteByExample(TbBriefAnswerExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbBriefAnswer record);

    int insertSelective(TbBriefAnswer record);

    List<TbBriefAnswer> selectByExample(TbBriefAnswerExample example);

    TbBriefAnswer selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbBriefAnswer record, @Param("example") TbBriefAnswerExample example);

    int updateByExample(@Param("record") TbBriefAnswer record, @Param("example") TbBriefAnswerExample example);

    int updateByPrimaryKeySelective(TbBriefAnswer record);

    int updateByPrimaryKey(TbBriefAnswer record);

	int batchInsert(List<TbBriefAnswer> briefAnswerList);

	void deleteByNaireIdAndRespondentId(@Param("naireId")Long naireId, @Param("respondentId")Long respondentId);

}