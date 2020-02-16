package com.ruoyi.mry.service;

import com.ruoyi.mry.model.MryShopCost;
import com.ruoyi.system.domain.SysUser;

import java.util.List;

public interface MryShopCostService {

    List<MryShopCost> selectShopCosts(MryShopCost shopCost);

    int insertShopCost(MryShopCost shopCost);

    MryShopCost selectShopCostById(Long id);

    int updateShopCost(MryShopCost shopCost);

    int deleteShopCostByIds(String ids, SysUser user);
}
