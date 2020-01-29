/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2020/1/17
 * Description: MryCustomer
 */
package com.ruoyi.mry.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.mry.constant.MryConstant;
import com.ruoyi.mry.exception.MryException;
import com.ruoyi.mry.mapper.MryCustomerMapper;
import com.ruoyi.mry.mapper.MryCustomerPictureMapper;
import com.ruoyi.mry.mapper.MryShopMapper;
import com.ruoyi.mry.model.*;
import com.ruoyi.mry.service.MryCustomerService;
import com.ruoyi.mry.util.MryFileUtils;
import com.ruoyi.mry.vo.CustomerImgVo;
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
import java.util.stream.Collectors;

/**
 * MryCustomer
 * @author zhangguifeng
 * @create 2020-01-17 17:31
 **/
@Slf4j
@Service("mryCustomerService")
public class MryCustomerServiceImpl implements MryCustomerService {

    @Autowired
    private MryShopMapper shopMapper;
    @Autowired
    private MryCustomerMapper customerMapper;
    @Autowired
    private MryCustomerPictureMapper customerPictureMapper;

    @Override
    public List<MryCustomer> selectCustomers(MryCustomer customer) {
        MryCustomerExample example = new MryCustomerExample();
        MryCustomerExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(false);
        if (StringUtils.isNotBlank(customer.getName()) && StringUtils.isNotBlank(customer.getName().trim())) {
            criteria.andNameLike("%" + customer.getName().trim() + "%");
        }
        example.setOrderByClause(MryConstant.DEFAULT_ORDER_CLAUSE);
        List<MryCustomer> customers = this.customerMapper.selectByExample(example);

        if (CollectionUtils.isNotEmpty(customers)) {
            Map<Short, MryShop> shopMap = new HashMap<>();
            for (MryCustomer item : customers) {
                if (!shopMap.containsKey(item.getShopId())) {
                    MryShop shop = this.shopMapper.selectByPrimaryKey(item.getShopId());
                    shopMap.put(item.getShopId(), shop);
                }
                item.setShopName(shopMap.get(item.getShopId()).getName());
            }
        }

        return customers;
    }

    @Override
    public int insertCustomer(MryCustomer customer) {
        Date nowDate = new Date();
        customer.setIsDeleted(false);
        customer.setCreateTime(nowDate);
        customer.setUpdateTime(nowDate);

        return this.customerMapper.insertSelective(customer);
    }

    @Override
    public MryCustomer selectCustomerById(Long id) {
        MryCustomer customer = this.customerMapper.selectByPrimaryKey(id);

        // 当前客户名下的图片信息
        MryCustomerPictureExample pictureExample = new MryCustomerPictureExample();
        pictureExample.createCriteria().andIsDeletedEqualTo(false).andShopIdEqualTo(customer.getShopId()).andCustomerIdEqualTo(id);
        pictureExample.setOrderByClause("create_time asc");
        List<MryCustomerPicture> customerPictures = this.customerPictureMapper.selectByExample(pictureExample);
        if (CollectionUtils.isNotEmpty(customerPictures)) {
            List<String> pictures = customerPictures.stream().filter(item->StringUtils.isNotBlank(item.getPicture()))
                    .map(MryCustomerPicture::getPicture).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(pictures)) {
                String picturesStr = StringUtils.join(pictures, ",");
                customer.setPicture(picturesStr);
                customer.setImgPath(picturesStr);
            }
        }

        return customer;
    }

    @Override
    public int updateCustomer(MryCustomer customer) {
        Date nowDate = new Date();
        customer.setUpdateTime(nowDate);

        return this.customerMapper.updateByPrimaryKeySelective(customer);
    }

    @Override
    public int deleteCustomersByIds(String ids, SysUser user) {
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
            MryCustomerExample example = new MryCustomerExample();
            example.createCriteria().andIsDeletedEqualTo(false).andIdIn(idsLongs);
            MryCustomer update = new MryCustomer();
            update.setIsDeleted(true);
            update.setUpdateTime(new Date());
            if (user != null) {
                update.setOperatorId(user.getUserId());
                update.setOperatorName(user.getUserName());
            }

            return this.customerMapper.updateByExampleSelective(update, example);
        }

        return 0;
    }

    /**
     * 获取当前客户对应图片信息
     *
     * @param customer
     * @return
     */
    @Override
    public CustomerImgVo getCustomerImgs(MryCustomer customer) {
        CustomerImgVo vo = new CustomerImgVo();
        vo.setCode(MryConstant.AJAX_CODE_FAIL);
        if (customer == null || customer.getId() == null) {
            return vo;
        }

        MryCustomerPictureExample example = new MryCustomerPictureExample();
        example.createCriteria().andIsDeletedEqualTo(false).andCustomerIdEqualTo(customer.getId());
        example.setOrderByClause("create_time desc");
        List<MryCustomerPicture> customerPictures = this.customerPictureMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(customerPictures)) {
            return vo;
        }
        vo.setCode(MryConstant.AJAX_CODE_SUCCESS);
        List<String> pictureList = customerPictures.stream().filter(item ->StringUtils.isNotBlank(item.getPicture())).map(MryCustomerPicture::getPicture).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(pictureList)) {
            return vo;
        }
        String[] imgPaths = new String[pictureList.size()];
        imgPaths = pictureList.toArray(imgPaths);
        vo.setImgPaths(imgPaths);
        List<Map<String, Object>> cfgs = new ArrayList<>();
        Map<String, Object> cfg;
        String imgPath;
        for (MryCustomerPicture picture : customerPictures) {
            cfg = new HashMap<>(16);
            imgPath = picture.getPicture();
            if (StringUtils.isBlank(imgPath)) {
                continue;
            }
            cfg.put("caption", MryFileUtils.getInstance().getFileName(imgPath));
            cfg.put("size", MryFileUtils.getInstance().getFileSize(imgPath));
//            cfg.put("size", "122886");
            // 删除当前图片对应的参数: 客户id + "+" + imgPath
            String key = picture.getCustomerId().toString() + MryConstant.SEPARATOR_SEMICOLON + imgPath;
            cfg.put("key", key);
            cfg.put("width", "200px");
            // 每个图片元素上的小删除按钮对应的接口url地址
            cfg.put("url", "deletePic");
            cfgs.add(cfg);
        }
        vo.setCfg(cfgs);

        return vo;
    }

    @Override
    public int deletePic(String key, SysUser user) {
        log.info("-------------------------<deletePic> start, key : " + key);
        if (StringUtils.isBlank(key)) {
            return 0;
        }
        // 客户id + "+" + imgPath
        String[] keysArr = key.split(MryConstant.SEPARATOR_SEMICOLON);
        if (keysArr.length < 2) {
            log.info("-------------------------<deletePic> paramter is wrong, key : " + key);
            return 0;
        }
        String customerId = keysArr[0];
        String picture = keysArr[1];
        MryCustomerPicture update = new MryCustomerPicture();
        update.setIsDeleted(true);
        update.setUpdateTime(new Date());
        if (user != null) {
            update.setOperatorId(user.getUserId());
            update.setOperatorName(user.getUserName());
        }

        MryCustomerPictureExample example = new MryCustomerPictureExample();
        example.createCriteria().andIsDeletedEqualTo(false).andCustomerIdEqualTo(Long.valueOf(customerId)).andPictureEqualTo(picture);
        this.customerPictureMapper.updateByExampleSelective(update, example);

        return 1;
    }

    @Override
    public int editpicture(MryCustomer customer) {
        log.info("--------------------------<editpicture> paramters start, customer:" + JSONObject.toJSONString(customer));
        if (customer == null || customer.getId() == null || customer.getShopId() == null) {
            return 0;
        }

        List<Long> existIds = new ArrayList<>();
        List<String> existPictures = new ArrayList<>();
        List<String> pictureList = null;

        // 先收集之前已经保存过的图片，不做再次保存
        if (StringUtils.isNotBlank(customer.getPicture())) {
            String[] pictureArr = customer.getPicture().split(",");
            pictureList = Arrays.asList(pictureArr);
            MryCustomerPictureExample example = new MryCustomerPictureExample();
            example.createCriteria().andIsDeletedEqualTo(false).andShopIdEqualTo(customer.getShopId()).andCustomerIdEqualTo(customer.getId())
                    .andPictureIn(pictureList);
            List<MryCustomerPicture> customerPictures = this.customerPictureMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(customerPictures)) {
                existIds = customerPictures.stream().map(MryCustomerPicture::getId).collect(Collectors.toList());
                existPictures = customerPictures.stream().map(MryCustomerPicture::getPicture).collect(Collectors.toList());
            }
        }

        // 先删除后插入
        Date nowDate = new Date();
        MryCustomerPicture update = new MryCustomerPicture();
        update.setIsDeleted(true);
        update.setUpdateTime(nowDate);
        if (customer.getOperatorId() != null) {
            update.setOperatorId(customer.getOperatorId());
            update.setOperatorName(customer.getOperatorName());
        }

        MryCustomerPictureExample example = new MryCustomerPictureExample();
        MryCustomerPictureExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(false).andCustomerIdEqualTo(Long.valueOf(customer.getId())).andShopIdEqualTo(customer.getShopId());
        if (CollectionUtils.isNotEmpty(existIds)) {
            criteria.andIdNotIn(existIds);
        }
        this.customerPictureMapper.updateByExampleSelective(update, example);

        if (CollectionUtils.isNotEmpty(pictureList)) {
            for (String picture : pictureList) {
                if (StringUtils.isBlank(picture) || existPictures.contains(picture)) {
                    continue;
                }
                MryCustomerPicture customerPicture = new MryCustomerPicture();
                customerPicture.setShopId(customer.getShopId());
                customerPicture.setCustomerId(customer.getId());
                String fileName = picture.substring(picture.lastIndexOf("/") + 1);
                customerPicture.setFileName(fileName);
                customerPicture.setPicture(picture);
                customerPicture.setCreateTime(nowDate);
                customerPicture.setUpdateTime(nowDate);
                customerPicture.setOperatorId(customer.getOperatorId());
                customerPicture.setOperatorName(customer.getOperatorName());
                customerPicture.setIsDeleted(false);
                this.customerPictureMapper.insertSelective(customerPicture);
            }
        }

        return 1;
    }

    public static void main(String[] args) {
        String ss = "111.222.333.jpg";
        String[] ssArr = ss.split("\\.");
        String aaa = ssArr[ssArr.length - 2] + "." + ssArr[ssArr.length - 1];
        System.out.println(aaa);
    }
}
