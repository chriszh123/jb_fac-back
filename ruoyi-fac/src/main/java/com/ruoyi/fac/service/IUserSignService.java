package com.ruoyi.fac.service;

import com.ruoyi.fac.exception.FacException;
import com.ruoyi.fac.vo.client.req.SignReq;
import com.ruoyi.fac.vo.client.res.UserScoreLogs;
import com.ruoyi.fac.vo.client.res.UserSignLogs;

/**
 * Created by zgf
 * Date 2019/4/25 22:08
 * Description
 */
public interface IUserSignService {

    UserScoreLogs queryUserConsumerLogs(SignReq req) throws FacException;

    /**
     * 当前用户签到
     *
     * @param req
     * @throws FacException
     */
    int sign(SignReq req) throws FacException;

    /**
     * 指定用户的签到记录
     *
     * @param req
     * @return
     * @throws FacException
     */
    UserSignLogs queryUserSignLogs(SignReq req) throws FacException;

    /**
     * 查询当前用户对应的签到积分明细
     *
     * @param req
     * @return
     * @throws FacException
     */
    UserScoreLogs queryUserScoreLogs(SignReq req) throws FacException;
}
