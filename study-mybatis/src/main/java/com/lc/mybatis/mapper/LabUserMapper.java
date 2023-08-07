package com.lc.mybatis.mapper;

import com.lc.mybatis.domain.LabUser;

public interface LabUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LabUser record);

    int insertSelective(LabUser record);

    LabUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LabUser record);

    int updateByPrimaryKey(LabUser record);
}