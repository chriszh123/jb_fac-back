/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2020/1/17
 * Description: MryWorklog
 */
package com.ruoyi.mry.service;

import com.ruoyi.mry.model.MryWorklog;
import com.ruoyi.system.domain.SysUser;

import java.util.List;

/**
 * MryWorklog
 * @author zhangguifeng
 * @create 2020-01-17 18:08
 **/
public interface MryWorklogService {

    List<MryWorklog> selectWorklogs(MryWorklog worklog);

    int insertWorklog(MryWorklog worklog);

    MryWorklog selectWorklog(Long id);

    int updateWorklog(MryWorklog worklog);

    int deleteWorklogByIds(String ids, SysUser user);
}
