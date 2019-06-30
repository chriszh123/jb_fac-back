package com.ruoyi.fac.enums;

/**
 * 奖金提现状态
 * Created by zgf
 * Date 2019/5/5 21:06
 * Description
 */
public enum CashStatus {
    TODEAL("待处理", new Byte("1")),
    FAIL("失败", new Byte("2")),
    SUCCESS("成功", new Byte("3"));

    private String name;
    private Byte value;

    private CashStatus(String name, Byte value) {
        this.name = name;
        this.value = value;
    }

    public static Byte getCodeByName(String name) {
        for (CashStatus s : CashStatus.values()) {
            if (s.getName().equals(name)) {
                return s.getValue();
            }
        }
        return TODEAL.getValue();
    }

    public static String getNameByCode(String value) {
        for (CashStatus s : CashStatus.values()) {
            if (s.getValue().equals(value)) {
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

    public Byte getValue() {
        return value;
    }

    public void setValue(Byte value) {
        this.value = value;
    }
}
