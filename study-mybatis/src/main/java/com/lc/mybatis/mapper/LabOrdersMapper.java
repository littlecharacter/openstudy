package com.lc.mybatis.mapper;

import com.lc.mybatis.domain.LabOrders;

public interface LabOrdersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LabOrders record);

    int insertSelective(LabOrders record);

    LabOrders selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LabOrders record);

    int updateByPrimaryKey(LabOrders record);
}