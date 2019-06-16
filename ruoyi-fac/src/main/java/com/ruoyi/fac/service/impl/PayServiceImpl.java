package com.ruoyi.fac.service.impl;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.config.Global;
import com.ruoyi.fac.constant.FacConstant;
import com.ruoyi.fac.domain.Buyer;
import com.ruoyi.fac.domain.FacProductWriteoff;
import com.ruoyi.fac.domain.Order;
import com.ruoyi.fac.domain.Product;
import com.ruoyi.fac.enums.OrderStatus;
import com.ruoyi.fac.enums.ScoreTypeEnum;
import com.ruoyi.fac.exception.FacException;
import com.ruoyi.fac.mapper.*;
import com.ruoyi.fac.model.FacBuyer;
import com.ruoyi.fac.model.FacBuyerExample;
import com.ruoyi.fac.model.FacBuyerSign;
import com.ruoyi.fac.service.IPayService;
import com.ruoyi.fac.util.DecimalUtils;
import com.ruoyi.fac.util.FacCommonUtils;
import com.ruoyi.fac.util.TimeUtils;
import com.ruoyi.fac.vo.AppMchVo;
import com.ruoyi.fac.vo.wxpay.WxPrePayReq;
import com.ruoyi.fac.vo.wxpay.WxPrePayRes;
import com.ruoyi.fac.wxpay.PayCommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by zgf
 * https://www.cnblogs.com/yi1036943655/p/7211275.html  或者 https://www.cnblogs.com/yimiyan/p/5603657.html
 * 微信官方给的代码例子：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=11_1
 * Date 2019/2/18 17:16
 * 注意事项：
 * 　　1)、所有的签名和发送微信服务器的数据必须一致 包括Key的大小写 否则签名失败
 * 　　2)、微信小程序 前端调用 接口的时候 文档上并没有写appId参数 该参数一定要穿 并且是大写
 * 　　3)、交易类型 为 JSAPI 的时候 则必须传入openid
 * 　　4)、body格式问题 写的是UTF-8 实际要的格式则是ISO8859-1 而且单独对body进行设置好像不好使 所以必须全部都改成该格式
 * 　　5)、生成签名 最后加上key的那块 加的格式是 &key = KEY 这种 而且不是直接 + key 这个地方需要注意一下 我碰了个坑 文档没看仔细
 * 　　6)、数据包ID 格式 不是 value直接设置成 数据包ID就可以 前面需要加 "prepay_id="
 * 　　7)、最后一点强调 生成签名的数据和发送服务器的数据 必须保持一致
 * <p>
 * https://www.cnblogs.com/yimiyan/p/5603657.html
 */
@Slf4j
@Service("payService")
public class PayServiceImpl implements IPayService {
    private static final Logger logger = LoggerFactory.getLogger(PayServiceImpl.class);

    private static final String prepayUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private BuyerMapper buyerMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private FacProductWriteoffMapper facProductWriteoffMapper;
    @Autowired
    private FacBuyerMapper facBuyerMapper;
    @Autowired
    private FacBuyerSignMapper facBuyerSignMapper;

    /**
     * 微信支付预支付接口
     *
     * @param req
     * @return
     * @throws Exception
     */
    @Override
    public WxPrePayRes getWxPrePayInfo(WxPrePayReq req, HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(String.format("====================[getWxPrePayInfo] req:%s", JSON.toJSONString(req)));
        //设置最终返回对象
        final WxPrePayRes res = new WxPrePayRes();
        String openid = req.getToken();
        // id为订单号
        final List<Order> orders = this.orderMapper.selectOrderByOrderNo(req.getNextAction().getId());
        if (CollectionUtils.isEmpty(orders)) {
            throw new Exception("当前订单已不存在，请确认");
        }
        if (!OrderStatus.PAYING.getCode().equals(orders.get(0).getStatus())) {
            throw new Exception("当前订单处于非待付款状态，请核对后再操作");
        }
        // 当前订单总消费金额(后台计算出来的)
        BigDecimal totalConsumeAmout = this.getTotalConsumAmout(orders);
        if (totalConsumeAmout.compareTo(new BigDecimal(req.getMoney())) != 0) {
            log.info(String.format("[getWxPrePayInfo] consumeAmout has error, money/paramter:%s, backStatic:%s", req.getMoney()
                    , totalConsumeAmout.toString()));
            throw new Exception("当前订单商品总额与实际消费不一致，请核实");
        }
        int userScore = orders.get(0).getUserScore();
        if (userScore > 0) {
            // 如果用户使用了积分，预支付金额需要扣除相应积分数
            float userScore2Yuan = (float) userScore / 100;
            totalConsumeAmout = DecimalUtils.subtract(totalConsumeAmout, new BigDecimal(userScore2Yuan));
        }
        totalConsumeAmout = DecimalUtils.formatDecimal(totalConsumeAmout);

        //接口调用总金额单位为分,需要换算一下(测试金额改成1,单位为分则是0.01,根据自己业务场景判断是转换成float类型还是int类型)
        //String amountFen = Integer.valueOf((Integer.parseInt(totalConsumeAmout.toString())*100)).toString();
//        String amountFen = Float.valueOf((Float.parseFloat(totalConsumeAmout.toString()) * 100)).toString();
        String amountFen = "1";
        // 创建hashmap(用户获得签名)
        SortedMap<String, String> paraMap = new TreeMap<String, String>();
        // 校验当前商品是否还可以购买:库存数据
        Long[] ids = new Long[orders.size()];
        // <prodId, Order>
        Map<Long, Order> prod2Order = new HashMap<>(orders.size());
        for (int i = 0, size = orders.size(); i < size; i++) {
            ids[i] = orders.get(i).getProdId();
            prod2Order.put(orders.get(i).getProdId(), orders.get(i));
        }
        List<Product> products = this.productMapper.selectProductsByIds(ids);
        for (Product item : products) {
            if (item == null || item.getIsDeleted() == 1) {
                throw new Exception(String.format("商品【%s】已被删除，请选择其它商品购买", item.getName()));
            }
            if (prod2Order.containsKey(item.getId()) && item.getInventoryQuantity() < prod2Order.get(item.getId()).getProdNumber()) {
                throw new Exception(String.format("商品【%s】库存数量已不足，请选择其它商品购买", item.getName()));
            }
            if (item.getStatus().intValue() == 2) {
                throw new Exception(String.format("商品【%s】已下架，请选择其它商品购买", item.getName()));
            }
            if (prod2Order.get(item.getId()).getProdNumber() > item.getLimitQuantity()) {
                throw new FacException(String.format("商品【%s】每人限购%s份", item.getName(), item.getLimitQuantity()));
            }
            Date nowDate = new Date();
            if (nowDate.compareTo(item.getRushEnd()) > 0) {
                throw new Exception(String.format("商品【%s】抢购时间已结束，请选择其它商品购买", item.getName()));
            }
        }
        // 设置商户订单号
        String outTradeNo = orders.get(0).getOrderNo();
        try {
            // 测试金额:1分钱
            BigDecimal totalAmount = new BigDecimal("0.01");
            AppMchVo appMchVo = new AppMchVo();
            appMchVo.setAppId(Global.getFacAppId());
            appMchVo.setMchId(Global.getFacMchId());
            appMchVo.setApiKey(Global.getFacMchSecret());
            String body = "JB FAC";
            String notifyUrl = Global.getDomain() + "/fac/client/pay/wx/payCallback";
            SortedMap<String, Object> preResult = PayCommonUtil.WxPublicPay(outTradeNo, totalAmount, body, "prepayfac", openid, notifyUrl, appMchVo, request);
            if (MapUtils.isNotEmpty(preResult)) {
                logger.info(String.format("[getWxPrePayInfo] preResult:%s", JSON.toJSONString(preResult)));
                res.setTimeStamp(String.valueOf(preResult.get("timeStamp")));
                res.setNonceStr(String.valueOf(preResult.get("nonceStr")));
                res.setPrepayId(String.valueOf(preResult.get("package")));
                res.setSign(String.valueOf(preResult.get("paySign")));
                logger.info(String.format("=========================[getWxPrePayInfo] success, result:%s============================", JSON.toJSONString(res)));
                return res;
            } else {
                throw new Exception("预支付接口响应数据为空");
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new Exception(ex.getMessage(), ex);
        }
    }

    /**
     * 微信支付结果通知接口
     *
     * @param request
     * @param response
     */
    @Override
    public String payCallback(HttpServletRequest request, HttpServletResponse response) {
        logger.info("微信回调接口 操作逻辑 start");
        try {
            InputStream inStream = request.getInputStream();
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            String resultxml = new String(outSteam.toByteArray(), "utf-8");
            logger.info(String.format("[payCallback] resultxml:%s", resultxml));
            Map<String, String> params = null;
            try {
                params = PayCommonUtil.doXMLParse(resultxml);
            } catch (JDOMException e) {
                e.printStackTrace();
            }
            outSteam.close();
            inStream.close();
            if (!PayCommonUtil.isTenpaySign(params)) {
                logger.info("===========payCallback====付款失败==============");
                // 支付失败
                return "fail";
            } else {
                logger.info("==========payCallback=====付款成功==============");
                // ------------------------------
                // 处理业务开始
                logger.info("=================微信回调返回是否支付成功：是, " + JSON.toJSONString(params));
                //获得 返回的商户订单号
                String outTradeNo = params.get("out_trade_no");
                logger.info("===========微信回调返回商户订单号：" + outTradeNo);
                //访问DB
                List<Order> orders = this.orderMapper.selectOrderByOrderNo(outTradeNo);
                if (CollectionUtils.isNotEmpty(orders)) {
                    logger.info("===========微信回调 根据订单号查询订单状态：" + orders.get(0).getStatus());
                    if (OrderStatus.PAYING.getCode().equals(orders.get(0).getStatus())) {
                        //修改支付状态:订单支付成功后状态变为待核销状态
                        int sqlRow = this.orderMapper.updateOrderStatusAfterPayed(outTradeNo, OrderStatus.TOWRITEOFF.getCode().intValue());
                        if (sqlRow >= 1) {
                            // 支付成功后，处理当前订单、商品、用户相关信息
                            this.dealOrderAndProdDataAfterPayed(orders);
                            logger.info("微信回调  订单号：" + outTradeNo + ",修改状态成功");
                        }
                    }
                }
                logger.info("==============微信回调接口 操作逻辑 success, at:" + TimeUtils.getCurrentTimeSSS());
                // 处理业务完毕

                return "success";
            }
        } catch (Exception ex) {
            logger.info("=========payCallback======付款异常==============");
            ex.printStackTrace();
        }

        return "fail";
    }

    @Transactional(rollbackFor = Exception.class)
    public void dealOrderAndProdDataAfterPayed(List<Order> orders) {
        logger.info(String.format("[dealOrderAndProdDataAfterPayed] orders:%s"
                , CollectionUtils.isNotEmpty(orders) ? JSON.toJSONString(orders) : "has no order."));
        // 支付成功后，处理当前订单、商品、用户相关信息
        if (CollectionUtils.isNotEmpty(orders)) {
            return;
        }
        // 批量更新当前商品对应的销售数量
        // <inviterId, [prodId]>
        Map<Long, List<Long>> inviterId2ProdId = new HashMap<>();
        List<Product> products = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        for (Order order : orders) {
            Product product = new Product();
            product.setId(order.getProdId());
            product.setSales(order.getProdNumber());
            products.add(product);
            // 商品是通过别人分享购买的
            if (order.getInviterId() != null) {
                if (!inviterId2ProdId.containsKey(order.getInviterId())) {
                    inviterId2ProdId.put(order.getInviterId(), new ArrayList<>());
                }
                inviterId2ProdId.get(order.getInviterId()).add(order.getProdId());
                ids.add(order.getProdId());
            }
        }
        this.productMapper.batchUpdateProductSales(products);
        logger.info("---------------------[dealOrderAndProdDataAfterPayed] batchUpdateProductSales success.----------------");
        // 给分享人加积分/佣金啥的
        if (MapUtils.isNotEmpty(inviterId2ProdId)) {
            Long[] idsArr = new Long[ids.size()];
            idsArr = ids.toArray(idsArr);
            List<Product> productList = this.productMapper.selectProductsByIds(idsArr);
            if (CollectionUtils.isNotEmpty(productList)) {
                Map<Long, Product> productMap = new HashMap<>(productList.size());
                for (Product item : productList) {
                    productMap.put(item.getId(), item);
                }
                // <inviterId, [prodId]>
                List<Buyer> updateObjs = new ArrayList<>();
                for (Map.Entry<Long, List<Long>> entry : inviterId2ProdId.entrySet()) {
                    Buyer buyer = new Buyer();
                    // 积分
                    int points = 0;
                    BigDecimal balance = new BigDecimal("0.0");
                    for (Long prodId : entry.getValue()) {
                        if (productMap.containsKey(prodId)) {
                            points = points + productMap.get(prodId).getBonusPoints();
                            balance = DecimalUtils.add(balance, productMap.get(prodId).getDistribution());
                        }
                    }
                    buyer.setId(entry.getKey());
                    buyer.setPoints(points);
                    buyer.setBalance(balance);
                    updateObjs.add(buyer);
                }
                this.buyerMapper.batchUpdatePoints(updateObjs);
                logger.info(String.format("---------------------[dealOrderAndProdDataAfterPayed] batchUpdatePoints success.---------%s"
                        , JSON.toJSONString(updateObjs)));
            }
        }

        // 用户付款成功后订单涉及到的每个商品再创建一条对应的待核销记录数据
        Date nowDate = new Date();
        FacProductWriteoff productWriteoff;
        List<FacProductWriteoff> productWriteoffs = new ArrayList<>();
        String writeOffCode;
        for (Order item : orders) {
            // 核销码动态随机生成:8位整数随机码
            writeOffCode = FacCommonUtils.randomInt(FacConstant.PRODUCT_WRITEOFF_CODE_LENGTH);
            productWriteoff = new FacProductWriteoff();
            productWriteoff.setOrderNo(item.getOrderNo());
            productWriteoff.setProductId(item.getProdId());
            productWriteoff.setBuyerId(item.getUserId());
            productWriteoff.setCode(writeOffCode);
            // 核销状态:1-已核销,2-待核销
            productWriteoff.setStatus(2);
            productWriteoff.setWriteoffTime(null);
            productWriteoff.setCreateTime(nowDate);
            productWriteoff.setUpdateTime(nowDate);
            productWriteoff.setOperatorId(item.getUserId());
            productWriteoff.setOperatorName(item.getUserName());
            productWriteoff.setIsDeleted(0);
            productWriteoffs.add(productWriteoff);
        }
        this.facProductWriteoffMapper.batchInsert(productWriteoffs);
        logger.info(String.format("---------------------[dealOrderAndProdDataAfterPayed] batchInsert success.---------%s"
                , JSON.toJSONString(productWriteoffs)));

        // 如果当前订单使用了积分抵扣，需要扣除当前用户相应的消费积分数,并且记录消费记录
        int useScore = orders.get(0).getUserScore();
        if (useScore > 0) {
            Buyer buyer = this.buyerMapper.selectBuyerByOpenId(orders.get(0).getToken());
            if (buyer != null) {
                FacBuyer facBuyer = new FacBuyer();
                int remainderPoints = buyer.getPoints() - useScore;
                facBuyer.setPoints(Short.valueOf(String.valueOf(remainderPoints)));
                facBuyer.setUpdateTime(nowDate);
                FacBuyerExample buyerExample = new FacBuyerExample();
                buyerExample.createCriteria().andIsDeletedEqualTo(false).andTokenEqualTo(orders.get(0).getToken());
                this.facBuyerMapper.updateByExampleSelective(facBuyer, buyerExample);
                logger.info(String.format("==========update buyer point after consume:buyer:%s,data:%s==========", JSON.toJSON(buyer), JSON.toJSON(facBuyer)));
                // 增加消费记录
                FacBuyerSign record = new FacBuyerSign();
                record.setToken(facBuyer.getToken());
                record.setNickName(buyer.getNickName());
                // 积分类型：0-签到;1-购物反赠积分,2-购物消费
                record.setType(ScoreTypeEnum.COUNSUMER.getValue());
                record.setPoint(Short.valueOf(String.valueOf(useScore)));
                record.setSignTime(nowDate);
                record.setCreateTime(nowDate);
                record.setUpdateTime(nowDate);
                record.setIsDeleted(false);
                this.facBuyerSignMapper.insertSelective(record);
                logger.info(String.format("============add consume record:%s===============", JSON.toJSONString(record)));
            } else {
                logger.info(String.format("==================current user is not exist, token:%s================", orders.get(0).getToken()));
            }
        }
    }

    private BigDecimal getTotalConsumAmout(List<Order> orders) {
        BigDecimal total = DecimalUtils.getDefaultDecimal();
        if (CollectionUtils.isEmpty(orders)) {
            return total;
        }

        for (Order order : orders) {
            total = DecimalUtils.add(total, DecimalUtils.mul(order.getPrice(), new BigDecimal(String.valueOf(order.getProdNumber()))));
        }
        return total;
    }

    public static void main(String[] args) {
        BigDecimal total = new BigDecimal(38);
        float ddd = (float) 4 / 100;
        BigDecimal data = new BigDecimal(ddd);
        total = total.subtract(data);
        System.out.println(total);
    }
}
