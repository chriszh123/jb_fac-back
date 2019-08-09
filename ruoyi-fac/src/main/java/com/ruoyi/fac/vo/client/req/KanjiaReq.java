package com.ruoyi.fac.vo.client.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class KanjiaReq implements Serializable{
    private static final long serialVersionUID = 8144247904949610578L;

    private Long kjid;
    // 用户id
    private Long joiner;
    private String token;
    private String joinerUser;
}
