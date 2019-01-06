package com.ruoyi.fac.service.impl;

import java.util.List;

import com.ruoyi.fac.domain.Buyer;
import com.ruoyi.fac.mapper.BuyerMapper;
import com.ruoyi.fac.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fac.mapper.OrderMapper;
import com.ruoyi.fac.domain.Order;
import com.ruoyi.fac.service.IOrderService;
import com.ruoyi.common.support.Convert;

/**
 * 订单 服务层实现
 *
 * @author ruoyi
 * @date 2018-12-24
 */
@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private BuyerMapper buyerMapper;

    /**
     * 查询订单信息
     *
     * @param id 订单ID
     * @return 订单信息
     */
    @Override
    public Order selectOrderById(Long id) {
        return orderMapper.selectOrderById(id);
    }

    /**
     * 查询订单列表
     *
     * @param order 订单信息
     * @return 订单集合
     */
    @Override
    public List<Order> selectOrderList(Order order) {
        order.setIsDeleted(0);
        return orderMapper.selectOrderList(order);
    }

    /**
     * 新增订单
     *
     * @param order 订单信息
     * @return 结果
     */
    @Override
    public int insertOrder(Order order) {
        return orderMapper.insertOrder(order);
    }

    /**
     * 修改订单
     *
     * @param order 订单信息
     * @return 结果
     */
    @Override
    public int updateOrder(Order order) {
        return orderMapper.updateOrder(order);
    }

    /**
     * 删除订单对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteOrderByIds(String ids) {
        return orderMapper.deleteOrderByIds(Convert.toStrArray(ids));
    }

    /**
     * 取消订单
     *
     * @param ids
     * @return 结果
     */
    @Override
    public int cancelOrderByIds(String ids) {
        return this.orderMapper.cancelOrderByIds(Convert.toStrArray(ids));
    }

    /**
     * 订单详情
     *
     * @param id
     * @return
     */
    @Override
    public OrderVo detailOrderById(Long id) {
        OrderVo orderVo = new OrderVo();
        Order order = this.orderMapper.selectOrderById(id);
        if (order != null) {
            orderVo.setId(order.getId().toString());
            orderVo.setUserName(order.getUserName());
            Buyer buyer = this.buyerMapper.selectBuyerById(order.getUserId());
            if (buyer != null) {
                orderVo.setPhoneNumber(buyer.getPhoneNumber());
                orderVo.setHarvestAddress(buyer.getHarvestAddress());
            }
            orderVo.setRemark(order.getRemark());
            orderVo.setShipCode(order.getShipCode());
            orderVo.setRemarkMngt(order.getRemarkMngt());
            orderVo.setShip(order.getShip().toString());
        }
        return orderVo;
    }
}
