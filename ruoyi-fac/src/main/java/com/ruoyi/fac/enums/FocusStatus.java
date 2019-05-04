package com.ruoyi.fac.enums;

/**
 * 商品状态：上架、下架
 * Created by zgf
 * Date 2019/1/5 21:06
 * Description
 */
public enum FocusStatus {
    VISIBLE("显示", 1),
    HIDE("隐藏", 2);

    private String name;
    private Integer value;

    private FocusStatus(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public static Integer getCodeByName(String name) {
        for (FocusStatus s : FocusStatus.values()) {
            if (s.getName().equals(name)) {
                return s.getValue();
            }
        }
        return 1;
    }

    public static String getNameByCode(String value) {
        for (FocusStatus s : FocusStatus.values()) {
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

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
