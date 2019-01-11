package com.ruoyi.fac.mapper;

import com.ruoyi.fac.domain.Business;

import java.util.Date;
import java.util.List;

/**
 * 商家 数据层
 *
 * @author ruoyi
 * @date 2018-12-24
 */
public interface BusinessMapper {
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
     * 删除商家
     *
     * @param id 商家ID
     * @return 结果
     */
    int deleteBusinessById(Integer id);

    /**
     * 批量删除商家
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteBusinessByIds(String[] ids);

    /**
     * 指定日期内的商家数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    int countBusinesses(Date startDate, Date endDate);
}