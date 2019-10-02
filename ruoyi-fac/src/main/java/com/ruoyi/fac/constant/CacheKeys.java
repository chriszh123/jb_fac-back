package com.ruoyi.fac.constant;

public class CacheKeys {
    /**
     * 缓存有效期：天数
     */
    public static int EXPIRIER_TIME = 10;
    /**
     * 商品key：product:商品id
     */
    public static String KEY_FAC_PRODUCT = "fac:facproduct:%s";
    /**
     * 商品key：product:商品id
     */
    public static String KEY_PRODUCT = "fac:product:%s";

    /**
     * 用户key：fac:buyer:token:
     */
    public static String KEY_FAC_BUYER_TOKEN = "fac:buyer:token:%s";

    /**
     * 用户key：fac:buyer:id:
     */
    public static String KEY_FAC_BUYER_ID = "fac:buyer:id:%s";

    public static final String KEY_PREFIX = "fac*";
}
