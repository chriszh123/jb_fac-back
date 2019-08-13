package com.ruoyi.fac.enums;

/**
 * 商品砍价活动状态
 * Created by zgf
 * Date 2019/8/13 21:06
 * Description
 */
public enum KanjiaStatus {
    ING("进行中;", new Byte("1")),
    COMPLETED("砍价完成", new Byte("2")),
    UNCOMPLETED("砍价未完成(已过期)", new Byte("3"));

    private String name;
    private Byte value;

    private KanjiaStatus(String name, Byte value) {
        this.name = name;
        this.value = value;
    }

    public static Byte getCodeByName(String name) {
        for (KanjiaStatus s : KanjiaStatus.values()) {
            if (s.getName().equals(name)) {
                return s.getValue();
            }
        }
        return 1;
    }

    public static String getNameByCode(Byte value) {
        for (KanjiaStatus s : KanjiaStatus.values()) {
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
