package com.ylsh.survey.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.ylsh.survey.dto.NaireAnswerDataRecord;
import com.ylsh.survey.pojo.TbAnswerRecord;
import com.ylsh.survey.pojo.TbAnswerRecordExample;
import com.ylsh.survey.pojo.TbQuestion;
import com.ylsh.survey.pojo.TbRespondentInfo;


public interface TbAnswerRecordMapper {
    int countByExample(TbAnswerRecordExample example);

    int deleteByExample(TbAnswerRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbAnswerRecord record);

    int insertSelective(TbAnswerRecord record);

    List<TbAnswerRecord> selectByExample(TbAnswerRecordExample example);

    TbAnswerRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbAnswerRecord record, @Param("example") TbAnswerRecordExample example);

    int updateByExample(@Param("record") TbAnswerRecord record, @Param("example") TbAnswerRecordExample example);

    int updateByPrimaryKeySelective(TbAnswerRecord record);

    int updateByPrimaryKey(TbAnswerRecord record);
    
    int insertRecord(@Param("record")NaireAnswerDataRecord record);    

	List<TbQuestion> getDetailInfoByRespondent(@Param("naireId") Long naireId, @Param("respondentId") Long respondentId);
	
	Map<String, Long> getIndexStatistics();

	List<TbQuestion> chart(Long naireId);
	
	List<TbQuestion> getNaireAnswerPercent(Long naireId);
	
	List<TbRespondentInfo> getRespondentNaireData(Long naireId);
	

}