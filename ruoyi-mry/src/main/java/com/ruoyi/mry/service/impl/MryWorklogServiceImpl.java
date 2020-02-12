/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2020/1/17
 * Description: MryWorklog
 */
package com.ruoyi.mry.service.impl;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.mry.constant.MryConstant;
import com.ruoyi.mry.exception.MryException;
import com.ruoyi.mry.mapper.MryWorklogMapper;
import com.ruoyi.mry.model.*;
import com.ruoyi.mry.service.MryWorklogService;
import com.ruoyi.system.domain.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * MryWorklog
 * @author zhangguifeng
 * @create 2020-01-17 18:08
 **/
@Slf4j
@Service("mryWorklogService")
public class MryWorklogServiceImpl implements MryWorklogService {

    @Autowired
    private MryWorklogMapper worklogMapper;

    @Override
    public List<MryWorklog> selectWorklogs(MryWorklog worklog) {

        final MryWorklogExample example = new MryWorklogExample();
        MryWorklogExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(false);
        if (StringUtils.isNotBlank(worklog.getRemark()) && StringUtils.isNotBlank(worklog.getRemark().trim())) {
            criteria.andRemarkLike("%" + worklog.getRemark().trim() + "%");
        }
        example.setOrderByClause(MryConstant.DEFAULT_ORDER_CLAUSE);

        List<MryWorklog> worklogs = this.worklogMapper.selectByExample(example);
        return worklogs;
    }

    @Override
    public int insertWorklog(MryWorklog worklog) {

        Date nowDate = new Date();
        worklog.setCreateTime(nowDate);
        worklog.setUpdateTime(nowDate);
        worklog.setIsDeleted(false);

        return this.worklogMapper.insertSelective(worklog);
    }

    @Override
    public MryWorklog selectWorklog(Long id) {
        return this.worklogMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateWorklog(MryWorklog worklog) {

        Date nowDate = new Date();
        worklog.setUpdateTime(nowDate);

        worklog.setContent(worklog.getContentEdit());

        return this.worklogMapper.updateByPrimaryKeySelective(worklog);
    }

    @Override
    public int deleteWorklogByIds(String ids, SysUser user) {
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
            final MryWorklogExample example = new MryWorklogExample();
            example.createCriteria().andIsDeletedEqualTo(false).andIdIn(idsLongs);
            MryWorklog update = new MryWorklog();
            update.setIsDeleted(true);
            update.setUpdateTime(new Date());
            if (user != null) {
                update.setOperatorId(user.getUserId());
                update.setOperatorName(user.getUserName());
            }

            return this.worklogMapper.updateByExampleSelective(update, example);
        }

        return 0;
    }
}
