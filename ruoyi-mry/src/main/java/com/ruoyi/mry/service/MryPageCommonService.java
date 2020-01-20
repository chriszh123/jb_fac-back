/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2020/1/20
 * Description: 页面上的一些下拉选项内容
 */
package com.ruoyi.mry.service;

import com.ruoyi.mry.constant.MryConstant;
import com.ruoyi.mry.mapper.MryServiceProMapper;
import com.ruoyi.mry.mapper.MryShopMapper;
import com.ruoyi.mry.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 页面上的一些下拉选项内容
 * @author zhangguifeng
 * @create 2020-01-20 9:15
 **/
@Slf4j
@Service("mpcs")
public class MryPageCommonService {

    @Autowired
    private MryShopMapper shopMapper;

    public List<MryShop> getShops() {
        MryShopExample shopExample = new MryShopExample();
        shopExample.createCriteria().andIsDeletedEqualTo(false);
        shopExample.setOrderByClause(MryConstant.DEFAULT_ORDER_CLAUSE);

        List<MryShop> shops = this.shopMapper.selectByExample(shopExample);
        return shops;
    }
}
