package com.ruoyi.fac.vo.client.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class QuestionReq implements Serializable {
    private static final long serialVersionUID = -7903905035638847963L;

    private String token;
    private Integer page = 0;
    private Integer size = 0;
}
