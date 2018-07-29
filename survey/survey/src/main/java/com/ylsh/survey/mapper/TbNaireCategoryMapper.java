package com.ylsh.survey.mapper;

import com.ylsh.survey.pojo.TbNaireCategory;
import com.ylsh.survey.pojo.TbNaireCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbNaireCategoryMapper {
    int countByExample(TbNaireCategoryExample example);

    int deleteByExample(TbNaireCategoryExample example);

    int deleteByPrimaryKey(Byte id);

    int insert(TbNaireCategory record);

    int insertSelective(TbNaireCategory record);

    List<TbNaireCategory> selectByExample(TbNaireCategoryExample example);

    TbNaireCategory selectByPrimaryKey(Byte id);

    int updateByExampleSelective(@Param("record") TbNaireCategory record, @Param("example") TbNaireCategoryExample example);

    int updateByExample(@Param("record") TbNaireCategory record, @Param("example") TbNaireCategoryExample example);

    int updateByPrimaryKeySelective(TbNaireCategory record);

    int updateByPrimaryKey(TbNaireCategory record);
    
    List<Byte> selectChildByParentId(Byte parentId);
}