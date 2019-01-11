package com.ruoyi.fac.mapper;

import com.ruoyi.fac.domain.BuyerBusiness;
import com.ruoyi.fac.domain.Order;

import java.util.Date;
import java.util.List;

/**
 * 订单 数据层
 *
 * @author ruoyi
 * @date 2019-01-06
 */
public interface OrderMapper {
    /**
     * 查询订单信息
     *
     * @param id 订单ID
     * @return 订单信息
     */
    Order selectOrderById(Long id);

    /**
     * 查询订单列表
     *
     * @param order 订单信息
     * @return 订单集合
     */
    List<Order> selectOrderList(Order order);

    /**
     * 新增订单
     *
     * @param order 订单信息
     * @return 结果
     */
    int insertOrder(Order order);

    /**
     * 修改订单
     *
     * @param order 订单信息
     * @return 结果
     */
    int updateOrder(Order order);

    /**
     * 删除订单
     *
     * @param id 订单ID
     * @return 结果
     */
    int deleteOrderById(Long id);

    /**
     * 批量删除订单
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteOrderByIds(String[] ids);

    /**
     * 取消订单
     *
     * @param ids
     * @return 结果
     */
    int cancelOrderByIds(String[] ids);

    /**
     * 查询指定日期内的订单信息
     *
     * @param startDate
     * @param endDate
     * @return
     */
    List<Order> queryRecentOrderInfo(Date startDate, Date endDate);

    /**
     * 指定日期内的订单数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    int countOrders(Date startDate, Date endDate);
}