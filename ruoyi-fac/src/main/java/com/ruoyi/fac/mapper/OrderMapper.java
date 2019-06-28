package com.ruoyi.fac.mapper;

import com.ruoyi.fac.domain.Order;
import com.ruoyi.fac.model.FacOrder;
import com.ruoyi.fac.model.FacOrderProduct;
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
    FacOrder selectOrderById(Long id);

    /**
     * 查询订单明细列表
     *
     * @param order 订单信息
     * @return 订单集合
     */
    List<FacOrderProduct> selectFacOrderProductList(Order order);

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
     * 批量删除订单
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteOrderByIds(String[] ids);

    /**
     * 查询指定日期内的订单信息
     *
     * @param queryVo
     * @return
     */
    List<FacOrder> queryRecentOrderInfo(QueryVo queryVo);

    /**
     * 指定日期内的订单数
     *
     * @param queryVo
     * @return
     */
    int countOrders(QueryVo queryVo);

    List<FacOrder> orderList(QueryVo queryVo);

    int updateOrderStatus(QueryVo queryVo);

    /**
     * 查询指定订单信息
     *
     * @param id    订单id
     * @param token 当前用户id
     * @return
     */
    FacOrder selectOrderByIdAndToken(@Param("id") long id, @Param("token") String token);

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
    List<FacOrder> selectProductsByProdAndStatus(@Param("prodIds") List<Long> prodIds, @Param("status") List<Integer> status);
}