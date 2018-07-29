package com.ylsh.survey.mapper;

import com.ylsh.survey.pojo.TbProvince;
import com.ylsh.survey.pojo.TbProvinceExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

public interface TbProvinceMapper {
    int countByExample(TbProvinceExample example);

    int deleteByExample(TbProvinceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbProvince record);

    int insertSelective(TbProvince record);

    List<TbProvince> selectByExample(TbProvinceExample example);

    TbProvince selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbProvince record, @Param("example") TbProvinceExample example);

    int updateByExample(@Param("record") TbProvince record, @Param("example") TbProvinceExample example);

    int updateByPrimaryKeySelective(TbProvince record);

    int updateByPrimaryKey(TbProvince record);
    
    @MapKey("code")
    Map<String, TbProvince> selectAllAsMap();
    
    List<Map<String, Object>> getNaireAreaData(Long naireId);
}