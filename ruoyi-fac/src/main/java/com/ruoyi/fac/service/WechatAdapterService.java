package com.ruoyi.fac.service;

import com.ruoyi.fac.vo.client.SessionDTO;

public interface WechatAdapterService {
    SessionDTO jscode2session(String code);
}
