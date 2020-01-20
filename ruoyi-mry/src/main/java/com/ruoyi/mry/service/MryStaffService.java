/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2020/1/17
 * Description: MryStaff
 */
package com.ruoyi.mry.service;

import com.ruoyi.mry.model.MryStaff;
import com.ruoyi.system.domain.SysUser;

import java.util.List;

/**
 * MryStaff
 * @author zhangguifeng
 * @create 2020-01-17 18:05
 **/
public interface MryStaffService {

    List<MryStaff> selectStaffs(MryStaff staff);

    int insertStaff(MryStaff staff);

    MryStaff selectStaffById(Long id);

    int updateStaff(MryStaff staff);

    int deleteStaffByIds(String ids, SysUser user);
}
