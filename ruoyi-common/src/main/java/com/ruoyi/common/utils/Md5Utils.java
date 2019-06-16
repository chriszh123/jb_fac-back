package com.ruoyi.common.utils;

import java.security.MessageDigest;
import java.util.*;

import com.ruoyi.common.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Md5加密方法
 *
 * @author ruoyi
 */
public class Md5Utils {
    private static final Logger log = LoggerFactory.getLogger(Md5Utils.class);

    private static byte[] md5(String s) {
        MessageDigest algorithm;
        try {
            algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(s.getBytes("UTF-8"));
            byte[] messageDigest = algorithm.digest();
            return messageDigest;
        } catch (Exception e) {
            log.error("MD5 Error...", e);
        }
        return null;
    }

    private static final String toHex(byte hash[]) {
        if (hash == null) {
            return null;
        }
        StringBuffer buf = new StringBuffer(hash.length * 2);
        int i;

        for (i = 0; i < hash.length; i++) {
            if ((hash[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString(hash[i] & 0xff, 16));
        }
        return buf.toString();
    }

    public static String hash(String s) {
        try {
            return new String(toHex(md5(s)).getBytes("UTF-8"), "UTF-8");
        } catch (Exception e) {
            log.error("not supported charset...{}", e);
            return s;
        }
    }

    public static String createSign(SortedMap<String, String> packageParams, String API_KEY) {
        log.info(String.format("============================createSign start = %s", packageParams));
        StringBuffer sb = new StringBuffer();
        Set<?> es = packageParams.entrySet();
        Iterator<?> it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
//            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k) && !"notify_url".equals(k)&& !"out_trade_no".equals(k)&& !"spbill_create_ip".equals(k)&& !"total_fee".equals(k)&& !"trade_type".equals(k)) {
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + API_KEY);
        log.info(String.format("============================sb = %s", sb.toString()));
//        String stringA="appid=wxc7b425229b570867&mch_id=1406330002&nonce_str=1702585759&key=ab42e0b7aa6bce35164a2d14855d7264";
        String sign = hash(sb.toString()).toUpperCase();
        log.info(String.format("============================createSign result = %s", sign));
        return sign;
    }

    public static void main(String[] args) {
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", "wxd54ed7d779db1829");
        packageParams.put("attach", "prepayfac");
        packageParams.put("body", "JBFAC");
        packageParams.put("mch_id", "1516843871");
        packageParams.put("nonce_str", "02e4fc477daf43fe87e14333c0c3fd69");
        packageParams.put("notify_url", "https://www.jbfac.xyz/fac/client/pay/wx/payCallback");
        packageParams.put("out_trade_no", "201906160045520907");
        packageParams.put("spbill_create_ip", "180.109.21.124");
        packageParams.put("total_fee", "1");
        packageParams.put("trade_type", "JSAPI");
        packageParams.put("openid", "o9GRO5SMPZoqbInntYeXAcUJUHKU");

        String sign = createSign(packageParams, "MhBCCK8x4stXCXSBbPvia1TWSO0PNymz");
        System.out.println("sign = " + sign);
    }
}
