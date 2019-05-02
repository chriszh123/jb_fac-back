package com.ruoyi.fac.mapper;

import com.ruoyi.fac.domain.Order;
import com.ruoyi.fac.domain.Product;
import com.ruoyi.fac.model.FacOrder;
import com.ruoyi.fac.vo.QueryVo;
import org.apache.ibatis.annotations.Param;

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
    int cancelOrderByIds(@Param("array") String[] ids, @Param("remarkMngt") String remarkMngt);

    /**
     * 查询指定日期内的订单信息
     *
     * @param queryVo
     * @return
     */
    List<Order> queryRecentOrderInfo(QueryVo queryVo);

    /**
     * 指定日期内的订单数
     *
     * @param queryVo
     * @return
     */
    int countOrders(QueryVo queryVo);

    /**
     * 批量保存订单信息
     *
     * @param list
     */
    int batchInsertOrders(@Param("list") List<FacOrder> list);

    List<Order> orderList(QueryVo queryVo);

    int updateOrderStatus(QueryVo queryVo);

    /**
     * 查询指定订单信息
     *
     * @param id    订单id
     * @param token 当前用户id
     * @return
     */
    Order selectOrderByIdAndToken(@Param("id") long id, @Param("token") String token);

    /**
     * 更新指定订单对应的预支付id
     *
     * @param id       订单id
     * @param prepayId 预支付id
     * @return 影响数据条数
     */
    int updateOrderPrePayId(@Param("id") long id, @Param("prepayId") long prepayId);

    /**
     * 查询指定订单号对应的订单
     *
     * @param orderNo 订单号
     * @return
     */
    List<Order> selectOrderByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 支付完成后更新订单状态
     *
     * @param orderNo
     * @param status
     * @return
     */
    int updateOrderStatusAfterPayed(@Param("orderNo") String orderNo, @Param("status") int status);

    /**
     * 查询指定商品、相应状态对应的订单
     *
     * @param prodIds 商品ids
     * @param status  订单状态
     * @return List<Product>
     */
    List<Order> selectProductsByProdAndStatus(@Param("prodIds") List<Long> prodIds, @Param("status") List<Integer> status);
}