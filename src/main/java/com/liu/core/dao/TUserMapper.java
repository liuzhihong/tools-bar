package com.liu.core.dao;

import com.liu.core.dao.annotation.IRepository;
import com.liu.core.entity.TUser;
import com.liu.core.entity.TUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

@IRepository
public interface TUserMapper {
    int countByExample(TUserExample example);

    int deleteByExample(TUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TUser record);

    int insertSelective(TUser record);

    List<TUser> selectByExample(TUserExample example);

    TUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TUser record, @Param("example") TUserExample example);

    int updateByExample(@Param("record") TUser record, @Param("example") TUserExample example);

    int updateByPrimaryKeySelective(TUser record);

    int updateByPrimaryKey(TUser record);
}