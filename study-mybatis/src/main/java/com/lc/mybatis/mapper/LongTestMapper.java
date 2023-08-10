package com.lc.mybatis.mapper;

import com.lc.mybatis.domain.LongTest;

public interface LongTestMapper {
    int insert(LongTest record);

    int insertSelective(LongTest record);
}