/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2020/1/17
 * Description: MryShopCard
 */
package com.ruoyi.mry.service.impl;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.mry.constant.MryConstant;
import com.ruoyi.mry.exception.MryException;
import com.ruoyi.mry.mapper.MryShopCardMapper;
import com.ruoyi.mry.mapper.MryShopMapper;
import com.ruoyi.mry.model.MryShop;
import com.ruoyi.mry.model.MryShopCard;
import com.ruoyi.mry.model.MryShopCardExample;
import com.ruoyi.mry.model.MryShopExample;
import com.ruoyi.mry.model.MryStaff;
import com.ruoyi.mry.service.MryShopCardService;
import com.ruoyi.mry.util.MryTimeUtils;
import com.ruoyi.system.domain.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MryShopCard
 * @author zhangguifeng
 * @create 2020-01-17 18:00
 **/
@Slf4j
@Service("mryShopCardService")
public class MryShopCardServiceImpl implements MryShopCardService {

    @Autowired
    private MryShopCardMapper shopCardMapper;
    @Autowired
    private MryShopMapper shopMapper;

    @Override
    public List<MryShopCard> selectShopCards(MryShopCard shopCard) {
        MryShopCardExample example = new MryShopCardExample();
        MryShopCardExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(false);
        if (StringUtils.isNotBlank(shopCard.getName()) && StringUtils.isNotBlank(shopCard.getName().trim())) {
            criteria.andNameLike("%" + shopCard.getName().trim() + "%");
        }
        example.setOrderByClause(MryConstant.DEFAULT_ORDER_CLAUSE);
        List<MryShopCard> shopCards = this.shopCardMapper.selectByExample(example);

        if (CollectionUtils.isNotEmpty(shopCards)) {
            Map<Short, MryShop> shopMap = new HashMap<>();
            for (MryShopCard item : shopCards ) {
                if (!shopMap.containsKey(item.getShopId())) {
                    MryShop shop = this.shopMapper.selectByPrimaryKey(item.getShopId());
                    shopMap.put(item.getShopId(), shop);
                }
                item.setShopName(shopMap.get(item.getShopId()).getName().trim());
            }
        }

        return shopCards;
    }

    @Override
    public int insertShopCard(MryShopCard shopCard) {
        Date nowDate = new Date();
        shopCard.setIsDeleted(false);
        shopCard.setCreateTime(nowDate);
        shopCard.setUpdateTime(nowDate);

        return this.shopCardMapper.insertSelective(shopCard);
    }

    @Override
    public MryShopCard selectShopCard(Long id) {
        MryShopCard shopCard = this.shopCardMapper.selectByPrimaryKey(id);

        if (shopCard.getServiceStart() != null) {
            shopCard.setServiceStartStr(MryTimeUtils.date2Str(shopCard.getServiceStart(), MryTimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM));
        }
        if (shopCard.getServiceEnd() != null) {
            shopCard.setServiceEndStr(MryTimeUtils.date2Str(shopCard.getServiceEnd(), MryTimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM));
        }
        return shopCard;
    }

    @Override
    public int updateShopCard(MryShopCard shopCard) {
        Date nowDate = new Date();
        shopCard.setUpdateTime(nowDate);

        if (StringUtils.isNotBlank(shopCard.getServiceStartStr())) {
            shopCard.setServiceStart(MryTimeUtils.parseTime(shopCard.getServiceStartStr(), MryTimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM));
        }
        if (StringUtils.isNotBlank(shopCard.getServiceEndStr())) {
            shopCard.setServiceEnd(MryTimeUtils.parseTime(shopCard.getServiceEndStr(), MryTimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM));
        }

        return this.shopCardMapper.updateByPrimaryKeySelective(shopCard);
    }

    @Override
    public int deleteShopCards(String ids, SysUser user) {
        if (StringUtils.isBlank(ids)) {
            throw new MryException("主键id不能为空");
        }
        List<String> idsList = Arrays.asList(ids.split(","));
        List<Long> idsLongs = new ArrayList<>();
        for (String id : idsList) {
            if (StringUtils.isBlank(id)) {
                continue;
            }
            idsLongs.add(Long.valueOf(id));
        }
        if (CollectionUtils.isNotEmpty(idsLongs)) {
            MryShopCardExample example = new MryShopCardExample();
            example.createCriteria().andIsDeletedEqualTo(false).andIdIn(idsLongs);
            MryShopCard update = new MryShopCard();
            update.setIsDeleted(true);
            update.setUpdateTime(new Date());
            if (user != null) {
                update.setOperatorId(user.getUserId());
                update.setOperatorName(user.getUserName());
            }
            return this.shopCardMapper.updateByExampleSelective(update, example);
        }

        return 0;
    }
}
