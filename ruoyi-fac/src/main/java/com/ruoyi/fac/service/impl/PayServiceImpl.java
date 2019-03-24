package com.ruoyi.fac.service.impl;

import com.ruoyi.common.config.Global;
import com.ruoyi.fac.domain.Order;
import com.ruoyi.fac.enums.OrderStatus;
import com.ruoyi.fac.mapper.OrderMapper;
import com.ruoyi.fac.service.IPayService;
import com.ruoyi.fac.util.MD5;
import com.ruoyi.fac.util.WebUtils;
import com.ruoyi.fac.vo.wxpay.WxPrePayReq;
import com.ruoyi.fac.vo.wxpay.WxPrePayRes;
import org.apache.commons.lang3.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by zgf
 * https://www.cnblogs.com/yi1036943655/p/7211275.html
 * Date 2019/2/18 17:16
 * 注意事项：
 * 　　1)、所有的签名和发送微信服务器的数据必须一致 包括Key的大小写 否则签名失败
 * 　　2)、微信小程序 前端调用 接口的时候 文档上并没有写appId参数 该参数一定要穿 并且是大写
 * 　　3)、交易类型 为 JSAPI 的时候 则必须传入openid
 * 　　4)、body格式问题 写的是UTF-8 实际要的格式则是ISO8859-1 而且单独对body进行设置好像不好使 所以必须全部都改成该格式
 * 　　5)、生成签名 最后加上key的那块 加的格式是 &key = KEY 这种 而且不是直接 + key 这个地方需要注意一下 我碰了个坑 文档没看仔细
 * 　　6)、数据包ID 格式 不是 value直接设置成 数据包ID就可以 前面需要加 "prepay_id="
 * 　　7)、最后一点强调 生成签名的数据和发送服务器的数据 必须保持一致
 */
@Service("payService")
public class PayServiceImpl implements IPayService {
    private static final Logger logger = LoggerFactory.getLogger(PayServiceImpl.class);

    private static final String prepayUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 微信支付预支付接口
     *
     * @param req
     * @return
     * @throws Exception
     */
    @Override
    public WxPrePayRes getWxPrePayInfo(WxPrePayReq req, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //设置最终返回对象
        WxPrePayRes res = new WxPrePayRes();
        String openid = req.getToken();
        //接口调用总金额单位为分换算一下(测试金额改成1,单位为分则是0.01,根据自己业务场景判断是转换成float类型还是int类型)
        //String amountFen = Integer.valueOf((Integer.parseInt(amount)*100)).toString();
//        String amountFen = Float.valueOf((Float.parseFloat(req.getMoney()) * 100)).toString();
        String amountFen = "1";
        //创建hashmap(用户获得签名)
        SortedMap<String, String> paraMap = new TreeMap<String, String>();
        //设置body变量 (支付成功显示在微信支付 商品详情中)
        String body = "啦啦啦测试";
        Order order = this.orderMapper.selectOrderById(Long.valueOf(req.getNextAction().getId()));
        if (order == null) {
            throw new Exception("当前订单已不存在，请确认");
        }
        if (!OrderStatus.PAYING.getCode().equals(order.getStatus())) {
            throw new Exception("当前订单处于非待付款状态，请核对后再操作");
        }
        //设置随机字符串
        String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
        //设置商户订单号
        String outTradeNo = order.getOrderNo();

        //设置请求参数(小程序ID)
        paraMap.put("appid", Global.getFacAppId().toUpperCase());
        //设置请求参数(商户号)
        paraMap.put("mch_id", Global.getFacMchId().toUpperCase());
        //设置请求参数(随机字符串)
        paraMap.put("nonce_str", nonceStr);
        //设置请求参数(商品描述)
        paraMap.put("body", body);
        //设置请求参数(商户订单号)
        paraMap.put("out_trade_no", outTradeNo);
        //设置请求参数(总金额)
        paraMap.put("total_fee", amountFen);
        //设置请求参数(终端IP)
        paraMap.put("spbill_create_ip", WebUtils.getIpAddress(request));
        //设置请求参数(通知地址)
        paraMap.put("notify_url", Global.getDomain() + "/fac/client/pay/wx/payCallback");
        //设置请求参数(交易类型)
        paraMap.put("trade_type", "JSAPI");
        //设置请求参数(openid)(在接口文档中 该参数 是否必填项 但是一定要注意 如果交易类型设置成'JSAPI'则必须传入openid)
        paraMap.put("openid", openid);
        //调用逻辑传入参数按照字段名的 ASCII 码从小到大排序（字典序）
        String stringA = formatUrlMap(paraMap, false, false);
        //第二步，在stringA最后拼接上key得到stringSignTemp字符串，并对stringSignTemp进行MD5运算，再将得到的字符串所有字符转换为大写，得到sign值signValue。(签名)
        String sign = MD5.MD5Encode(stringA + "&key=" + Global.getFacMchSecret()).toUpperCase();
        //将参数 编写XML格式
        StringBuffer paramBuffer = new StringBuffer();
        paramBuffer.append("<xml>");
        paramBuffer.append("<appid>" + Global.getFacAppId().toUpperCase() + "</appid>");
        paramBuffer.append("<mch_id>" + Global.getFacMchId().toUpperCase() + "</mch_id>");
        paramBuffer.append("<nonce_str>" + paraMap.get("nonce_str") + "</nonce_str>");
        paramBuffer.append("<sign>" + sign + "</sign>");
        paramBuffer.append("<body>" + body + "</body>");
        paramBuffer.append("<out_trade_no>" + paraMap.get("out_trade_no") + "</out_trade_no>");
        paramBuffer.append("<total_fee>" + paraMap.get("total_fee") + "</total_fee>");
        paramBuffer.append("<spbill_create_ip>" + paraMap.get("spbill_create_ip") + "</spbill_create_ip>");
        paramBuffer.append("<notify_url>" + paraMap.get("notify_url") + "</notify_url>");
        paramBuffer.append("<trade_type>" + paraMap.get("trade_type") + "</trade_type>");
        paramBuffer.append("<openid>" + paraMap.get("openid") + "</openid>");
        paramBuffer.append("</xml>");

        try {
            //发送请求(POST)(获得数据包ID)(这有个注意的地方 如果不转码成ISO8859-1则会告诉你body不是UTF8编码 就算你改成UTF8编码也一样不好使 所以修改成ISO8859-1)
            Map<String, String> map = doXMLParse(getRemotePortData(prepayUrl, new String(paramBuffer.toString().getBytes(), "ISO8859-1")));
            //应该创建 支付表数据
            if (map != null) {
                if (map.containsKey("prepay_id")) {
                    Long prepayId = Long.valueOf(map.get("prepay_id"));
                    this.orderMapper.updateOrderPrePayId(order.getId(), prepayId);

                    //创建 时间戳
                    String timeStamp = Long.valueOf(System.currentTimeMillis()).toString();
                    // 签名
                    //创建hashmap(用户获得签名)
                    paraMap = new TreeMap<String, String>();
                    //设置(小程序ID)(这块一定要是大写)
                    paraMap.put("appId", Global.getFacAppId().toLowerCase());
                    //设置(时间戳)
                    paraMap.put("timeStamp", timeStamp);
                    //设置(随机串)
                    paraMap.put("nonceStr", nonceStr);
                    //设置(数据包)
                    paraMap.put("package", "prepay_id=" + prepayId);
                    //设置(签名方式)
                    paraMap.put("signType", "MD5");
                    //调用逻辑传入参数按照字段名的 ASCII 码从小到大排序（字典序）
                    stringA = formatUrlMap(paraMap, false, false);
                    //第二步，在stringA最后拼接上key得到stringSignTemp字符串，并对stringSignTemp进行MD5运算，再将得到的字符串所有字符转换为大写，得到sign值signValue。(签名)
                    sign = MD5.MD5Encode(stringA + "&key=" + Global.getFacMchSecret()).toUpperCase();
                    if (StringUtils.isNotBlank(sign)) {
                        res.setTimeStamp(timeStamp);
                        res.setNonceStr(paraMap.get("nonce_str"));
                        res.setPrepayId(prepayId.toString());
                        res.setSign(sign);
                        logger.info("微信 支付接口生成签名 设置返回值");
                    }
                }
            }
            logger.info("微信 支付接口生成签名 方法结束");
        } catch (UnsupportedEncodingException e) {
            logger.info("微信 统一下单 异常：" + e.getMessage(), e);
        } catch (Exception e) {
            logger.info("微信 统一下单 异常：" + e.getMessage(), e);
        }

        return res;
    }

    /**
     * 微信支付结果通知接口
     *
     * @param request
     * @param response
     */
    @Override
    public void payCallback(HttpServletRequest request, HttpServletResponse response) {
        logger.info("微信回调接口方法 start");
        logger.info("微信回调接口 操作逻辑 start");
        String inputLine = "";
        String notityXml = "";
        try {
            while ((inputLine = request.getReader().readLine()) != null) {
                notityXml += inputLine;
            }
            //关闭流
            request.getReader().close();
            logger.info("微信回调内容信息：" + notityXml);
            //解析成Map
            Map<String, String> map = doXMLParse(notityXml);
            //判断 支付是否成功
            if ("SUCCESS".equals(map.get("result_code"))) {
                logger.info("微信回调返回是否支付成功：是");
                //获得 返回的商户订单号
                String outTradeNo = map.get("out_trade_no");
                logger.info("微信回调返回商户订单号：" + outTradeNo);
                //访问DB
                Order order = this.orderMapper.selectOrderByOrderNo(outTradeNo);
                if (order != null) {
                    logger.info("微信回调 根据订单号查询订单状态：" + order.getStatus());
                    if (OrderStatus.PAYING.getCode().equals(order.getStatus())) {
                        //修改支付状态
                        int sqlRow = this.orderMapper.updateOrderStatusAfterPayed(outTradeNo, OrderStatus.PAYED.getCode().intValue());
                        if (sqlRow == 1) {
                            logger.info("微信回调  订单号：" + outTradeNo + ",修改状态成功");
                            //封装 返回值
                            StringBuffer buffer = new StringBuffer();
                            buffer.append("<xml>");
                            buffer.append("<return_code>SUCCESS</return_code>");
                            buffer.append("<return_msg>OK</return_msg>");
                            buffer.append("</xml>");
                            //给微信服务器返回 成功标示 否则会一直询问 咱们服务器 是否回调成功
                            PrintWriter writer = response.getWriter();
                            //返回
                            writer.print(buffer.toString());
                        }
                    }
                }
            }
        } catch (IOException e) {
            logger.error("[payCallback IOException] error", e);
        } catch (Exception e) {
            logger.error("[payCallback Exception] error", e);
        }
    }

    /**
     * 方法用途: 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序），并且生成url参数串<br>
     * 实现步骤: <br>
     *
     * @param paraMap    要排序的Map对象
     * @param urlEncode  是否需要URLENCODE
     * @param keyToLower 是否需要将Key转换为全小写
     *                   true:key转化成小写，false:不转化
     * @return
     */
    private static String formatUrlMap(Map<String, String> paraMap, boolean urlEncode, boolean keyToLower) {
        String buff = "";
        Map<String, String> tmpMap = paraMap;
        try {
            List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(tmpMap.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
                @Override
                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });
            // 构造URL 键值对的格式
            StringBuilder buf = new StringBuilder();
            for (Map.Entry<String, String> item : infoIds) {
                if (StringUtils.isNotBlank(item.getKey())) {
                    String key = item.getKey();
                    String val = item.getValue();
                    if (urlEncode) {
                        val = URLEncoder.encode(val, "utf-8");
                    }
                    if (keyToLower) {
                        buf.append(key.toLowerCase() + "=" + val);
                    } else {
                        buf.append(key + "=" + val);
                    }
                    buf.append("&");
                }

            }
            buff = buf.toString();
            if (buff.isEmpty() == false) {
                buff = buff.substring(0, buff.length() - 1);
            }
        } catch (Exception e) {
            return null;
        }
        return buff;
    }

    /**
     * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
     *
     * @param strxml
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    private Map<String, String> doXMLParse(String strxml) throws Exception {
        if (null == strxml || "".equals(strxml)) {
            return null;
        }

        Map<String, String> m = new HashMap<String, String>();
        InputStream in = String2Inputstream(strxml);
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(in);
        Element root = doc.getRootElement();
        List list = root.getChildren();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Element e = (Element) it.next();
            String k = e.getName();
            String v = "";
            List children = e.getChildren();
            if (children.isEmpty()) {
                v = e.getTextNormalize();
            } else {
                v = getChildrenText(children);
            }

            m.put(k, v);
        }

        //关闭流
        in.close();

        return m;
    }

    private InputStream String2Inputstream(String str) {
        return new ByteArrayInputStream(str.getBytes());
    }

    /**
     * 获取子结点的xml
     *
     * @param children
     * @return String
     */
    @SuppressWarnings("rawtypes")
    private static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if (!children.isEmpty()) {
            Iterator it = children.iterator();
            while (it.hasNext()) {
                Element e = (Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if (!list.isEmpty()) {
                    sb.append(getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }

        return sb.toString();
    }

    /**
     * 方法名: getRemotePortData
     * 描述: 发送远程请求 获得代码示例
     * 参数：  @param urls 访问路径
     * 参数：  @param param 访问参数-字符串拼接格式, 例：port_d=10002&port_g=10007&country_a=
     * 创建人: Xia ZhengWei
     * 创建时间: 2017年3月6日 下午3:20:32
     * 版本号: v1.0
     * 返回类型: String
     */
    private String getRemotePortData(String urls, String param) {
        logger.info("港距查询抓取数据----开始抓取外网港距数据");
        try {
            URL url = new URL(urls);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置连接超时时间
            conn.setConnectTimeout(30000);
            // 设置读取超时时间
            conn.setReadTimeout(30000);
            conn.setRequestMethod("POST");
            if (StringUtils.isNotBlank(param)) {
                conn.setRequestProperty("Origin", "https://sirius.searates.com");// 主要参数
                conn.setRequestProperty("Referer", "https://sirius.searates.com/cn/port?A=ChIJP1j2OhRahjURNsllbOuKc3Y&D=567&G=16959&shipment=1&container=20st&weight=1&product=0&request=&weightcargo=1&");
                conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");// 主要参数
            }
            // 需要输出
            conn.setDoInput(true);
            // 需要输入
            conn.setDoOutput(true);
            // 设置是否使用缓存
            conn.setUseCaches(false);
            // 设置请求属性
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            conn.setRequestProperty("Charset", "UTF-8");

            if (StringUtils.isNotBlank(param)) {
                // 建立输入流，向指向的URL传入参数
                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                dos.writeBytes(param);
                dos.flush();
                dos.close();
            }
            // 输出返回结果
            InputStream input = conn.getInputStream();
            int resLen = 0;
            byte[] res = new byte[1024];
            StringBuilder sb = new StringBuilder();
            while ((resLen = input.read(res)) != -1) {
                sb.append(new String(res, 0, resLen));
            }
            return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            logger.info("港距查询抓取数据----抓取外网港距数据发生异常：" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("港距查询抓取数据----抓取外网港距数据发生异常：" + e.getMessage());
        }
        logger.info("港距查询抓取数据----抓取外网港距数据失败, 返回空字符串");
        return "";
    }
}
