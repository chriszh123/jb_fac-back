package com.ruoyi.fac.service.impl;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fac.constant.FacConstant;
import com.ruoyi.fac.exception.FacException;
import com.ruoyi.fac.mapper.FacBuyerSignMapper;
import com.ruoyi.fac.model.FacBuyerSign;
import com.ruoyi.fac.model.FacBuyerSignExample;
import com.ruoyi.fac.service.IUserSignService;
import com.ruoyi.fac.util.FacCommonUtils;
import com.ruoyi.fac.util.TimeUtils;
import com.ruoyi.fac.vo.client.req.SignReq;
import com.ruoyi.fac.vo.client.res.UserSignLog;
import com.ruoyi.fac.vo.client.res.UserSignLogs;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zgf
 * Date 2019/4/25 22:09
 * Description
 */
@Slf4j
@Service("userSignService")
public class UserSignServiceImpl implements IUserSignService {
    @Autowired
    private FacBuyerSignMapper facBuyerSignMapper;

    @Override
    public void sign(SignReq req) throws FacException {
        if (req == null || StringUtils.isBlank(req.getToken())) {
            throw new FacException("参数token不能为空");
        }
        Date nowDate;
        try {
            nowDate = TimeUtils.getCurrDate(TimeUtils.DEFAULT_DATE_TIME_FORMAT_00_00_00);
        } catch (Exception ex) {
            log.error("[sign]  date convet error", ex);
            throw new FacException("日期转换异常");
        }
        FacBuyerSignExample example = new FacBuyerSignExample();
        example.createCriteria().andIsDeletedEqualTo(false).andTokenEqualTo(req.getToken()).andSignTimeEqualTo(nowDate);
        List<FacBuyerSign> signs = this.facBuyerSignMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(signs)) {
            throw new FacException("今天您已签到，请明天再来");
        }
        int point = FacCommonUtils.getRandomInt(FacConstant.USER_SIGN_POINT_MAX, FacConstant.USER_SIGN_POINT_MIN);
        Date now = new Date();
        FacBuyerSign sign = new FacBuyerSign();
        sign.setToken(req.getToken());
        sign.setPoint(Short.valueOf(String.valueOf(point)));
        sign.setSignTime(nowDate);
        sign.setCreateTime(now);
        sign.setUpdateTime(now);
        sign.setIsDeleted(false);
        this.facBuyerSignMapper.insertSelective(sign);
    }

    @Override
    public UserSignLogs queryUserSignLogs(SignReq req) throws FacException {
        if (req == null || StringUtils.isBlank(req.getToken())) {
            throw new FacException("参数token不能为空");
        }
        UserSignLogs logs = new UserSignLogs();
        FacBuyerSignExample example = new FacBuyerSignExample();
        example.createCriteria().andIsDeletedEqualTo(false).andTokenEqualTo(req.getToken());
        List<FacBuyerSign> signs = this.facBuyerSignMapper.selectByExample(example);
        logs.setTotalRow(CollectionUtils.isNotEmpty(signs) ? signs.size() : 0);
        if (CollectionUtils.isEmpty(signs)) {
            return logs;
        }
        List<UserSignLog> result = this.buildSignLogs(signs);
        logs.setResult(result);

        return logs;
    }

    private List<UserSignLog> buildSignLogs(List<FacBuyerSign> signs) {
        List<UserSignLog> result = new ArrayList<>();
        UserSignLog log;
        for (FacBuyerSign item : signs) {
            log = new UserSignLog();
            log.setContinuous(1);
            log.setDateAdd(TimeUtils.date2Str(item.getSignTime(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_00_00_00));
            result.add(log);
        }
        return result;
    }


}
