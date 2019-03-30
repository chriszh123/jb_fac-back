/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: LogisticsVo
 */
package com.ruoyi.fac.vo.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * LogisticsVo
 *
 * @author zhangguifeng
 * @create 2019-01-25 16:16
 **/
public class LogisticsVo implements Serializable {
    private static final long serialVersionUID = 1443063896407963850L;

    private boolean isFree = false;
    private int feeType;
    /**
     * "按件数"
     */
    private String feeTypeStr;
    private List<LogisticsDetailVo> details = new ArrayList<>();

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public int getFeeType() {
        return feeType;
    }

    public void setFeeType(int feeType) {
        this.feeType = feeType;
    }

    public String getFeeTypeStr() {
        return feeTypeStr;
    }

    public void setFeeTypeStr(String feeTypeStr) {
        this.feeTypeStr = feeTypeStr;
    }

    public List<LogisticsDetailVo> getDetails() {
        return details;
    }

    public void setDetails(List<LogisticsDetailVo> details) {
        this.details = details;
    }
}
