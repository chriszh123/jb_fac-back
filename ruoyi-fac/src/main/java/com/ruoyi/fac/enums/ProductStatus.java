package com.ruoyi.fac.enums;

/**
 * 商品状态：上架、下架
 * Created by zgf
 * Date 2019/1/5 21:06
 * Description
 */
public enum ProductStatus {
    UPPER_SHELF("上架", 1),
    LOWER_SHELF("下架", 2);

    private String name;
    private Integer value;

    private ProductStatus(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public static String getCodeByName(String name) {
        for (ProductStatus s : ProductStatus.values()) {
            if (s.getName().equals(name)) {
                return s.getName();
            }
        }
        return "0";
    }

    public static String getNameByCode(String value) {
        for (ProductStatus s : ProductStatus.values()) {
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
