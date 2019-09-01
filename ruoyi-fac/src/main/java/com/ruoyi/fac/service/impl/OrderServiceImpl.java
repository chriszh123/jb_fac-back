package com.ruoyi.fac.service.impl;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.support.Convert;
import com.ruoyi.fac.cache.ProductCache;
import com.ruoyi.fac.domain.*;
import com.ruoyi.fac.enums.CashStatus;
import com.ruoyi.fac.enums.OrderStatus;
import com.ruoyi.fac.enums.ProductStatus;
import com.ruoyi.fac.exception.FacException;
import com.ruoyi.fac.mapper.*;
import com.ruoyi.fac.model.*;
import com.ruoyi.fac.service.IOrderService;
import com.ruoyi.fac.util.DecimalUtils;
import com.ruoyi.fac.util.FacCommonUtils;
import com.ruoyi.fac.util.TimeUtils;
import com.ruoyi.fac.vo.FacStaticVo;
import com.ruoyi.fac.vo.OrderDiagramVo;
import com.ruoyi.fac.vo.OrderItemVo;
import com.ruoyi.fac.vo.QueryVo;
import com.ruoyi.fac.vo.client.*;
import com.ruoyi.fac.vo.condition.QueryGoodVo;
import com.ruoyi.system.domain.SysUser;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 订单 服务层实现
 *
 * @author ruoyi
 * @date 2018-12-24
 */
@Service
public class OrderServiceImpl implements IOrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private BuyerMapper buyerMapper;
    @Autowired
    private BusinessMapper businessMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private FacProductWriteoffMapper facProductWriteoffMapper;
    @Autowired
    private BuyerBusinessMapper buyerBusinessMapper;
    @Autowired
    private FacOrderMapper facOrderMapper;
    @Autowired
    private FacCashMapper facCashMapper;
    @Autowired
    private FacProductMapper facProductMapper;
    @Autowired
    private FacOrderProductMapper facOrderProductMapper;
    @Autowired
    private FacBuyerMapper facBuyerMapper;
    @Autowired
    private FacBuyerBusinessMapper facBuyerBusinessMapper;
    @Autowired
    private FacProductWriteOffBeanMapper facProductWriteOffBeanMapper;
    @Autowired
    private FacKanjiaMapper kanjiaMapper;
    @Autowired
    private FacKanjiaJoinerMapper kanjiaJoinerMapper;

    @Autowired
    private ProductCache productCache;

    @Override
    public List<Order> selectFacOrderProductList(Order order) {
        List<FacOrderProduct> orderProducts = this.orderMapper.selectFacOrderProductList(order);
        return this.convert2Orders(orderProducts);
    }

    private List<Order> convert2Orders(List<FacOrderProduct> orderProducts) {
        final List<Order> orders = new ArrayList<>();
        if (CollectionUtils.isEmpty(orderProducts)) {
            return orders;
        }
        final Set<String> orderNos = new HashSet<>();
        for (FacOrderProduct orderProduct : orderProducts) {
            orderNos.add(orderProduct.getOrderNo());
        }
        final FacOrderExample orderExample = new FacOrderExample();
        orderExample.createCriteria().andIsDeletedEqualTo(false).andOrderNoIn(new ArrayList<>(orderNos));
        final List<FacOrder> facOrders = this.facOrderMapper.selectByExample(orderExample);
        // <orderNo, Order>
        Map<String, FacOrder> no2Order = new HashMap<>();
        if (CollectionUtils.isNotEmpty(facOrders)) {
            for (FacOrder order : facOrders) {
                no2Order.put(order.getOrderNo(), order);
            }
        }
        for (final FacOrderProduct orderProduct : orderProducts) {
            FacOrder facOrder = no2Order.get(orderProduct.getOrderNo());
            if (facOrder == null) {
                log.info("-----------------------order is not exist, orderNo :" + orderProduct.getOrderNo());
                continue;
            }
            Order order = new Order();
            orders.add(order);
            order.setId(orderProduct.getId());
            order.setOrderNo(orderProduct.getOrderNo());
            order.setToken(orderProduct.getToken());
            order.setOpenId(orderProduct.getOpenId());
            order.setNickName(orderProduct.getNickName());
            order.setProdId(orderProduct.getProdId());
            order.setProdName(orderProduct.getProdName());
            order.setProdNumber(orderProduct.getProdNumber());
            order.setPrice(orderProduct.getPrice());
            order.setUserScore(facOrder.getUserScore());
            order.setStatus(Integer.valueOf(facOrder.getStatus()));
            order.setPayTime(facOrder.getPayTime());
            order.setInviterId(orderProduct.getInviterId());
            order.setUserId(facOrder.getUserId());
            order.setUserName(facOrder.getUserName());
            order.setRemark(facOrder.getRemark());
            order.setRemarkMngt(facOrder.getRemarkMngt());
            order.setShip(Integer.valueOf(facOrder.getShip()));
            order.setCacelId(facOrder.getCacelId());
            order.setCacelName(facOrder.getCacelName());
            order.setCacelTime(facOrder.getCacelTime());
            order.setCreateTime(facOrder.getCreateTime());
        }
        // 核销时间
        FacProductWriteOffBeanExample beanExample = new FacProductWriteOffBeanExample();
        beanExample.createCriteria().andIsDeletedEqualTo(false).andOrderNoIn(new ArrayList<>(orderNos));
        List<FacProductWriteOffBean> beans = this.facProductWriteOffBeanMapper.selectByExample(beanExample);
        if (CollectionUtils.isNotEmpty(beans)) {
            Map<String, Date> order2WriteoffDate = new HashMap<>();
            for (FacProductWriteOffBean item : beans) {
                order2WriteoffDate.put(item.getOrderNo(), item.getWriteoffTime());
            }
            for (Order order : orders) {
                order.setWriteoffTime(order2WriteoffDate.get(order.getOrderNo()));
            }
        }

        return orders;
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
     * @param orderNo
     * @return 结果
     */
    @Override
    public int cancelOrderByOrderNo(String orderNo, String remarkMngt, SysUser user) throws FacException {
        if (StringUtils.isBlank(orderNo)) {
            throw new FacException("订单编号参数为空");
        }
        FacOrder facOrder = new FacOrder();
        facOrder.setStatus(Byte.valueOf(OrderStatus.CACELED.getCode().toString()));
        facOrder.setRemarkMngt(remarkMngt);
        facOrder.setUpdateTime(new Date());
        if (user != null) {
            facOrder.setOperatorId(user.getUserId());
            facOrder.setOperatorName(user.getUserName());
        }
        FacOrderExample example = new FacOrderExample();
        example.createCriteria().andIsDeletedEqualTo(false).andOrderNoEqualTo(orderNo);
        return this.facOrderMapper.updateByExampleSelective(facOrder, example);
    }

    /**
     * 订单详情
     *
     * @param orderNo
     * @return
     */
    @Override
    public OrderItemVo detailOrderByOrderNo(String orderNo) {
        OrderItemVo orderItemVo = new OrderItemVo();
        FacOrderExample orderExample = new FacOrderExample();
        orderExample.createCriteria().andIsDeletedEqualTo(false).andOrderNoEqualTo(orderNo);
        List<FacOrder> orders = this.facOrderMapper.selectByExample(orderExample);
        if (CollectionUtils.isEmpty(orders)) {
            return orderItemVo;
        }
        FacOrder order = orders.get(0);
        orderItemVo.setId(order.getId().toString());
        orderItemVo.setOrderNo(order.getOrderNo());
        orderItemVo.setUserName(order.getUserName());
        Buyer buyer = this.buyerMapper.selectBuyerById(order.getUserId());
        if (buyer != null) {
            // 取那个默认的收获地址里的电话号码:zgf
            orderItemVo.setPhoneNumber("");
        }
        orderItemVo.setRemark(order.getRemark());
        orderItemVo.setShipCode(order.getShipCode());
        orderItemVo.setRemarkMngt(order.getRemarkMngt());
        orderItemVo.setShip(order.getShip().toString());

        return orderItemVo;
    }

    /**
     * 查询指定日期内的订单信息
     *
     * @param startDateStr
     * @param endDateStr
     * @return
     */
    @Override
    public OrderDiagramVo queryRecentOrderInfo(String startDateStr, String endDateStr) {
        OrderDiagramVo vo = new OrderDiagramVo();
        Date startDate = null, endDate = null;
        try {
            if (!StringUtils.isEmpty(startDateStr) && !StringUtils.isEmpty(endDateStr)) {
                startDate = TimeUtils.parseTime(startDateStr, TimeUtils.DEFAULT_DATE_FORMAT);
                endDate = TimeUtils.parseTime(endDateStr, TimeUtils.DEFAULT_DATE_FORMAT);
            } else {
                // 最近一周日期: 2019-01-04, end = 2019-01-11
                endDate = new Date();
                startDate = TimeUtils.getDateByHours(endDate, -168);
            }
            if (startDate == null || endDate == null) {
                return vo;
            }
            List<Date> dateList = TimeUtils.getStaticDates(startDate, endDate);
            String[] xAxisData = new String[dateList.size()];
            String[] seriesOrderCount = new String[dateList.size()];
            String[] seriesOrderAmount = new String[dateList.size()];
            for (int i = 0, size = dateList.size(); i < size; i++) {
                xAxisData[i] = TimeUtils.date2Str(dateList.get(i), "");
                seriesOrderCount[i] = "0";
                seriesOrderAmount[i] = "0.0";
            }
            vo.setxAxisData(xAxisData);
            vo.setSeriesOrderCount(seriesOrderCount);
            vo.setSeriesOrderAmount(seriesOrderAmount);
            // 当前时间范围内产生的订单
            QueryVo queryVo = new QueryVo();
            queryVo.setStartDate(startDate);
            queryVo.setEndDate(endDate);
            List<FacOrder> orders = this.orderMapper.queryRecentOrderInfo(queryVo);
            if (CollectionUtils.isEmpty(orders)) {
                return vo;
            }
            Map<Date, Integer> date2OrderCount = new HashMap<>(16);
            Map<Date, BigDecimal> date2OrderAmount = new HashMap<>(16);
            Date date;
            int tempCount = 0;
            BigDecimal tempAmount = new BigDecimal("0.0");
            List<String> orderNos = new ArrayList<>();
            Map<Date, List<String>> createTime2OrderNo = new HashMap<>();
            for (FacOrder order : orders) {
                orderNos.add(order.getOrderNo());
                date = TimeUtils.parseTime(order.getCreateTime(), TimeUtils.DEFAULT_DATE_FORMAT);
                if (!date2OrderCount.containsKey(date)) {
                    date2OrderCount.put(date, 0);
                }
                tempCount = date2OrderCount.get(date);
                date2OrderCount.put(date, ++tempCount);
                // 指定创建日期对应的订单
                if (!createTime2OrderNo.containsKey(date)) {
                    createTime2OrderNo.put(date, new ArrayList<>());
                }
                createTime2OrderNo.get(date).add(order.getOrderNo());
            }
            FacOrderProductExample orderProductExample = new FacOrderProductExample();
            orderProductExample.createCriteria().andIsDeletedEqualTo(false).andOrderNoIn(orderNos);
            List<FacOrderProduct> orderProducts = this.facOrderProductMapper.selectByExample(orderProductExample);
            if (CollectionUtils.isNotEmpty(orderProducts) && MapUtils.isNotEmpty(createTime2OrderNo)) {
                for (Map.Entry<Date, List<String>> entry : createTime2OrderNo.entrySet()) {
                    // 对应统计的订单创建时间
                    Date createtime = entry.getKey();
                    orderNos = entry.getValue();
                    if (!date2OrderAmount.containsKey(createtime)) {
                        date2OrderAmount.put(createtime, new BigDecimal("0.0"));
                    }
                    for (FacOrderProduct item : orderProducts) {
                        // 对应订单下的商品总金额
                        if (orderNos.contains(item.getOrderNo())) {
                            tempAmount = date2OrderAmount.get(createtime);
                            tempAmount = tempAmount.add(DecimalUtils.mul(item.getPrice(), new BigDecimal(String.valueOf(item.getProdNumber()))));
                            date2OrderAmount.put(createtime, tempAmount);
                        }
                    }
                }
            }
            for (int i = 0, size = dateList.size(); i < size; i++) {
                seriesOrderCount[i] = date2OrderCount.containsKey(dateList.get(i)) ? date2OrderCount.get(dateList.get(i)).toString() : "0";
                seriesOrderAmount[i] = date2OrderAmount.containsKey(dateList.get(i)) ? date2OrderAmount.get(dateList.get(i)).toString() : "0.0";
            }
            vo.setxAxisData(xAxisData);
            vo.setSeriesOrderCount(seriesOrderCount);
            vo.setSeriesOrderAmount(seriesOrderAmount);
        } catch (Exception ex) {
            log.info("[queryRecentUserInfo] error", ex);
        }

        return vo;
    }

    /**
     * 指定日期内FAC的统计信息
     *
     * @param startDateStr
     * @param endDateStr
     * @return
     */
    @Override
    public FacStaticVo queryFacStaticInfo(String startDateStr, String endDateStr) {
        FacStaticVo vo = new FacStaticVo();
        Date startDate = null, endDate = null;
        if (!StringUtils.isEmpty(startDateStr)) {
            try {
                startDate = TimeUtils.parseTime(startDateStr, TimeUtils.DEFAULT_DATE_FORMAT);
            } catch (Exception ex) {
                log.info("[queryFacStaticInfo] error", ex);
            }
        }
        if (!StringUtils.isEmpty(endDateStr)) {
            try {
                endDate = TimeUtils.parseTime(endDateStr, TimeUtils.DEFAULT_DATE_FORMAT);
            } catch (Exception ex) {
                log.info("[queryFacStaticInfo] error", ex);
            }
        }
        // 统计项目
        String[] xAxis = new String[4];
        xAxis[0] = "订单";
        xAxis[1] = "用户";
        xAxis[2] = "商家";
        xAxis[3] = "商品";
        Integer[] seriesData = new Integer[4];
        // 1.当前订单数
        QueryVo queryVo = new QueryVo(startDate, endDate);
        int orderCount = this.orderMapper.countOrders(queryVo);
        seriesData[0] = orderCount;
        // 2.当前用户数
        int buyCount = this.buyerMapper.countBuyers(queryVo);
        seriesData[1] = buyCount;
        // 3.当前商家数
        int bizCount = this.businessMapper.countBusinesses(queryVo);
        seriesData[2] = bizCount;
        // 4.当前商品数
        int productCount = this.productMapper.countProducts(queryVo);
        seriesData[3] = productCount;

        vo.setStaticXAxis(xAxis);
        vo.setStaticData(seriesData);
        return vo;
    }

    /**
     * 用户客户端创建订单
     *
     * @param orderCreateVo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderCreateRes createOrderFromClient(OrderCreateVo orderCreateVo) throws FacException {
        OrderCreateRes res = new OrderCreateRes();
        if (StringUtils.isBlank(orderCreateVo.getGoodsJsonStr()) || StringUtils.isEmpty(orderCreateVo.getToken())) {
            return res;
        }
        List<GoodsJsonStrVo> goodsJsonStr = JSON.parseArray(orderCreateVo.getGoodsJsonStr(), GoodsJsonStrVo.class);
        // 当前用户信息
        Buyer buyer = this.buyerMapper.selectBuyerByOpenId(orderCreateVo.getToken());
        if (buyer == null) {
            return res;
        }
        // <prodId, 当前订单购买次数>
        Map<Long, Integer> prodCount = new HashMap<>(goodsJsonStr.size());
        List<Long> prodIds = new ArrayList<>();
        int tempCount = 0;
        for (GoodsJsonStrVo good : goodsJsonStr) {
            prodIds.add(good.getGoodsId());
            if (!prodCount.containsKey(good.getGoodsId())) {
                prodCount.put(good.getGoodsId(), 0);
            }
            tempCount = prodCount.get(good.getGoodsId());
            prodCount.put(good.getGoodsId(), ++tempCount);
        }
        // 每种商品在每个订单中只能出现一次
        for (Map.Entry<Long, Integer> entry : prodCount.entrySet()) {
            if (entry.getValue() > 1) {
                throw new FacException("每种商品在每个订单中只能出现一次，请核实后再购买");
            }
        }
        // 当前用户选择的商品
        QueryGoodVo vo = new QueryGoodVo();
        vo.setGoodIds(prodIds);
        vo.setPage(null);
        List<Product> products = this.productMapper.goodsList(vo);
        if (CollectionUtils.isEmpty(products)) {
            return res;
        }
        Map<Long, Product> productMap = new HashMap<>();
        // 下架的商品
        StringBuilder lowerProds = new StringBuilder();
        for (Product product : products) {
            // 下架的商品不能创建订单
            if (product.getStatus().equals(ProductStatus.LOWER_SHELF)) {
                lowerProds.append(product.getName()).append(",");
            }
            productMap.put(product.getId(), product);
        }
        if (StringUtils.isNotBlank(lowerProds.toString())) {
            lowerProds = lowerProds.deleteCharAt(lowerProds.toString().length() - 1);
            throw new FacException("以下商品已下架，不能创建订单:\n" + lowerProds.toString());
        }
        Date nowDate = new Date();
        // 存在砍价活动场景
        FacKanjia kanjia = null;
        if (orderCreateVo.getKjid() != null && !orderCreateVo.getKjid().equals(0L)) {
            final FacKanjiaExample kanjiaExample = new FacKanjiaExample();
            kanjiaExample.createCriteria().andIsDeletedEqualTo(false).andIdEqualTo(orderCreateVo.getKjid());
            List<FacKanjia> kanjias = this.kanjiaMapper.selectByExample(kanjiaExample);
            if (CollectionUtils.isEmpty(kanjias)) {
                log.info(String.format("-----------createOrder: kanjia set is not exist, token:%s, kanjiaId:%s", orderCreateVo.getToken()
                        , orderCreateVo.getKjid()));
                throw new FacException("当前砍价活动信息不存在，请联系管理员");
            }
            kanjia = kanjias.get(0);
            if (nowDate.compareTo(kanjia.getStartDate()) < 0) {
                throw new FacException("砍价活动暂未开始");
            }
            if (nowDate.compareTo(kanjia.getStopDate()) > 0) {
                throw new FacException("砍价活动已结束");
            }
        }
        // 转换用户选择的商品信息
        List<FacOrder> orders = new ArrayList<>();
        // 累积商品总价
        BigDecimal amountConsume = new BigDecimal("0");
        // 当前订单单号
        String orderNo = DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSSS");
        // 记录通过分享人创建的订单，给分享人相应分享商品设置的分享奖金
        List<String> prodIdList = new ArrayList<>();
        // 商品分享人
        Map<Long, Long> prodId2InviterId = new HashMap<>();
        for (GoodsJsonStrVo good : goodsJsonStr) {
            Product product = productMap.get(good.getGoodsId());
            if (product == null || product.getIsDeleted() == 1) {
                throw new FacException(String.format("商品【%s】已被删除，请选择其它商品购买", product.getName()));
            }
            if (kanjia == null && product.getStatus().intValue() == 2) {
                throw new FacException(String.format("商品【%s】已下架，请选择其它商品购买", product.getName()));
            }
            if (kanjia == null && nowDate.compareTo(product.getRushEnd()) > 0) {
                throw new FacException(String.format("商品【%s】抢购时间已结束，请选择其它商品购买", product.getName()));
            }
            if (kanjia != null) {
                int total = kanjia.getTotal();
                int sales = kanjia.getSales();
                int left = total - sales;
                if (left < good.getNumber()) {
                    throw new FacException(String.format("商品【%s】库存数量已不足，请选择其它商品购买", product.getName()));
                }
            } else if (product.getInventoryQuantity() == 0 || (product.getInventoryQuantity() < good.getNumber())) {
                throw new FacException(String.format("商品【%s】库存数量已不足，请选择其它商品购买", product.getName()));
            }
            if (kanjia == null && good.getNumber() > product.getLimitQuantity()) {
                throw new FacException(String.format("商品【%s】每人限购%s份", product.getName(), product.getLimitQuantity()));
            }
            // 当前订单涉及到的商品
            prodIdList.add(product.getId().toString());
            // 当前商品分享人
            if (good.getInviter_id() > 0) {
                // 每种商品只记录最后一次分享人
                prodId2InviterId.put(good.getGoodsId(), good.getInviter_id());
            }
            // 累积商品总价
            amountConsume = DecimalUtils.add(amountConsume, DecimalUtils.mul(product.getPrice()
                    , new BigDecimal(String.valueOf(good.getNumber()))));
        }
        // 同一订单商家唯一
        FacBuyerBusinessExample buyerBusinessExample = new FacBuyerBusinessExample();
        buyerBusinessExample.createCriteria().andIsDeletedEqualTo(false).andBusinessProdIdIn(prodIds);
        List<FacBuyerBusiness> buyerBusinesses = this.facBuyerBusinessMapper.selectByExample(buyerBusinessExample);
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(buyerBusinesses)) {
            // <businessId, [prodId]>
            Map<Long, List<Long>> biz2ProdIds = new HashMap<>();
            for (FacBuyerBusiness item : buyerBusinesses) {
                if (!biz2ProdIds.containsKey(item.getBusinessId())) {
                    biz2ProdIds.put(item.getBusinessId(), new ArrayList<>());
                }
                biz2ProdIds.get(item.getBusinessId()).add(item.getBusinessProdId());
            }
            if (biz2ProdIds.size() != 1) {
                throw new FacException("您选择的商品在不同卖家中，不能创建订单");
            }
            for (Map.Entry<Long, List<Long>> entry : biz2ProdIds.entrySet()) {
                List<Long> prodId2Business = entry.getValue();
                for (Long prodId : prodIds) {
                    if (!prodId2Business.contains(prodId)) {
                        // 用户选择的商品存在于不同的卖家中
                        throw new FacException("当前订单中的商品在不同卖家中，不能创建订单");
                    }
                }
            }
        } else {
            throw new FacException("当前商品暂未绑定商家，请联系管理员");
        }
        // 当前订单用户可以使用的积分
        // 使用积分规则暂定为：消费总金额大于当前用户名下的积分数
        int userScore = 0;
        if (orderCreateVo.getUserScore() == 1) {
            // 如果用户选择了使用积分，根据当前用户积分数重新计算当前用户实际可以抵用的积分数
            userScore = this.getUserScore(amountConsume, buyer.getPoints());
        }
        // 订单信息
        FacOrder facOrder = this.buildFacOrder(orderCreateVo, buyer, nowDate);
        facOrder.setOrderNo(orderNo);
        facOrder.setUserScore(Short.valueOf(String.valueOf(userScore)));
        facOrder.setProdIds(StringUtils.join(prodIdList, ","));
        this.facOrderMapper.insert(facOrder);
        // 订单商品明细信息
        this.insertFacOrderProduct(buyer, facOrder, goodsJsonStr, productMap, prodId2InviterId, nowDate);

        // 所有商品总价
        amountConsume = DecimalUtils.formatDecimal(amountConsume);
        // 创建返回结果
        res.setAmountLogistics(Double.valueOf(amountConsume.toString()));
        res.setScore(0);
        res.setGoodsNumber(prodIdList.size());
        res.setNeedLogistics(false);
        res.setAmountTotle(0);
        res.setDateAdd(TimeUtils.date2Str(nowDate, TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM_SS));
        res.setStatus(OrderStatus.PAYING.getCode());
        res.setStatusStr(OrderStatus.PAYING.getName());

        return res;
    }

    private FacOrder buildFacOrder(OrderCreateVo orderCreateVo, Buyer buyer, Date nowDate) {
        FacOrder order = new FacOrder();
        order.setStatus(OrderStatus.PAYING.getCode().byteValue());
        order.setToken(orderCreateVo.getToken());
        order.setOpenId(orderCreateVo.getToken());
        order.setUserId(buyer.getId());
        order.setUserName(buyer.getName());
        order.setNickName(buyer.getNickName());
        order.setRemark(orderCreateVo.getRemark());
        order.setRemarkMngt("");
        order.setShipId(-1L);
        order.setShip(new Byte("2"));
        order.setShipCode("");
        order.setKanjiaId(orderCreateVo.getKjid());
        order.setCreateTime(nowDate);
        order.setUpdateTime(nowDate);
        order.setOperatorId(buyer.getId());
        order.setOperatorName(buyer.getName());
        order.setIsDeleted(false);

        return order;
    }

    private void insertFacOrderProduct(Buyer buyer, FacOrder order, List<GoodsJsonStrVo> goodsJsonStr, Map<Long, Product> productMap
            , Map<Long, Long> prodId2InviterId, Date nowDate) throws FacException {
        if (CollectionUtils.isEmpty(goodsJsonStr) || MapUtils.isEmpty(productMap)) {
            return;
        }
        for (GoodsJsonStrVo good : goodsJsonStr) {
            FacOrderProduct facOrderProduct = new FacOrderProduct();
            facOrderProduct.setOrderNo(order.getOrderNo());
            // 当前商品
            Product product = productMap.get(good.getGoodsId());
            facOrderProduct.setProdId(product.getId());
            facOrderProduct.setProdName(product.getName());
            facOrderProduct.setProdNumber(Short.valueOf(String.valueOf(good.getNumber())));
            if (prodId2InviterId.containsKey(product.getId()) && prodId2InviterId.get(product.getId()) != 0) {
                facOrderProduct.setInviterId(prodId2InviterId.get(product.getId()));
            }
            if (order.getKanjiaId() != null && !order.getKanjiaId().equals(0L)) {
                FacKanjiaJoinerExample kanjiaJoinerExample = new FacKanjiaJoinerExample();
                kanjiaJoinerExample.createCriteria().andIsDeletedEqualTo(false).andKanjiaIdEqualTo(order.getKanjiaId())
                        .andTokenEqualTo(order.getToken());
                List<FacKanjiaJoiner> kanjiaJoiners = this.kanjiaJoinerMapper.selectByExample(kanjiaJoinerExample);
                if (CollectionUtils.isEmpty(kanjiaJoiners)) {
                    log.info(String.format("---------------create order->create order product, has no kanjia joiner info, token:%s, kanjiaId:%s"
                            , order.getToken(), order.getKanjiaId()));
                    throw new FacException("您当前商品砍价信息不存在，请联系管理员");
                }
                // 砍价活动商品价格为砍价活动当前的实时商品价格
                FacKanjiaJoiner kanjiaJoiner = kanjiaJoiners.get(0);
                facOrderProduct.setPrice(kanjiaJoiner.getCurrentPrice());
            } else {
                facOrderProduct.setPrice(product.getPrice());
            }
            facOrderProduct.setToken(order.getToken());
            facOrderProduct.setOpenId(order.getOpenId());
            facOrderProduct.setUserId(order.getUserId());
            facOrderProduct.setUserName(order.getUserName());
            facOrderProduct.setNickName(order.getNickName());
            facOrderProduct.setCreateTime(nowDate);
            facOrderProduct.setUpdateTime(nowDate);
            facOrderProduct.setOperatorId(buyer.getId());
            facOrderProduct.setOperatorName(buyer.getName());
            facOrderProduct.setIsDeleted(false);

            this.facOrderProductMapper.insert(facOrderProduct);
        }
    }

    @Override
    public OrderStatisticsVo orderStatistics(String token) {
        OrderStatisticsVo vo = new OrderStatisticsVo();
        // 当前用户下的所有订单
        QueryVo queryVo = new QueryVo();
        queryVo.setToken(token);
        List<FacOrder> orders = this.orderMapper.orderList(queryVo);
        if (CollectionUtils.isEmpty(orders)) {
            return vo;
        }
        int paying = 0, toWriteoff = 0, toEvaluate = 0, complete = 0, cancel = 0;
        for (FacOrder item : orders) {
            if (item.getStatus() == null) {
                log.error("order status is null, orderId = " + item.getId());
                continue;
            }
            if (OrderStatus.PAYING.getCode().intValue() == item.getStatus().intValue()) {
                paying++;
            } else if (OrderStatus.TOWRITEOFF.getCode().intValue() == item.getStatus().intValue()) {
                toWriteoff++;
            } else if (OrderStatus.TOEVALUATE.getCode().intValue() == item.getStatus().intValue()) {
                toEvaluate++;
            } else if (OrderStatus.COMPLETED.getCode().intValue() == item.getStatus().intValue()) {
                complete++;
            } else if (OrderStatus.CACELED.getCode().intValue() == item.getStatus().intValue()) {
                cancel++;
            }
        }
        vo.setPaying(paying);
        vo.setToWriteoff(toWriteoff);
        vo.setToEvaluate(toEvaluate);
        vo.setComplete(complete);
        vo.setCancel(cancel);
        // 查询当前用户是不是商家，有没有自己的商品
        BuyerBusiness buyerBusiness = new BuyerBusiness();
        buyerBusiness.setToken(token);
        buyerBusiness.setIsDeleted(0);
        List<BuyerBusiness> buyerBusinesses = this.buyerBusinessMapper.selectBuyerBusinessList(buyerBusiness);
        // 用户类型:0-普通购买用户,1-商家：先屏蔽掉待核销的tab
//        vo.setUserType(CollectionUtils.isEmpty(buyerBusinesses) ? 0 : 1);
        vo.setUserType(0);
        if (CollectionUtils.isNotEmpty(buyerBusinesses)) {
            List<Long> prodIds = new ArrayList<>();
            for (BuyerBusiness item : buyerBusinesses) {
                prodIds.add(item.getBusinessProdId());
            }
            // 查询当前用户名下的商品是否存在处于待核销(买家已付款)的订单
            List<Integer> status = new ArrayList<>();
            status.add(OrderStatus.TOWRITEOFF.getCode());
            List<FacOrder> orderList = this.orderMapper.selectProductsByProdAndStatus(prodIds, status);
            // 待核销：商家要核销的自己的商品
            vo.setWriteoffing(CollectionUtils.isNotEmpty(orderList) ? orderList.size() : 0);
        }

        return vo;
    }

    /**
     * 客户端商品查询接口
     *
     * @param token
     * @param status
     * @return
     */
    @Override
    public OrderListVo orderList(String token, int status) {
        OrderListVo vo = new OrderListVo();
        if (StringUtils.isEmpty(token)) {
            return vo;
        }
        List<FacOrder> orders = null;
        if (OrderStatus.TOWRITEOFF_BIZ.getCode().intValue() != status) {
            // 正常买者用户对应的订单
            QueryVo queryVo = new QueryVo();
            queryVo.setToken(token);
            queryVo.setStatus(status);
            // 当前条件下所有商品
            orders = this.orderMapper.orderList(queryVo);
        } else if (OrderStatus.TOWRITEOFF_BIZ.getCode().intValue() == status) {
            // 当前用户是商家，其对应的待核销的商品订单
            // 查询当前用户是不是商家，有没有自己的商品
            BuyerBusiness buyerBusiness = new BuyerBusiness();
            buyerBusiness.setToken(token);
            buyerBusiness.setIsDeleted(0);
            List<BuyerBusiness> buyerBusinesses = this.buyerBusinessMapper.selectBuyerBusinessList(buyerBusiness);
            if (CollectionUtils.isNotEmpty(buyerBusinesses)) {
                List<Long> prodIds = new ArrayList<>();
                for (BuyerBusiness item : buyerBusinesses) {
                    prodIds.add(item.getBusinessProdId());
                }
                // 查询当前用户名下的商品是否存在处于待核销(买家已付款)的订单
                List<Integer> statuses = new ArrayList<>();
                statuses.add(OrderStatus.TOWRITEOFF.getCode());
                // 待核销：商家要核销的自己的商品
                orders = this.orderMapper.selectProductsByProdAndStatus(prodIds, statuses);
            }
        }

        if (CollectionUtils.isEmpty(orders)) {
            return vo;
        }
        // 商品信息
        this.convertOrders(vo, orders, status);

        return vo;
    }

    /**
     * 取消订单
     *
     * @param token
     * @param orderNo
     */
    @Override
    public void closeOrder(String token, String orderNo) {
        // 更改订单状态为取消状态
        QueryVo queryVo = new QueryVo();
        queryVo.setToken(token);
        queryVo.setOpenId(token);
        queryVo.setStatus(OrderStatus.CACELED.getCode());
        queryVo.setOrderNo(orderNo);
        this.orderMapper.updateOrderStatus(queryVo);
        // 删除订单对应的核销记录
        List<String> orderNos = new ArrayList<>();
        orderNos.add(orderNo);
        this.facProductWriteoffMapper.deleteFacProductWriteoffByOrderNos(orderNos);
    }

    /**
     * 指定订单详情
     *
     * @param id    订单id
     * @param token 用户token
     * @return OrderDetailVo
     */
    @Override
    public OrderDetailVo orderDetail(long id, String token) {
        OrderDetailVo orderDetailVo = new OrderDetailVo();
        FacOrder order = this.orderMapper.selectOrderByIdAndToken(id, token);
        if (order == null) {
            return null;
        }
        // 1.当前订单信息
        OrderInfoVo orderInfo = new OrderInfoVo();
        orderDetailVo.setOrderInfo(orderInfo);
        Order orderQ = new Order();
        orderQ.setOrderNo(order.getOrderNo());
        List<FacOrderProduct> facOrderProducts = this.orderMapper.selectFacOrderProductList(orderQ);
        // 商品金额
        BigDecimal amount = DecimalUtils.getDefaultDecimal();
        if (CollectionUtils.isNotEmpty(facOrderProducts)) {
            for (FacOrderProduct item : facOrderProducts) {
                amount = DecimalUtils.add(amount, DecimalUtils.mul(item.getPrice(), new BigDecimal(String.valueOf(item.getProdNumber()))));
            }
            orderInfo.setAmount(Double.valueOf(amount.toString()));
        }
        // 应付总额
        BigDecimal orderTotalPrice = amount;
        if (order.getUserScore() > 0) {
            orderTotalPrice = DecimalUtils.subtract(orderTotalPrice, DecimalUtils.convert(order.getUserScore()));
        }
        orderInfo.setAmountReal(Double.valueOf(orderTotalPrice.toString()));
        // 订单状态
        orderInfo.setStatus(order.getStatus().intValue());
        orderInfo.setStatusStr(OrderStatus.getNameByCode(Integer.valueOf(order.getStatus())));

        // 2.订单商品信息:暂时一个订单就涉及一个商品
        List<GoodSpecification> goods = new ArrayList<>();
        orderDetailVo.setGoods(goods);
        GoodSpecification good = new GoodSpecification();
        goods.add(good);
//        good.setGoodsName(order.getProdName());
//        good.setNumber(order.getProdNumber());
//        good.setAmount(Double.valueOf(order.getPrice().toString()));
//        // 商品图片
//        Product product = this.productMapper.selectProductById(order.getProdId());
//        if (product != null && StringUtils.isNotBlank(product.getPicture())) {
//            good.setPic(product.getPicture().split(",")[0]);
//        }

        return orderDetailVo;
    }

    /**
     * 核销商品订单
     *
     * @param token
     * @param orderNo
     */
    @Override
    public void writeOffOrder(String token, String orderNo) throws Exception {
        final FacOrderExample orderExample = new FacOrderExample();
        orderExample.createCriteria().andIsDeletedEqualTo(false).andOrderNoEqualTo(orderNo);
        final List<FacOrder> facOrders = this.facOrderMapper.selectByExample(orderExample);
        if (CollectionUtils.isEmpty(facOrders)) {
            throw new Exception("当前订单已不存在,请联系管理员");
        }
        FacOrder order = facOrders.get(0);
        if (!OrderStatus.TOWRITEOFF.getCode().equals(Integer.valueOf(order.getStatus()))) {
            throw new Exception("当前商品订单处于非待核销状态，请联系管理员");
        }
        FacBuyerExample buyerExample = new FacBuyerExample();
        buyerExample.createCriteria().andIsDeletedEqualTo(false).andTokenEqualTo(token);
        List<FacBuyer> buyers = this.facBuyerMapper.selectByExample(buyerExample);
        if (CollectionUtils.isEmpty(buyers)) {
            throw new Exception("您的卖家信息不存在，请联系管理员");
        }
        // 当前商家信息
        FacBuyer business = buyers.get(0);
        // 当前商家名下的商品
        FacBuyerBusinessExample buyerBusinessExample = new FacBuyerBusinessExample();
        buyerBusinessExample.createCriteria().andIsDeletedEqualTo(false).andUserIdEqualTo(business.getId());
        List<FacBuyerBusiness> buyerBusinesses = this.facBuyerBusinessMapper.selectByExample(buyerBusinessExample);
        if (CollectionUtils.isEmpty(buyerBusinesses)) {
            throw new Exception("您当前名下没有绑定商品信息，请联系管理员");
        }
        // 当前商家绑定的商品
        List<Long> prodIds = new ArrayList<>();
        for (FacBuyerBusiness item : buyerBusinesses) {
            prodIds.add(item.getBusinessProdId());
        }
        // 当前订单对应的商品明细
        final FacOrderProductExample orderProductExample = new FacOrderProductExample();
        orderProductExample.createCriteria().andIsDeletedEqualTo(false).andOrderNoEqualTo(orderNo);
        List<FacOrderProduct> orderProducts = this.facOrderProductMapper.selectByExample(orderProductExample);
        if (CollectionUtils.isEmpty(orderProducts)) {
            throw new Exception("当前订单商品不存在，请联系管理员");
        }

        Business prodBusiness = null;
        for (FacOrderProduct orderProduct : orderProducts) {
            Product product = this.productCache.getProductCache(orderProduct.getProdId().toString());
            if (product == null) {
                throw new Exception(String.format("商品【%s】已不存在,请联系管理员", product.getName()));
            }
            prodBusiness = this.businessMapper.selectBusinessById(product.getBusinessId());
            if (prodBusiness == null) {
                throw new Exception("您的商家信息已不存在,请联系管理员");
            }
            if (!prodIds.contains(product.getId())) {
                throw new Exception(String.format("商品【%s】不是您的商品，不能核销,请核对", product.getName()));
            }
        }

        Date nowDate = new Date();
        // 更新当前订单状态
        FacOrder facOrder = new FacOrder();
        // 商品订单核销后状态更新为：已完成(以后添加评价功能后，这里状态需要更新为:待评价)
        facOrder.setStatus(OrderStatus.COMPLETED.getCode().byteValue());
        // 核销时间
        facOrder.setWriteoffTime(nowDate);
        facOrder.setUpdateTime(nowDate);
        facOrder.setOperatorId(Long.valueOf(business.getId()));
        facOrder.setOperatorName(business.getName());
        this.facOrderMapper.updateByExampleSelective(facOrder, orderExample);

        // 更新核销时间、操作人
        FacProductWriteoff facProductWriteoff = new FacProductWriteoff();
        facProductWriteoff.setOrderNo(orderNo);
        List<FacProductWriteoff> records = this.facProductWriteoffMapper.selectFacProductWriteoffList(facProductWriteoff);
        if (CollectionUtils.isNotEmpty(records)) {
            facProductWriteoff = records.get(0);
            if (facProductWriteoff.getStatus().intValue() == 1) {
                throw new Exception(String.format("当前订单已被【%s】于%s 核销过，请核实后再操作"
                        , facProductWriteoff.getOperatorName(), TimeUtils.date2Str(facProductWriteoff.getUpdateTime()
                                , TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM_SS)));
            }
            // 更新核销状态
            FacProductWriteOffBean bean = new FacProductWriteOffBean();
            bean.setWriteoffTime(nowDate);
            // 核销状态:1-已核销,2-待核销
            bean.setStatus(new Byte("1"));
            bean.setUpdateTime(nowDate);
            bean.setOperatorId(Long.valueOf(business.getId()));
            bean.setOperatorName(business.getName());
            FacProductWriteOffBeanExample beanExample = new FacProductWriteOffBeanExample();
            beanExample.createCriteria().andIsDeletedEqualTo(false).andOrderNoEqualTo(orderNo);
            this.facProductWriteOffBeanMapper.updateByExampleSelective(bean, beanExample);
        }
    }

    /**
     * 变更订单状态
     *
     * @param orderNo
     * @param remarkMngt
     * @param status
     * @throws Exception
     */
    @Override
    public int changeStatus(String orderNo, String remarkMngt, Integer status, SysUser sysUser) throws Exception {
        if (StringUtils.isBlank(orderNo)) {
            throw new Exception("订单号不能为空");
        }
        if (status == null) {
            throw new Exception("订单目标状态不能为空");
        }
        FacOrder facOrder = new FacOrder();
        facOrder.setStatus(new Byte(status.toString()));
        facOrder.setRemarkMngt(remarkMngt);
        facOrder.setUpdateTime(new Date());
        if (sysUser != null) {
            facOrder.setOperatorId(sysUser.getUserId());
            facOrder.setOperatorName(sysUser.getUserName());
        }
        FacOrderExample orderExample = new FacOrderExample();
        orderExample.createCriteria().andIsDeletedEqualTo(false).andOrderNoEqualTo(orderNo);
        return this.facOrderMapper.updateByExampleSelective(facOrder, orderExample);
    }

    private void convertOrders(OrderListVo vo, List<FacOrder> orders, int status) {
        List<OrderVo> orderVos = new ArrayList<>();
        final List<String> orderNos = new ArrayList<>();
        final Map<String, FacOrder> orderNo2Order = new HashMap<>();
        for (FacOrder order : orders) {
            OrderVo orderVo = new OrderVo();
            orderVos.add(orderVo);
            orderVo.setDateAdd(TimeUtils.date2Str(order.getCreateTime(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM_SS));
            orderVo.setGoodsNumber(1);
            orderVo.setId(order.getId());
            orderVo.setPay(order.getPayTime() != null);
            orderVo.setOrderNumber(order.getOrderNo());
            orderVo.setRemark(order.getRemark());
            orderVo.setShopId(order.getShipId());
            orderVo.setStatus(order.getStatus());
            if (OrderStatus.TOWRITEOFF_BIZ.getCode().intValue() == status) {
                // 商家待核销的订单
                orderVo.setStatus(status);
            }
            orderVo.setStatusStr(OrderStatus.getNameByCode(Integer.valueOf(order.getStatus())));
            orderVo.setUserId(order.getUserId());

            // 用户使用的积分
            orderVo.setScore(order.getUserScore());
            orderNos.add(order.getOrderNo());
            orderNo2Order.put(order.getOrderNo(), order);
        }
        List<FacProductWriteoff> productWriteoffs = this.facProductWriteoffMapper.selectFacProductWriteoffListByOrderNos(orderNos, null);
        if (CollectionUtils.isNotEmpty(productWriteoffs)) {
            // 设置核销码
            Map<String, String> order2WriteCode = new HashMap<>(16);
            for (FacProductWriteoff item : productWriteoffs) {
                order2WriteCode.put(item.getOrderNo(), item.getCode());
            }
            for (OrderVo item : orderVos) {
                item.setWriteOffCode(order2WriteCode.get(item.getOrderNumber()));
            }
        }
        // 订单信息
        vo.setOrderList(orderVos);
        // 收集当前所有订单各自包含的商品信息
        this.setOrderProducts(vo, orderNos);
    }

    private void setOrderProducts(OrderListVo vo, List<String> orderNos) {
        if (CollectionUtils.isEmpty(orderNos) || CollectionUtils.isEmpty(vo.getOrderList())) {
            return;
        }
        FacOrderProductExample orderProductExample = new FacOrderProductExample();
        orderProductExample.createCriteria().andIsDeletedEqualTo(false).andOrderNoIn(orderNos);
        List<FacOrderProduct> orderProducts = this.facOrderProductMapper.selectByExample(orderProductExample);
        if (CollectionUtils.isEmpty(orderProducts)) {
            return;
        }
        // <orderNo, [FacOrderProduct]>
        final Map<String, List<FacOrderProduct>> orderNo2Prods = new HashMap<>();
        final Set<Long> prodIds = new HashSet<>();
        for (FacOrderProduct item : orderProducts) {
            if (!orderNo2Prods.containsKey(item.getOrderNo())) {
                orderNo2Prods.put(item.getOrderNo(), new ArrayList<>());
            }
            orderNo2Prods.get(item.getOrderNo()).add(item);
            prodIds.add(item.getProdId());
        }
        // 涉及的所有商品信息
        FacProductExample productExample = new FacProductExample();
        productExample.createCriteria().andIsDeletedEqualTo(false).andIdIn(new ArrayList<>(prodIds));
        List<FacProduct> products = this.facProductMapper.selectByExample(productExample);
        if (CollectionUtils.isEmpty(products)) {
            return;
        }
        final Map<Long, FacProduct> id2Prod = new HashMap<>();
        for (FacProduct product : products) {
            id2Prod.put(product.getId(), product);
        }
        for (OrderVo orderVo : vo.getOrderList()) {
            if (!orderNo2Prods.containsKey(orderVo.getOrderNumber()) || CollectionUtils.isEmpty(orderNo2Prods.get(orderVo.getOrderNumber()))) {
                continue;
            }
            List<FacOrderProduct> facOrderProducts = orderNo2Prods.get(orderVo.getOrderNumber());
            BigDecimal orderTotalPrice = DecimalUtils.getDefaultDecimal();
            for (FacOrderProduct db : facOrderProducts) {
                OrderProductVo orderProductVo = new OrderProductVo();
                orderProductVo.setOrderNo(db.getOrderNo());
                orderProductVo.setProdName(db.getProdName());
                orderProductVo.setProdId(db.getProdId().toString());
                orderProductVo.setNumber(db.getProdNumber().toString());
                orderProductVo.setPrice(Double.valueOf(String.valueOf(db.getPrice())).toString());
                // 商品图片，这里只给前端每个商品的第一张图片
                if (id2Prod.containsKey(db.getProdId())) {
                    String pictures = id2Prod.get(db.getProdId()).getPicture();
                    if (StringUtils.isNotBlank(pictures)) {
                        orderProductVo.setPic(pictures.split(",")[0]);
                    }
                }
                orderVo.getOrderProducts().add(orderProductVo);
                // 当前订单的实际总价
                orderTotalPrice = DecimalUtils.add(orderTotalPrice, DecimalUtils.mul(db.getPrice(), new BigDecimal(String.valueOf(db.getProdNumber()))));
            }
            // 再看看有没有用到积分，如果用到需要再扣掉积分对应的钱
            int useScore = orderVo.getScore();
            if (useScore != 0) {
                BigDecimal scoreMount = DecimalUtils.division(useScore, 100);
                orderTotalPrice = DecimalUtils.subtract(orderTotalPrice, scoreMount);
            }
            double amountReal = Double.valueOf(orderTotalPrice.toString());
            orderVo.setAmountReal(amountReal);
        }
    }

    private int getUserScore(BigDecimal amountConsume, int points) {
        // 只有商品总消费金额大于积分总数时才可以使用积分:一个积分1分钱
        if (points == 0) {
            return 0;
        }
        BigDecimal scoreBD = DecimalUtils.division(points, 100);
        if (amountConsume.compareTo(scoreBD) > 0) {
            return points;
        }

        return 0;
    }

    private void recordInviterCash(Map<Long, Long> prodId2InviterId, Buyer buyer) {
        // 记录分享奖金
        if (MapUtils.isEmpty(prodId2InviterId)) {
            return;
        }
        Date nowDate = new Date();
        for (Map.Entry<Long, Long> entry : prodId2InviterId.entrySet()) {
            long prodId = entry.getKey();
            final FacProductExample example = new FacProductExample();
            example.createCriteria().andIsDeletedEqualTo(false).andIdEqualTo(prodId);
            final List<FacProduct> products = this.facProductMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(products)) {
                FacProduct product = products.get(0);
                FacCash facCash = new FacCash();
                facCash.setUserId(entry.getValue());
                facCash.setCash(product.getDistribution());
                facCash.setStatus(CashStatus.TODEAL.getValue());
                facCash.setApplyTime(nowDate);
                facCash.setCreateTime(nowDate);
                facCash.setOperatorId(buyer.getId());
                facCash.setOperatorName(buyer.getNickName());
                facCash.setIsDeleted(false);
                this.facCashMapper.insert(facCash);
            }
        }
    }

    public static void main(String[] args) {
        String orderNo = DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSSS");
        System.out.println("orderNo = " + orderNo);
        BigDecimal scoreMount = new BigDecimal(String.valueOf(2 / 100));
        System.out.println(scoreMount);
    }
}
