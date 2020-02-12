package com.ruoyi.mry.service;

import com.ruoyi.mry.model.MryCustomerInvest;
import com.ruoyi.system.domain.SysUser;

import java.util.List;

public interface MryCustomerInvestService {

    List<MryCustomerInvest> selectCustomerInvests(MryCustomerInvest customerInvest);

    int insertCustomerInvest(MryCustomerInvest customerInvest);

    MryCustomerInvest selectCustomerInvest(Long id);

    int updateCustomerInvest(MryCustomerInvest customerInvest);

    int deleteCustomerInvestByIds(String ids, SysUser user);
}
