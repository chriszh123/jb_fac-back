/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2020/1/17
 * Description: MryCustomerPro
 */
package com.ruoyi.mry.service;

import com.ruoyi.mry.model.MryCustomer;
import com.ruoyi.mry.model.MryCustomerProItem;
import com.ruoyi.mry.model.MryServicePro;
import com.ruoyi.mry.model.MryShop;
import com.ruoyi.system.domain.SysUser;

import java.util.List;
import java.util.Map;

/**
 * MryCustomerPro
 * @author zhangguifeng
 * @create 2020-01-17 17:32
 **/
public interface MryCustomerProItemService {

    List<MryCustomerProItem> selectCustomerProItems(MryCustomerProItem customerProItem, List<MryShop> shops, Map<Long, MryCustomer> customers
            , List<MryServicePro> servicePros);

    int insertCustomerProItem(MryCustomerProItem customerProItem);

    MryCustomerProItem selectCustomerProItemById(Long id);

    int updateCustomerProItem(MryCustomerProItem customerProItem);

    int deleteCustomerProItemByIds(String ids, SysUser user);
}
