package com.ruoyi.fac.enums;

/**
 * 订单状态
 * Created by zgf
 * Date 2019/1/5 21:06
 * Description
 */
public enum OrderStatus {
    PAYING("待付款", 0),
    TOWRITEOFF("待核销", 1),
    TOEVALUATE("待评价", 2),
    COMPLETED("已完成", 3),
    CACELED("已取消", 4);

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
