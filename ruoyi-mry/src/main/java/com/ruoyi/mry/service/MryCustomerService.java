/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2020/1/17
 * Description: MryCustomer
 */
package com.ruoyi.mry.service;

import com.ruoyi.mry.model.MryCustomer;
import com.ruoyi.system.domain.SysUser;

import java.util.List;

/**
 * MryCustomer
 * @author zhangguifeng
 * @create 2020-01-17 17:30
 **/
public interface MryCustomerService {

    List<MryCustomer> selectCustomers(MryCustomer customer);

    int insertCustomer(MryCustomer customer);

    MryCustomer selectCustomerById(Long id);

    int updateCustomer(MryCustomer customer);

    int deleteCustomersByIds(String ids, SysUser user);
}
