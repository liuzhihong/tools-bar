package com.liu.core.dao;

import com.liu.core.dao.annotation.IRepository;
import com.liu.core.entity.TOuInfo;
import com.liu.core.entity.TOuInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

@IRepository
public interface TOuInfoMapper {
    int countByExample(TOuInfoExample example);

    int deleteByExample(TOuInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TOuInfo record);

    int insertSelective(TOuInfo record);

    List<TOuInfo> selectByExample(TOuInfoExample example);

    TOuInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TOuInfo record, @Param("example") TOuInfoExample example);

    int updateByExample(@Param("record") TOuInfo record, @Param("example") TOuInfoExample example);

    int updateByPrimaryKeySelective(TOuInfo record);

    int updateByPrimaryKey(TOuInfo record);
}