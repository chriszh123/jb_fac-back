package com.ruoyi.fac.service;

import com.ruoyi.fac.exception.FacException;
import com.ruoyi.fac.vo.client.req.SignReq;
import com.ruoyi.fac.vo.client.res.UserSignLogs;

/**
 * Created by zgf
 * Date 2019/4/25 22:08
 * Description
 */
public interface IUserSignService {
    // 当前用户签到
    void sign(SignReq req) throws FacException;

    // 指定用户的签到记录
    UserSignLogs queryUserSignLogs(SignReq req) throws FacException;
}
