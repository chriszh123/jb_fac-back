/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2020/1/17
 * Description: MryStaff
 */
package com.ruoyi.mry.service.impl;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.mry.constant.MryConstant;
import com.ruoyi.mry.exception.MryException;
import com.ruoyi.mry.mapper.MryShopMapper;
import com.ruoyi.mry.mapper.MryStaffMapper;
import com.ruoyi.mry.model.MryShop;
import com.ruoyi.mry.model.MryShopExample;
import com.ruoyi.mry.model.MryStaff;
import com.ruoyi.mry.model.MryStaffExample;
import com.ruoyi.mry.service.MryStaffService;
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
 * MryStaff
 * @author zhangguifeng
 * @create 2020-01-17 18:05
 **/
@Slf4j
@Service("mryStaffService")
public class MryStaffServiceImpl implements MryStaffService {

    @Autowired
    private MryStaffMapper staffMapper;

    @Autowired
    private MryShopMapper shopMapper;

    @Override
    public List<MryStaff> selectStaffs(MryStaff staff) {
        MryStaffExample staffExample = new MryStaffExample();
        MryStaffExample.Criteria criteria = staffExample.createCriteria();
        criteria.andIsDeletedEqualTo(false);
        if (StringUtils.isNotBlank(staff.getSex())) {
            criteria.andSexEqualTo(staff.getSex());
        }
        if (StringUtils.isNotBlank(staff.getName()) && StringUtils.isNotBlank(staff.getName().trim())) {
            criteria.andNameLike("%" + staff.getName() + "%");
        }
        staffExample.setOrderByClause(MryConstant.DEFAULT_ORDER_CLAUSE);
        List<MryStaff> staffs = this.staffMapper.selectByExample(staffExample);
        if (CollectionUtils.isNotEmpty(staffs)) {
            Map<Short, MryShop> shopMap = new HashMap<>();
            for (MryStaff item : staffs ) {
                if (!shopMap.containsKey(item.getShopId())) {
                    MryShop shop = this.shopMapper.selectByPrimaryKey(item.getShopId());
                    shopMap.put(item.getShopId(), shop);
                }
                item.setShopName(shopMap.get(item.getShopId()).getName().trim());
            }
        }

        return staffs;
    }

    @Override
    public int insertStaff(MryStaff staff) {
        Date nowDate = new Date();
        staff.setIsDeleted(false);
        staff.setCreateTime(nowDate);
        staff.setUpdateTime(nowDate);
        return this.staffMapper.insertSelective(staff);
    }

    @Override
    public MryStaff selectStaffById(Long id) {
        MryStaff staff = this.staffMapper.selectByPrimaryKey(id);
        if (staff != null && staff.getJoinTime() != null) {
            staff.setJoinTimeStr(MryTimeUtils.date2Str(staff.getJoinTime(), MryTimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM));
        }
        if (staff != null && staff.getGoTime() != null) {
            staff.setGoTimeStr(MryTimeUtils.date2Str(staff.getGoTime(), MryTimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM));
        }

        return staff;
    }

    @Override
    public int updateStaff(MryStaff staff) {
        Date nowDate = new Date();
        staff.setUpdateTime(nowDate);
        if (StringUtils.isNotBlank(staff.getJoinTimeStr())) {
            staff.setJoinTime(MryTimeUtils.parseTime(staff.getJoinTimeStr(), MryTimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM));
        }
        if (StringUtils.isNotBlank(staff.getGoTimeStr())) {
            staff.setGoTime(MryTimeUtils.parseTime(staff.getGoTimeStr(), MryTimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM));
        }

        return this.staffMapper.updateByPrimaryKeySelective(staff);
    }

    @Override
    public int deleteStaffByIds(String ids, SysUser user) {
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
            MryStaffExample staffExample = new MryStaffExample();
            staffExample.createCriteria().andIsDeletedEqualTo(false).andIdIn(idsLongs);
            MryStaff update = new MryStaff();
            update.setIsDeleted(true);
            update.setUpdateTime(new Date());
            if (user != null) {
                update.setOperatorId(user.getUserId());
                update.setOperatorName(user.getUserName());
            }

            return this.staffMapper.updateByExampleSelective(update, staffExample);
        }

        return 0;
    }
}
