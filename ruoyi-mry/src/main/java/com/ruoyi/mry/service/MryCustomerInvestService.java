package com.ruoyi.mry.service;

import com.ruoyi.mry.model.MryCustomer;
import com.ruoyi.mry.model.MryCustomerInvest;
import com.ruoyi.mry.model.MryShop;
import com.ruoyi.system.domain.SysUser;

import java.util.List;
import java.util.Map;

public interface MryCustomerInvestService {

    List<MryCustomerInvest> selectCustomerInvests(MryCustomerInvest customerInvest, List<MryShop> shops, Map<Long, MryCustomer> customers);

    int insertCustomerInvest(MryCustomerInvest customerInvest);

    MryCustomerInvest selectCustomerInvest(Long id);

    int updateCustomerInvest(MryCustomerInvest customerInvest);

    int deleteCustomerInvestByIds(String ids, SysUser user);
}
