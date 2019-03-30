package com.ruoyi.fac.enums;

/**
 * 商品状态：上架、下架
 * Created by zgf
 * Date 2019/1/5 21:06
 * Description
 */
public enum FacCode {
    SUCCESS("success", 0),
    HAS_NO_DATA("暂无数据", 404),
    PARAMTER_NULL("请求参数不能为空", 400),
    ERROR_WX_LOGIN_SESSION("查询微信用户session接口异常", 10000001),
    ERROR_SERVER_INTERVAL("服务启内部异常，请联系管理员", 509),
    UNREGISTER("未注册", 10000),
    DATA_NOT_EXIST("用户数据已经不存在", 1000001)
    ;

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
