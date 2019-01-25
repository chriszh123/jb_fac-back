/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: PicsVo
 */
package com.ruoyi.fac.vo.client;

import java.io.Serializable;

/**
 * PicsVo
 *
 * @author zhangguifeng
 * @create 2019-01-25 16:21
 **/
public class PicsVo implements Serializable {
    private static final long serialVersionUID = 8958602988967832954L;

    private long goodsId;
    private long id;
    /**
     * "https://cdn.it120.cc/apifactory/2019/01/22/b1a89da11b7411f04c54ba15a1ed4fcc.jpg"
     */
    private String pic;
    private long userId;

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
