package com.ruoyi.fac.enums;

/**
 * 商品状态：上架、下架
 * Created by zgf
 * Date 2019/5/1 21:06
 * Description
 */
public enum ScoreTypeEnum {
    SIGN("签到", new Byte("0")),
    BUY_BACK("购物反赠", new Byte("1")),
    COUNSUMER("购物消费", new Byte("2"));

    private String name;
    private Byte value;

    private ScoreTypeEnum(String name, Byte value) {
        this.name = name;
        this.value = value;
    }

    public static Byte getCodeByName(String name) {
        for (ScoreTypeEnum s : ScoreTypeEnum.values()) {
            if (s.getName().equals(name)) {
                return s.getValue();
            }
        }
        return new Byte("0");
    }

    public static String getNameByCode(Byte value) {
        for (ScoreTypeEnum s : ScoreTypeEnum.values()) {
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

    /**
     * 是否为奖励积分
     *
     * @return
     */
    public static boolean isReward(Byte code) {
        boolean reward = true;
        if (code.equals(COUNSUMER.value)) {
            return false;
        }

        return reward;
    }
}
