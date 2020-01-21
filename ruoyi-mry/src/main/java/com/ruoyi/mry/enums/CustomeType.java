package com.ruoyi.mry.enums;

/**
 * 奖金提现状态
 * Created by zgf
 * Date 2019/5/5 21:06
 * Description
 */
public enum CustomeType {
    POINTS("积分制", new Byte("0")),
    TIMES("消费次数", new Byte("1"));

    private String name;
    private Byte value;

    private CustomeType(String name, Byte value) {
        this.name = name;
        this.value = value;
    }

    public static Byte getCodeByName(String name) {
        for (CustomeType s : CustomeType.values()) {
            if (s.getName().equals(name)) {
                return s.getValue();
            }
        }
        return POINTS.getValue();
    }

    public static String getNameByCode(String value) {
        for (CustomeType s : CustomeType.values()) {
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
