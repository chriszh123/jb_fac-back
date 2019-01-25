package com.ruoyi.fac.enums;

/**
 * 商品状态：上架、下架
 * Created by zgf
 * Date 2019/1/5 21:06
 * Description
 */
public enum FacCode {
    SUCCESS("success", 0),
    HAS_NO_DATA("暂无数据", 404);

    private String msg;
    private Integer code;

    private FacCode(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }

    public static Integer getCodeByMsg(String msg) {
        for (FacCode s : FacCode.values()) {
            if (s.getMsg().equals(msg)) {
                return s.getCode();
            }
        }
        return 0;
    }

    public static String getMsgByCode(Integer code) {
        for (FacCode s : FacCode.values()) {
            if (s.getCode().equals(code)) {
                return s.getMsg();
            }
        }
        return "";
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
