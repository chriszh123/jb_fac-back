/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2020/1/17
 * Description: MryServicePro
 */
package com.ruoyi.mry.service.impl;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.mry.constant.MryConstant;
import com.ruoyi.mry.exception.MryException;
import com.ruoyi.mry.mapper.MryServiceProMapper;
import com.ruoyi.mry.mapper.MryShopMapper;
import com.ruoyi.mry.mapper.MryStaffMapper;
import com.ruoyi.mry.model.MryServicePro;
import com.ruoyi.mry.model.MryServiceProExample;
import com.ruoyi.mry.model.MryShop;
import com.ruoyi.mry.model.MryShopExample;
import com.ruoyi.mry.model.MryStaff;
import com.ruoyi.mry.model.MryStaffExample;
import com.ruoyi.mry.service.MryServiceProService;
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
 * MryServicePro
 * @author zhangguifeng
 * @create 2020-01-17 17:35
 **/
@Slf4j
@Service("serviceProService")
public class MryServiceProServiceImpl implements MryServiceProService {

    @Autowired
    private MryServiceProMapper serviceProMapper;
    @Autowired
    private MryShopMapper shopMapper;
    @Autowired
    private MryStaffMapper staffMapper;

    @Override
    public List<MryServicePro> selectMryServicePros(MryServicePro servicePro) {
        MryServiceProExample example = new MryServiceProExample();
        MryServiceProExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(false);
        if (StringUtils.isNotBlank(servicePro.getName()) && StringUtils.isNotBlank(servicePro.getName().trim())) {
            criteria.andNameLike("%" + servicePro.getName().trim() + "%");
        }
        example.setOrderByClause(MryConstant.DEFAULT_ORDER_CLAUSE);

        List<MryServicePro> servicePros = this.serviceProMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(servicePros)) {
            Map<Short, MryShop> shopMap = new HashMap<>();
            for (MryServicePro item : servicePros) {
                if (!shopMap.containsKey(item.getShopId())) {
                    MryShop shop = this.shopMapper.selectByPrimaryKey(item.getShopId());
                    shopMap.put(item.getShopId(), shop);
                }
                item.setShopName(shopMap.get(item.getShopId()).getName());
            }
        }
        return servicePros;
    }

    @Override
    public int insertServicePro(MryServicePro servicePro) {
        Date nowDate = new Date();
        servicePro.setIsDeleted(false);
        servicePro.setCreateTime(nowDate);
        servicePro.setUpdateTime(nowDate);

        // 解析员工信息
        if (StringUtils.isNotBlank(servicePro.getStaffId())) {
            String[] staffIds = servicePro.getStaffId().split(",");
            String staffRef, staffId = null;
            String[] staffRefArr = null;
            StringBuilder staffs = new StringBuilder();
            for (int i = 0, size = staffIds.length; i < size; i++) {
                // nodeType-id-pId,员工id："店面id + 员工ID"
                staffRef = staffIds[i];
                staffRefArr = staffRef.split("-");
                if (staffRefArr.length < 3 || !MryConstant.NODE_FIELD_TYPE_STAFF.equals(staffRefArr[0])) {
                    continue;
                }
                staffId = staffRefArr[1].substring(staffRefArr[2].length());
                staffs.append(staffId).append(",");
            }
            if (StringUtils.isNotBlank(staffs.toString())) {
                staffs = staffs.deleteCharAt(staffs.length() - 1);
            }
            servicePro.setStaffId(staffs.toString());
        } else {
            servicePro.setStaffId("");
        }

        return this.serviceProMapper.insertSelective(servicePro);
    }

    @Override
    public MryServicePro selectServiceProById(Long id) {
        MryServicePro servicePro = this.serviceProMapper.selectByPrimaryKey(id.shortValue());
        return servicePro;
    }

    @Override
    public int updateServicePro(MryServicePro servicePro) {
        Date nowDate = new Date();
        servicePro.setUpdateTime(nowDate);
        // 解析员工信息
        if (StringUtils.isNotBlank(servicePro.getStaffId())) {
            String[] staffIds = servicePro.getStaffId().split(",");
            String staffRef, staffId;
            String[] staffRefArr;
            StringBuilder staffs = new StringBuilder();
            for (int i = 0, size = staffIds.length; i < size; i++) {
                // nodeType-id-pId,员工id："店面id + 员工ID"
                staffRef = staffIds[i];
                staffRefArr = staffRef.split("-");
                if (staffRefArr.length < 3 || !MryConstant.NODE_FIELD_TYPE_STAFF.equals(staffRefArr[0])) {
                    continue;
                }
                staffId = staffRefArr[1].substring(staffRefArr[2].length());
                staffs.append(staffId).append(",");
            }
            if (StringUtils.isNotBlank(staffs.toString())) {
                staffs = staffs.deleteCharAt(staffs.length() - 1);
            }
            servicePro.setStaffId(staffs.toString());
        } else {
            servicePro.setStaffId("");
        }

        return this.serviceProMapper.updateByPrimaryKeySelective(servicePro);
    }

    @Override
    public int deleteServicePros(String ids, SysUser user) {
        if (StringUtils.isBlank(ids)) {
            throw new MryException("主键id不能为空");
        }
        List<String> idsList = Arrays.asList(ids.split(","));
        List<Short> idsLongs = new ArrayList<>();
        for (String id : idsList) {
            if (StringUtils.isBlank(id)) {
                continue;
            }
            idsLongs.add(Short.valueOf(id));
        }
        if (CollectionUtils.isNotEmpty(idsLongs)) {
            MryServiceProExample example = new MryServiceProExample();
            example.createCriteria().andIsDeletedEqualTo(false).andIdIn(idsLongs);
            MryServicePro update = new MryServicePro();
            update.setIsDeleted(true);
            update.setUpdateTime(new Date());
            if (user != null) {
                update.setOperatorId(user.getUserId());
                update.setOperatorName(user.getUserName());
            }

            return this.serviceProMapper.updateByExampleSelective(update, example);
        }

        return 0;
    }

    @Override
    public List<Map<String, Object>> staffTreeData(MryServicePro servicePro) {
        if (servicePro == null) {
            return null;
        }
        List<Map<String, Object>> datas = new ArrayList<>();

        MryShopExample shopExample = new MryShopExample();
        shopExample.createCriteria().andIsDeletedEqualTo(false);
        shopExample.setOrderByClause(MryConstant.DEFAULT_ORDER_CLAUSE);
        List<MryShop> shops = this.shopMapper.selectByExample(shopExample);
        if (CollectionUtils.isEmpty(shops)) {
            return datas;
        }
        List<Short> shopIds = new ArrayList<>();
        Map<String, Object> node;
        for (MryShop shop : shops) {
            node = new HashMap<>(16);
            node.put("id", shop.getId().toString());
            node.put("pId", "0");
            node.put("name", shop.getName());
            node.put("title", shop.getName());
            node.put("type", MryConstant.NODE_FIELD_TYPE_SHOP);
            node.put("checked", false);
            datas.add(node);
            shopIds.add(shop.getId());
        }

        MryStaffExample staffExample = new MryStaffExample();
        staffExample.createCriteria().andIsDeletedEqualTo(false).andShopIdIn(shopIds).andStatusEqualTo(new Byte("1"));
        staffExample.setOrderByClause(MryConstant.DEFAULT_ORDER_CLAUSE);
        List<MryStaff> staffs = this.staffMapper.selectByExample(staffExample);
        if (CollectionUtils.isEmpty(staffs)) {
            return datas;
        }
        Map<Short, List<MryStaff>> shop2Staffs = new HashMap<>();
        for (MryStaff staff : staffs) {
            shop2Staffs.putIfAbsent(staff.getShopId(), new ArrayList<>());
            shop2Staffs.get(staff.getShopId()).add(staff);
        }
        // 组装店面员工树
        for (Map.Entry<Short, List<MryStaff>> entry : shop2Staffs.entrySet()) {
            for (MryStaff item : entry.getValue()) {
                node = new HashMap<>(16);
                // 员工的Id值暂时用 "店面id + 员工ID"拼起来的值，防止给前端的树id重复
                String staffId = entry.getKey() + item.getId().toString();
                node.put("id", staffId);
                node.put("pId", entry.getKey());
                node.put("name", item.getName());
                node.put("title", item.getName());
                node.put("type", MryConstant.NODE_FIELD_TYPE_STAFF);
                node.put("checked", this.checkStaffSelected(servicePro, item.getId().toString()));
                datas.add(node);
            }
        }

        return datas;
    }

    private boolean checkStaffSelected(MryServicePro servicePro, String staffId) {
        // 指定服务项目中是否已经选择了指定的员工
        if (servicePro == null || servicePro.getId() == null || servicePro.getId().equals(Short.valueOf("0"))) {
            return false;
        }

        MryServicePro db = this.serviceProMapper.selectByPrimaryKey(servicePro.getId());
        String selectedStaffIds = db.getStaffId();
        if (StringUtils.isNotBlank(selectedStaffIds)) {
            List<String> selectedStaffIdsLsit = Arrays.asList(selectedStaffIds.split(","));
            return selectedStaffIdsLsit.contains(staffId);
        }

        return false;
    }
}
