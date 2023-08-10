package com.lc.mybatis.mapper;

import com.lc.mybatis.domain.DecimalTest;

public interface DecimalTestMapper {
    int insert(DecimalTest record);

    int insertSelective(DecimalTest record);
}