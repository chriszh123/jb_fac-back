package com.ruoyi.fac.service;

import com.ruoyi.fac.domain.Business;

import java.util.List;

/**
 * 商家 服务层
 *
 * @author ruoyi
 * @date 2018-12-24
 */
public interface IBusinessService {
    /**
     * 查询商家信息
     *
     * @param id 商家ID
     * @return 商家信息
     */
    Business selectBusinessById(Integer id);

    /**
     * 查询商家列表
     *
     * @param business 商家信息
     * @return 商家集合
     */
    List<Business> selectBusinessList(Business business);

    /**
     * 新增商家
     *
     * @param business 商家信息
     * @return 结果
     */
    int insertBusiness(Business business);

    /**
     * 修改商家
     *
     * @param business 商家信息
     * @return 结果
     */
    int updateBusiness(Business business);

    /**
     * 删除商家信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteBusinessByIds(String ids);

}
