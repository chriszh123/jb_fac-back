/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2020/1/17
 * Description: MryServicePro
 */
package com.ruoyi.mry.service;

import com.ruoyi.mry.model.MryServicePro;
import com.ruoyi.system.domain.SysUser;

import java.util.List;
import java.util.Map;

/**
 * MryServicePro
 * @author zhangguifeng
 * @create 2020-01-17 17:35
 **/
public interface MryServiceProService {

    List<MryServicePro> selectMryServicePros(MryServicePro servicePro);

    int insertServicePro(MryServicePro servicePro);

    MryServicePro selectServiceProById(Long id);

    int updateServicePro(MryServicePro servicePro);

    int deleteServicePros(String ids, SysUser user);

    List<Map<String, Object>> staffTreeData(MryServicePro servicePro);
}
