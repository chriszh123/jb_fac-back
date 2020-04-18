package com.ruoyi.mry.mapper;

import com.ruoyi.mry.model.MryCustomer;

import java.util.List;
import java.util.Map;

public interface MryExtMapper {

    List<MryCustomer> queryCustomers(Map<Object, Object> params);
}
