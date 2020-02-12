package com.ruoyi.mry.enums;

/**
 * 奖金提现状态
 * Created by zgf
 * Date 2019/5/5 21:06
 * Description
 */
public enum ShopStatus {
    RUNNNING("正常营业", new Byte("1")),
    STOP("休业整顿", new Byte("2"));

    private String name;
    private Byte value;

    private ShopStatus(String name, Byte value) {
        this.name = name;
        this.value = value;
    }

    public static Byte getCodeByName(String name) {
        for (ShopStatus s : ShopStatus.values()) {
            if (s.getName().equals(name)) {
                return s.getValue();
            }
        }
        return STOP.getValue();
    }

    public static String getNameByCode(String value) {
        for (ShopStatus s : ShopStatus.values()) {
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
