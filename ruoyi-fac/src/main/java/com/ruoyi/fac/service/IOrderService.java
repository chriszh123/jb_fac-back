package com.ruoyi.fac.service;

import com.ruoyi.fac.domain.Order;
import com.ruoyi.fac.exception.FacException;
import com.ruoyi.fac.vo.FacStaticVo;
import com.ruoyi.fac.vo.OrderDiagramVo;
import com.ruoyi.fac.vo.OrderItemVo;
import com.ruoyi.fac.vo.client.*;
import com.ruoyi.system.domain.SysUser;

import java.util.List;

/**
 * 订单 服务层
 *
 * @author ruoyi
 * @date 2018-12-24
 */
public interface IOrderService {
    List<Order> selectFacOrderProductList(Order order);

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
     * 删除订单信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteOrderByIds(String ids);

    /**
     * 取消订单
     *
     * @param orderNo
     * @return 结果
     */
    int cancelOrderByOrderNo(String orderNo, String remarkMngt, SysUser user) throws FacException;

    /**
     * 订单详情
     *
     * @param orderNo
     * @return
     */
    OrderItemVo detailOrderByOrderNo(String orderNo);

    /**
     * 查询指定日期内的订单信息
     *
     * @param startDateStr
     * @param endDateStr
     * @return
     */
    OrderDiagramVo queryRecentOrderInfo(String startDateStr, String endDateStr);

    /**
     * 指定日期内FAC的统计信息
     *
     * @param startDateStr
     * @param endDateStr
     * @return
     */
    FacStaticVo queryFacStaticInfo(String startDateStr, String endDateStr);

    /**
     * 用户客户端创建订单
     *
     * @param order
     */
    OrderCreateRes createOrderFromClient(OrderCreateVo order) throws FacException;

    OrderStatisticsVo orderStatistics(String token);

    /**
     * 客户端商品查询接口
     *
     * @param token
     * @param status
     * @return
     */
    OrderListVo orderList(String token, int status);

    /**
     * 取消订单
     *
     * @param token
     * @param orderIds
     */
    void closeOrder(String token, String orderIds);

    /**
     * 指定订单详情
     *
     * @param id    订单id
     * @param token 用户token
     * @return OrderDetailVo
     */
    OrderDetailVo orderDetail(long id, String token);

    /**
     * 核销商品订单
     *
     * @param token
     * @param orderNo
     */
    void writeOffOrder(String token, String orderNo) throws Exception;

    /**
     * 变更订单状态
     *
     * @param orderNo
     * @param remarkMngt
     * @param status
     * @throws Exception
     */
    int changeStatus(String orderNo, String remarkMngt, Integer status, SysUser sysUser) throws Exception;
}
