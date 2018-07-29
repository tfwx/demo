package com.ylsh.survey.mapper;

import com.ylsh.survey.pojo.TbRespondentInfo;
import com.ylsh.survey.pojo.TbRespondentInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbRespondentInfoMapper {
    int countByExample(TbRespondentInfoExample example);

    int deleteByExample(TbRespondentInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbRespondentInfo record);

    int insertSelective(TbRespondentInfo record);

    List<TbRespondentInfo> selectByExample(TbRespondentInfoExample example);

    TbRespondentInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbRespondentInfo record, @Param("example") TbRespondentInfoExample example);

    int updateByExample(@Param("record") TbRespondentInfo record, @Param("example") TbRespondentInfoExample example);

    int updateByPrimaryKeySelective(TbRespondentInfo record);

    int updateByPrimaryKey(TbRespondentInfo record);
    
    TbRespondentInfo getDetailAndAddress(Long id);
    
}