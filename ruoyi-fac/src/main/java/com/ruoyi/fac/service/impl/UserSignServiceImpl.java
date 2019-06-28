package com.ruoyi.fac.service.impl;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fac.constant.FacConstant;
import com.ruoyi.fac.enums.ScoreTypeEnum;
import com.ruoyi.fac.exception.FacException;
import com.ruoyi.fac.mapper.FacBuyerMapper;
import com.ruoyi.fac.mapper.FacBuyerSignMapper;
import com.ruoyi.fac.model.FacBuyer;
import com.ruoyi.fac.model.FacBuyerExample;
import com.ruoyi.fac.model.FacBuyerSign;
import com.ruoyi.fac.model.FacBuyerSignExample;
import com.ruoyi.fac.service.IUserSignService;
import com.ruoyi.fac.util.FacCommonUtils;
import com.ruoyi.fac.util.TimeUtils;
import com.ruoyi.fac.vo.client.req.SignReq;
import com.ruoyi.fac.vo.client.res.UserScoreLog;
import com.ruoyi.fac.vo.client.res.UserScoreLogs;
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
    @Autowired
    private FacBuyerMapper facBuyerMapper;

    @Override
    public int sign(SignReq req) throws FacException {
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
        FacBuyerExample buyerExample = new FacBuyerExample();
        buyerExample.createCriteria().andIsDeletedEqualTo(false).andTokenEqualTo(req.getToken());
        List<FacBuyer> buyers = this.facBuyerMapper.selectByExample(buyerExample);
        if (CollectionUtils.isEmpty(buyers)) {
            throw new FacException("请重新授权登录后再签到");
        }
        FacBuyer buyer = buyers.get(0);
        int point = FacCommonUtils.getRandomInt(FacConstant.USER_SIGN_POINT_MAX, FacConstant.USER_SIGN_POINT_MIN);
        Date now = new Date();
        FacBuyerSign sign = new FacBuyerSign();
        sign.setToken(req.getToken());
        sign.setNickName(buyer.getNickName());
        sign.setPoint(Short.valueOf(String.valueOf(point)));
        sign.setType(ScoreTypeEnum.SIGN.getValue());
        sign.setSignTime(nowDate);
        sign.setCreateTime(now);
        sign.setUpdateTime(now);
        sign.setIsDeleted(false);
        int rows = this.facBuyerSignMapper.insertSelective(sign);
        if (rows > 0) {
            // 更新用户表里当前用户相应的积分
            int newPoints = buyer.getPoints() + point;
            buyer.setPoints(Short.valueOf(String.valueOf(newPoints)));
            buyer.setUpdateTime(new Date());
            this.facBuyerMapper.updateByExampleSelective(buyer, buyerExample);
        }
        return point;
    }

    @Override
    public UserSignLogs queryUserSignLogs(SignReq req) throws FacException {
        if (req == null || StringUtils.isBlank(req.getToken())) {
            throw new FacException("参数token不能为空");
        }
        // 查询签到积分
        List<Byte> types = new ArrayList<>();
        types.add(ScoreTypeEnum.SIGN.getValue());

        UserSignLogs logs = new UserSignLogs();
        FacBuyerSignExample example = new FacBuyerSignExample();
        example.createCriteria().andIsDeletedEqualTo(false).andTokenEqualTo(req.getToken()).andTypeIn(types);
        List<FacBuyerSign> signs = this.facBuyerSignMapper.selectByExample(example);
        logs.setTotalRow(CollectionUtils.isNotEmpty(signs) ? signs.size() : 0);
        if (CollectionUtils.isEmpty(signs)) {
            return logs;
        }
        List<UserSignLog> result = this.buildSignLogs(signs);
        logs.setResult(result);

        return logs;
    }

    /**
     * 查询当前用户对应的签到积分明细
     *
     * @param req
     * @return
     * @throws FacException
     */
    @Override
    public UserScoreLogs queryUserScoreLogs(SignReq req) throws FacException {
        UserScoreLogs logs = new UserScoreLogs();
        if (StringUtils.isBlank(req.getToken())) {
            return logs;
        }
        List<Byte> types = new ArrayList<>();
        types.add(ScoreTypeEnum.SIGN.getValue());
        types.add(ScoreTypeEnum.BUY_BACK.getValue());
        types.add(ScoreTypeEnum.COUNSUMER.getValue());

        FacBuyerSignExample example = new FacBuyerSignExample();
        example.createCriteria().andIsDeletedEqualTo(false).andTokenEqualTo(req.getToken());
        example.setOrderByClause(" create_time desc ");
        example.setStartRow((req.getPage() - 1) * req.getPageSize());
        example.setPageSize(req.getPageSize());
        List<FacBuyerSign> buyerSigns = this.facBuyerSignMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(buyerSigns)) {
            List<UserScoreLog> result = new ArrayList<>();
            UserScoreLog log;
            for (FacBuyerSign sign : buyerSigns) {
                log = new UserScoreLog();
                result.add(log);
                log.setTypeStr(ScoreTypeEnum.getNameByCode(sign.getType()));
                log.setDateAdd(TimeUtils.date2Str(sign.getCreateTime(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM_SS));
                log.setScore(sign.getPoint());
                // 0为奖励积分，1为消费积分
                log.setBehavior(ScoreTypeEnum.isReward(sign.getType()) ? 0 : 1);
            }
            logs.setResult(result);
            return logs;
        }

        return logs;
    }

    private List<UserSignLog> buildSignLogs(List<FacBuyerSign> signs) {
        List<UserSignLog> result = new ArrayList<>();
        UserSignLog log;
        for (FacBuyerSign item : signs) {
            log = new UserSignLog();
            log.setContinuous(1);
            log.setDateAdd(TimeUtils.date2Str(item.getSignTime(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_00_00_00));
            if (item.getPoint() != null) {
                log.setPoint(item.getPoint());
            }
            result.add(log);
        }
        return result;
    }

    /**
     * 查询当前用户消费金额明细
     *
     * @param req
     * @return
     * @throws FacException
     */
    @Override
    public UserScoreLogs queryUserConsumerLogs(SignReq req) throws FacException {
        UserScoreLogs logs = new UserScoreLogs();
        if (StringUtils.isBlank(req.getToken())) {
            return logs;
        }
        List<Byte> types = new ArrayList<>();
        types.add(ScoreTypeEnum.COUNSUMER.getValue());

        FacBuyerSignExample example = new FacBuyerSignExample();
        example.createCriteria().andIsDeletedEqualTo(false).andTokenEqualTo(req.getToken()).andTypeIn(types);
        example.setOrderByClause(" create_time desc ");
        example.setStartRow((req.getPage() - 1) * req.getPageSize());
        example.setPageSize(req.getPageSize());
        List<FacBuyerSign> buyerSigns = this.facBuyerSignMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(buyerSigns)) {
            List<UserScoreLog> result = new ArrayList<>();
            UserScoreLog log;
            for (FacBuyerSign sign : buyerSigns) {
                log = new UserScoreLog();
                result.add(log);
                log.setTypeStr(ScoreTypeEnum.getNameByCode(sign.getType()));
                log.setDateAdd(TimeUtils.date2Str(sign.getCreateTime(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM_SS));
                log.setAmount(String.valueOf(sign.getMount()));
                // 0为奖励，1为消费
                log.setBehavior(1);
            }
            logs.setResult(result);
            return logs;
        }

        return logs;
    }


}
