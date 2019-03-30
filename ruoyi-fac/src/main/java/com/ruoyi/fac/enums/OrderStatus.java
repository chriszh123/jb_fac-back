package com.ruoyi.fac.enums;

/**
 * 订单状态
 * Created by zgf
 * Date 2019/1/5 21:06
 * Description
 */
public enum OrderStatus {
    PAYING("待付款", 0),
    TOSHIP("待发货", 1),
    TORECIVE("待收货", 2),
    TOEVALUATE("待评价", 3),
    PAYED("已支付", 4),
    CACELED("已取消", 5),
    CACELING("未取消", 6);

    private String name;
    private Integer code;

    private OrderStatus(String name, Integer code) {
        this.name = name;
        this.code = code;
    }

    public static Integer getCodeByName(String name) {
        for (OrderStatus s : OrderStatus.values()) {
            if (s.getName().equals(name)) {
                return s.getCode();
            }
        }
        return null;
    }

    public static String getNameByCode(Integer value) {
        for (OrderStatus s : OrderStatus.values()) {
            if (s.getCode().equals(value)) {
                return s.getName();
            }
        }
        return "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
