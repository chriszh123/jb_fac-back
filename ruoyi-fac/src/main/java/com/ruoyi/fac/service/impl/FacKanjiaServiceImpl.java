
package com.ruoyi.fac.service.impl;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fac.cache.ProductCache;
import com.ruoyi.fac.enums.KanjiaStatus;
import com.ruoyi.fac.exception.FacException;
import com.ruoyi.fac.mapper.*;
import com.ruoyi.fac.model.*;
import com.ruoyi.fac.service.IFacKanjiaService;
import com.ruoyi.fac.util.DecimalUtils;
import com.ruoyi.fac.util.KJAlgorithmUtil;
import com.ruoyi.fac.util.TimeUtils;
import com.ruoyi.fac.vo.client.req.KanjiaReq;
import com.ruoyi.fac.vo.client.res.*;
import com.ruoyi.fac.vo.kanjia.KanjiaVo;
import com.ruoyi.system.domain.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 商品砍价设置 服务层实现
 *
 * @author ruoyi
 * @date 2019-07-30
 */
@Service("facKanjiaService")
@Slf4j
public class FacKanjiaServiceImpl implements IFacKanjiaService {

    @Autowired
    private FacKanjiaMapper facKanjiaMapper;
    @Autowired
    private ProductCache productCache;
    @Autowired
    private FacBuyerMapper facBuyerMapper;
    @Autowired
    private FacKanjiaJoinerMapper facKanjiaJoinerMapper;
    @Autowired
    private FacKanjiaHelperMapper facKanjiaHelperMapper;

    @Override
    public List<FacKanjia> selectFacKanjiaList(FacKanjia kanjia) throws FacException {
        FacKanjiaExample example = new FacKanjiaExample();
        FacKanjiaExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(false);
        if (kanjia != null) {
            if (kanjia.getId() != null) {
                criteria.andIdEqualTo(kanjia.getId());
            }
            if (kanjia.getProdId() != null) {
                criteria.andProdIdEqualTo(kanjia.getProdId());
            }
            if (kanjia.getStatus() != null) {
                criteria.andStatusEqualTo(kanjia.getStatus());
            }
        }
        example.setOrderByClause("create_time desc");
        List<FacKanjia> list = this.facKanjiaMapper.selectByExample(example);
        return list;
    }

    @Override
    public int insertFacKanjia(KanjiaVo kanjia, SysUser user) throws FacException {
        FacProduct facProduct = this.productCache.getFacProductCache(kanjia.getProdId().toString());
        if (facProduct == null || facProduct.getIsDeleted()) {
            throw new FacException("当前指定商品不存在，请核实");
        }
        Date nowDate = new Date();
        // 校验当前商品在当前时刻是否已经存在砍价活动
        final FacKanjiaExample kanjiaExample = new FacKanjiaExample();
        kanjiaExample.createCriteria().andIsDeletedEqualTo(false).andProdIdEqualTo(kanjia.getProdId());

        this.facKanjiaMapper.countByExample(kanjiaExample);
        FacKanjia record = new FacKanjia();
        BeanUtils.copyProperties(kanjia, record);

        record.setSales(Short.valueOf("0"));
        record.setStatus(new Byte("1"));
        try {
            Date startDate = TimeUtils.parseTime(kanjia.getStartDate(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM);
            Date stopDate = TimeUtils.parseTime(kanjia.getStopDate(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM);
            record.setStartDate(startDate);
            record.setStopDate(stopDate);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new FacException("日期格式错误");
        }
        record.setProdName(facProduct.getName());
        record.setCreateTime(nowDate);
        record.setUpdateTime(nowDate);
        record.setIsDeleted(false);
        if (user != null) {
            record.setOperatorId(user.getUserId());
            record.setOperatorName(user.getUserName());
        }
        // 校验当前设置的砍价范围和当前商品价格是否能正常生成助力金额
        this.checkKanjiaSF(record);
        int rows = this.facKanjiaMapper.insertSelective(record);
        return rows;
    }

    @Override
    public FacKanjia selectFacKanjiaById(Long id) throws FacException {
        if (id == null) {
            throw new FacException("砍价id不能为空");
        }
        FacKanjiaExample example = new FacKanjiaExample();
        example.createCriteria().andIsDeletedEqualTo(false).andIdEqualTo(id);
        List<FacKanjia> kanjians = this.facKanjiaMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(kanjians)) {
            throw new FacException("当前砍价商品信息不存在，请确认");
        }
        FacKanjia data = kanjians.get(0);
        data.setStartDateStr(TimeUtils.date2Str(data.getStartDate(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM));
        data.setStopDateStr(TimeUtils.date2Str(data.getStopDate(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM));
        return data;
    }

    @Override
    public int updateFacKanjia(FacKanjia kanjia, SysUser user) throws FacException {
        if (kanjia == null || kanjia.getId() == null) {
            throw new FacException("砍价id不能为空");
        }
        FacKanjiaJoinerExample joinerExample = new FacKanjiaJoinerExample();
        joinerExample.createCriteria().andIsDeletedEqualTo(false).andKanjiaIdEqualTo(kanjia.getId());
        int joinerCount = this.facKanjiaJoinerMapper.countByExample(joinerExample);
        if (joinerCount > 0) {
//            throw new FacException("当前商品砍价活动已经有人参与，不能再修改相应内容");
        }

        Date nowDate = new Date();
        FacKanjia update = new FacKanjia();
        BeanUtils.copyProperties(kanjia, update);
        update.setUpdateTime(nowDate);
        if (user != null) {
            update.setOperatorId(user.getUserId());
            update.setOperatorName(user.getUserName());
        }
        try {
            if (StringUtils.isNotBlank(kanjia.getStartDateStr())) {
                update.setStartDate(TimeUtils.parseTime(kanjia.getStartDateStr(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM));
            }
            if (StringUtils.isNotBlank(kanjia.getStopDateStr())) {
                update.setStopDate(TimeUtils.parseTime(kanjia.getStopDateStr(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM));
            }
        } catch (Exception ex) {
            throw new FacException("砍价时间格式不对");
        }
        FacKanjiaExample example = new FacKanjiaExample();
        example.createCriteria().andIsDeletedEqualTo(false).andIdEqualTo(kanjia.getId());
        int rows = this.facKanjiaMapper.updateByExampleSelective(update, example);
        return rows;
    }

    @Override
    public int deleteFacKanjiaByIds(String ids, SysUser user) throws FacException {
        if (StringUtils.isBlank(ids)) {
            throw new FacException("砍价id不能为空");
        }
        List<Long> idsList = new ArrayList<>();
        String[] idsArr = ids.split(",");
        for (String id : idsArr) {
            idsList.add(Long.valueOf(id));
        }
        Date nowDate = new Date();
        FacKanjia update = new FacKanjia();
        update.setIsDeleted(true);
        update.setUpdateTime(nowDate);
        if (user != null) {
            update.setOperatorId(user.getUserId());
            update.setOperatorName(user.getUserName());
        }
        FacKanjiaExample example = new FacKanjiaExample();
        example.createCriteria().andIsDeletedEqualTo(false).andIdIn(idsList);
        int rows = this.facKanjiaMapper.updateByExampleSelective(update, example);
        // 相应砍价参与信息
        FacKanjiaJoiner kanjiaJoiner = new FacKanjiaJoiner();
        kanjiaJoiner.setIsDeleted(true);
        kanjiaJoiner.setUpdateTime(nowDate);
        if (user != null) {
            kanjiaJoiner.setOperatorId(user.getUserId());
            kanjiaJoiner.setOperatorName(user.getUserName());
        }
        FacKanjiaJoinerExample joinerExample = new FacKanjiaJoinerExample();
        joinerExample.createCriteria().andIsDeletedEqualTo(false).andKanjiaIdIn(idsList);
        this.facKanjiaJoinerMapper.updateByExampleSelective(kanjiaJoiner, joinerExample);
        // 相应助力信息
        FacKanjiaHelper helper = new FacKanjiaHelper();
        helper.setIsDeleted(true);
        helper.setUpdateTime(nowDate);
        if (user != null) {
            helper.setOperatorId(user.getUserId());
            helper.setOperatorName(user.getUserName());
        }
        FacKanjiaHelperExample helperExample = new FacKanjiaHelperExample();
        helperExample.createCriteria().andIsDeletedEqualTo(false).andKanjiaIdIn(idsList);
        this.facKanjiaHelperMapper.updateByExampleSelective(helper, helperExample);

        return rows;
    }

    @Override
    public KanjiaListVo queryKanjiaListFromClient() throws FacException {
        // 小程序端获取砍价商品列表
        KanjiaListVo data = new KanjiaListVo();

        FacKanjiaExample example = new FacKanjiaExample();
        example.createCriteria().andIsDeletedEqualTo(false);
        example.setOrderByClause(" create_time desc ");
        List<FacKanjia> kanjias = this.facKanjiaMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(kanjias)) {
            return data;
        }
        Date nowDate = new Date();
        List<KanjiaItemVo> result = new ArrayList<>();
        // <goodId, KanjiaProdVo>
        Map<Long, KanjiaProdVo> goodsMap = new HashMap<>();
        KanjiaItemVo vo;
        KanjiaProdVo kanjiaProdVo;
        for (FacKanjia kanjia : kanjias) {
            FacProduct facProduct = this.productCache.getFacProductCache(kanjia.getProdId().toString());
            if (facProduct == null || facProduct.getIsDeleted()) {
                log.info(String.format("[queryKanjiaListFromClient] product is not exist, productId:%s", kanjia.getProdId()));
                continue;
            }
            if (!kanjia.getStatus().equals(new Byte("1"))) {
                log.info(String.format("[queryKanjiaListFromClient] kanjia status is not normal, productId:%s", kanjia.getProdId()));
                continue;
            }
            if (nowDate.compareTo(kanjia.getStartDate()) < 0 || nowDate.compareTo(kanjia.getStopDate()) > 0) {
                log.info(String.format("[queryKanjiaListFromClient] knajia is 未开始或者已经结束, productId:%s", kanjia.getProdId()));
                continue;
            }
            vo = new KanjiaItemVo();
            vo.setKanjiaId(kanjia.getId());
            vo.setGoodsId(kanjia.getProdId());
            vo.setOriginalPrice(kanjia.getOriginalPrice().toString());
            vo.setMinPrice(kanjia.getPrice().toString());
            result.add(vo);

            kanjiaProdVo = new KanjiaProdVo();
            kanjiaProdVo.setName(facProduct.getName());
            // 默认取第一张图片
            if (StringUtils.isNotEmpty(facProduct.getPicture())) {
                String pics = facProduct.getPicture();
                kanjiaProdVo.setPic(this.getFirstNotBlankPic(pics));
            }
            // 商品的特征说明
            kanjiaProdVo.setCharacteristic("");
            goodsMap.put(kanjia.getProdId(), kanjiaProdVo);
        }

        data.setResult(result);
        data.setGoodsMap(goodsMap);
        log.info(String.format("[queryKanjiaListFromClient] success, size = %s", data.getResult().size()));

        return data;
    }

    @Override
    public KanjiaSetVo queryKanjiaSetFromClient(String prodId) throws FacException {
        // 指定商品对应的砍价活动信息
        Date nowDate = new Date();
        FacKanjiaExample example = new FacKanjiaExample();
        example.createCriteria().andIsDeletedEqualTo(false).andProdIdEqualTo(Long.valueOf(prodId))
                .andStartDateLessThanOrEqualTo(nowDate).andStopDateGreaterThanOrEqualTo(nowDate);
        List<FacKanjia> kanjias = this.facKanjiaMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(kanjias)) {
            return null;
        }
        KanjiaSetVo vo = new KanjiaSetVo();
        FacKanjia kanjia = kanjias.get(0);
        vo.setId(kanjia.getId());
        vo.setNumber(kanjia.getTotal());
        vo.setNumberBuy(kanjia.getSales());
        vo.setOriginalPrice(kanjia.getOriginalPrice().toString());
        vo.setMinPrice(kanjia.getPrice().toString());
        vo.setDateEnd(TimeUtils.date2Str(kanjia.getStopDate(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM));

        return vo;
    }

    @Override
    public KanjiaInfoVo queryKanJiaInfoFromClient(KanjiaReq req) throws FacException {
        // 查询当前商品砍价活动参与信息
        Date nowDate = new Date();
        FacKanjiaExample example = new FacKanjiaExample();
        example.createCriteria().andIsDeletedEqualTo(false).andIdEqualTo(req.getKjid());
        List<FacKanjia> kanjias = this.facKanjiaMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(kanjias)) {
            return null;
        }
        FacKanjia kanjia = kanjias.get(0);
        KanjiaInfoVo vo = new KanjiaInfoVo();
        // 当前用户信息
        JoinerVo joiner = new JoinerVo();
        vo.setJoiner(joiner);
        FacBuyerExample buyerExample = new FacBuyerExample();
        buyerExample.createCriteria().andIsDeletedEqualTo(false).andIdEqualTo(req.getJoiner());
        List<FacBuyer> buyers = this.facBuyerMapper.selectByExample(buyerExample);
        if (CollectionUtils.isNotEmpty(buyers)) {
            FacBuyer facBuyer = buyers.get(0);
            joiner.setNick(facBuyer.getNickName());
            // 当前商品对应的当前用户砍价活动信息
            FacKanjiaJoinerExample joinerExample = new FacKanjiaJoinerExample();
            joinerExample.createCriteria().andKanjiaIdEqualTo(req.getKjid()).andTokenEqualTo(facBuyer.getToken());
            List<FacKanjiaJoiner> joiners = this.facKanjiaJoinerMapper.selectByExample(joinerExample);
            if (CollectionUtils.isNotEmpty(joiners)) {
                // 当前用户 + 当前商品的 砍价活动参与信息
                FacKanjiaJoiner kjJoiner = joiners.get(0);
                KjInfoVo kanjiaInfo = new KjInfoVo();
                vo.setKanjiaInfo(kanjiaInfo);
                kanjiaInfo.setProdId(kanjia.getProdId());
                kanjiaInfo.setCurPrice(kjJoiner.getCurrentPrice());
                String statusStr = "进行中";
                if (nowDate.compareTo(kanjia.getStartDate()) < 0) {
                    statusStr = "未开始";
                } else if (nowDate.compareTo(kanjia.getStopDate()) > 0) {
                    statusStr = "已结束";
                }
                kanjiaInfo.setUid(facBuyer.getId());
                kanjiaInfo.setStatusStr(statusStr);
                kanjiaInfo.setDateAdd(TimeUtils.date2Str(kjJoiner.getCreateTime(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM_SS));
                // 助力人数(包括自己)
                FacKanjiaHelperExample helperExample = new FacKanjiaHelperExample();
                helperExample.createCriteria().andKanjiaIdEqualTo(req.getKjid()).andJoinIdEqualTo(Long.valueOf(kjJoiner.getId()));
                int helperNumers = this.facKanjiaHelperMapper.countByExample(helperExample);
                kanjiaInfo.setHelpNumber(helperNumers);
                // 是否已经达到砍价的底价
                boolean upToMinPrice = kanjia.getPrice().compareTo(kanjiaInfo.getCurPrice()) == 0;
                kanjiaInfo.setUpToMinPrice(upToMinPrice);

                // 当前参与砍价活动的助力砍价信息
                if (helperNumers > 0) {
                    helperExample.setOrderByClause(" create_time desc ");
                    List<FacKanjiaHelper> helpers = this.facKanjiaHelperMapper.selectByExample(helperExample);
                    KjHelperVo helperVo;
                    FacKanjiaHelper helper;
                    List<String> helperTokens = new ArrayList<>();
                    for (int i = 0, size = helpers.size(); i < size; i++) {
                        helperVo = new KjHelperVo();
                        vo.getHelps().add(helperVo);
                        helper = helpers.get(i);
                        helperVo.setToken(helper.getTokenHelper());
                        helperVo.setNick(helper.getNickNameHelper());
                        helperVo.setCutPrice(helper.getHelpPrice());
                        helperVo.setDateAdd(TimeUtils.date2Str(helper.getCreateTime(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM_SS));
                        helperTokens.add(helper.getTokenHelper());
                    }

                    // 查询当前助力人员对应的头像信息
                    buyerExample.clear();
                    buyerExample.createCriteria().andIsDeletedEqualTo(false).andTokenIn(helperTokens);
                    List<FacBuyer> helpBuyers = this.facBuyerMapper.selectByExample(buyerExample);
                    // <token, avatarUrl>
                    Map<String, String> token2AvatarUrl = new HashMap<>();
                    if (CollectionUtils.isNotEmpty(helpBuyers)) {
                        for (FacBuyer item : helpBuyers) {
                            token2AvatarUrl.put(item.getToken(), item.getAvatarurl());
                        }
                    }
                    for (KjHelperVo item : vo.getHelps()) {
                        item.setAvatarUrl(token2AvatarUrl.containsKey(item.getToken()) ? token2AvatarUrl.get(item.getToken()) : "");
                    }
                }
                return vo;
            } else {
                // 当前指定用户没有参加当前商品砍价活动就返回null
                return null;
            }
        }

        return null;
    }

    @Override
    public void joinKanjia(KanjiaReq req) throws FacException {
        FacKanjiaExample example = new FacKanjiaExample();
        example.createCriteria().andIsDeletedEqualTo(false).andIdEqualTo(req.getKjid());
        List<FacKanjia> kanjias = this.facKanjiaMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(kanjias)) {
            throw new FacException("当前商品砍价活动已不存在，请联系管理员");
        }
        FacKanjia kanjia = kanjias.get(0);
        Date nowDate = new Date();
        if (nowDate.compareTo(kanjia.getStartDate()) < 0) {
            throw new FacException("当前商品砍价活动暂未开始");
        }
        if (nowDate.compareTo(kanjia.getStopDate()) > 0) {
            throw new FacException("当前商品砍价活动已结束");
        }

        FacBuyerExample buyerExample = new FacBuyerExample();
        buyerExample.createCriteria().andIsDeletedEqualTo(false).andTokenEqualTo(req.getToken());
        List<FacBuyer> buyers = this.facBuyerMapper.selectByExample(buyerExample);
        if (CollectionUtils.isEmpty(buyers)) {
            throw new FacException("对不起，系统没有您的个人信息，请先授权登录");
        }
        FacBuyer facBuyer = buyers.get(0);
        FacKanjiaJoinerExample joinerExample = new FacKanjiaJoinerExample();
        joinerExample.createCriteria().andIsDeletedEqualTo(false).andKanjiaIdEqualTo(req.getKjid()).andTokenEqualTo(req.getToken());
        int count = this.facKanjiaJoinerMapper.countByExample(joinerExample);
        if (count > 0) {
            throw new FacException("您已参加过当前商品砍价活动，机会留给别人吧~");
        }

        BigDecimal toCutMoney = DecimalUtils.subtract(kanjia.getOriginalPrice(), kanjia.getPrice());
        int toCutMoneyI = DecimalUtils.convertFen(toCutMoney);
        int minMoney = DecimalUtils.convertFen(kanjia.getMin());
        int maxMoney = DecimalUtils.convertFen(kanjia.getMax());
        int helpCount = kanjia.getHelpPeopleCount();
        String helpAmount = KJAlgorithmUtil.splitReducePrice(toCutMoneyI, minMoney, maxMoney, helpCount);
        if (StringUtils.isBlank(helpAmount)) {
            log.info("----[joinKanjia] helpAmount fail, toCutMoneyI:%s, minMoney:%s, maxMoney:%s, helpCount:%s"
                    , toCutMoneyI, minMoney, maxMoney, helpCount);
            throw new FacException("参与失败，请联系管理员");
        }

        FacKanjiaJoiner kanjiaJoiner = new FacKanjiaJoiner();
        kanjiaJoiner.setKanjiaId(req.getKjid());
        kanjiaJoiner.setProdId(kanjia.getProdId());
        kanjiaJoiner.setProdName(kanjia.getProdName());
        // 参加用户手机号码
        kanjiaJoiner.setPhoneNumber("");
        kanjiaJoiner.setToken(req.getToken());
        kanjiaJoiner.setNickName(facBuyer.getNickName());
        kanjiaJoiner.setCurrentPrice(kanjia.getOriginalPrice());
        kanjiaJoiner.setPrice(kanjia.getPrice());
        kanjiaJoiner.setStatus(KanjiaStatus.ING.getValue());
        kanjiaJoiner.setHelpAmount(helpAmount);
        kanjiaJoiner.setCreateTime(nowDate);
        kanjiaJoiner.setUpdateTime(nowDate);
        kanjiaJoiner.setOperatorId(facBuyer.getId());
        kanjiaJoiner.setOperatorName(facBuyer.getNickName());
        kanjiaJoiner.setIsDeleted(false);

        this.facKanjiaJoinerMapper.insertSelective(kanjiaJoiner);
        log.info(String.format("--------------[joinKanjia] succcess, buyerId:%s, nickName:%s, prodName:%s, joinId:%s"
                , facBuyer.getId(), facBuyer.getNickName(), kanjia.getProdName(), kanjiaJoiner.getId()));
    }

    @Override
    public KjHelperVo kanjiaHelp(KanjiaReq req) throws FacException {
        // 帮他砍价，包括自己给自己砍价：俗称的第一刀
        log.info(String.format("----------[kanjiaHelp] start, req:%s", JSON.toJSON(req)));
        FacKanjiaExample example = new FacKanjiaExample();
        example.createCriteria().andIsDeletedEqualTo(false).andIdEqualTo(req.getKjid());
        List<FacKanjia> kanjias = this.facKanjiaMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(kanjias)) {
            throw new FacException("当前商品砍价活动已不存在，请联系管理员");
        }
        Date nowDate = new Date();
        FacKanjia kanjia = kanjias.get(0);
        if (nowDate.compareTo(kanjia.getStartDate()) <= 0) {
            throw new FacException("当前商品砍价活动暂未开始");
        }
        if (nowDate.compareTo(kanjia.getStopDate()) >= 0) {
            throw new FacException("当前商品砍价活动已结束");
        }
        // 当前操作涉及到的人员信息：参加砍价活动的人(joinerUser)、助力人员(token)
        FacBuyerExample buyerExample = new FacBuyerExample();
        buyerExample.or().andTokenEqualTo(req.getToken()); // 助力的人
        buyerExample.or().andIdEqualTo(req.getJoinerUser()); // 参与当前商品砍价活动的人
        List<FacBuyer> buyers = this.facBuyerMapper.selectByExample(buyerExample);
        if (CollectionUtils.isEmpty(buyers)) {
            throw new FacException("当前人员信息不存在，请联系管理员");
        }
        FacBuyer joiner = null, helper = null;
        if (buyers.size() == 1) {
            joiner = buyers.get(0);
            helper = buyers.get(0);
        } else {
            for (FacBuyer item : buyers) {
                if (item.getId().equals(req.getJoinerUser())) {
                    joiner = item;
                } else {
                    helper = item;
                }
            }
        }
        if (joiner == null) {
            throw new FacException("缺失商品砍价活动参与人员信息");
        }
        if (helper == null) {
            throw new FacException("缺失商品砍价活动助力人员信息");
        }
        FacKanjiaJoinerExample joinerExample = new FacKanjiaJoinerExample();
        joinerExample.createCriteria().andIsDeletedEqualTo(false).andKanjiaIdEqualTo(req.getKjid()).andTokenEqualTo(joiner.getToken());
        List<FacKanjiaJoiner> kanjiaJoiners = this.facKanjiaJoinerMapper.selectByExample(joinerExample);
        if (CollectionUtils.isEmpty(kanjiaJoiners)) {
            log.info(String.format("----------[kanjiaHelp] lose FacKanjiaJoiner"));
            throw new FacException("请先参加砍价活动再帮他砍价");
        }
        // 当前操作对应的参与的商品砍价活动数据
        FacKanjiaJoiner kanjiaJoiner = kanjiaJoiners.get(0);
        if (StringUtils.equals(kanjiaJoiner.getStatus().toString(), KanjiaStatus.COMPLETED.getValue().toString())) {
            throw new FacException("砍价活动已结束");
        }
        if (StringUtils.equals(kanjiaJoiner.getStatus().toString(), KanjiaStatus.UNCOMPLETED.getValue().toString())) {
            throw new FacException("砍价活动已过期");
        }

        FacKanjiaHelperExample helperExample = new FacKanjiaHelperExample();
        helperExample.createCriteria().andIsDeletedEqualTo(false).andKanjiaIdEqualTo(kanjia.getId())
                .andJoinIdEqualTo(Long.valueOf(kanjiaJoiner.getId())).andTokenHelperEqualTo(helper.getToken());
        int count = this.facKanjiaHelperMapper.countByExample(helperExample);
        if (count > 0) {
            throw new FacException("您已帮他砍过价,不能重复操作");
        }
        String helpAmount = kanjiaJoiner.getHelpAmount();
        if (StringUtils.isBlank(helpAmount)) {
            throw new FacException("砍价活动已结束");
        }
        String[] helpAmountArr = helpAmount.split(",");
        // 剩余的助力金额：助力一次就取走原先的助力金额的第一个，直至取完
        String leftHelpAmount = "";
        if (helpAmountArr.length >= 2) {
            String[] leftHelpAmountArr = new String[helpAmountArr.length - 1];
            System.arraycopy(helpAmountArr, 1, leftHelpAmountArr, 0, helpAmountArr.length - 1);
            leftHelpAmount = StringUtils.join(leftHelpAmountArr, ",");
        }
        // 当前助力砍价的助力帮砍价金额
        BigDecimal helpPrice = DecimalUtils.convert(helpAmount.split(",")[0]);
        // 当前操作对应的助力数据对象
        FacKanjiaHelper kanjiaHelper = new FacKanjiaHelper();
        kanjiaHelper.setKanjiaId(req.getKjid());
        kanjiaHelper.setJoinId(Long.valueOf(kanjiaJoiner.getId()));
        kanjiaHelper.setProdId(kanjiaJoiner.getProdId());
        kanjiaHelper.setProdName(kanjiaJoiner.getProdName());
        kanjiaHelper.setTokenJoiner(joiner.getToken());
        kanjiaHelper.setNickNameJoiner(joiner.getNickName());
        kanjiaHelper.setPhoneNumberJoiner("");
        kanjiaHelper.setTokenHelper(helper.getToken());
        kanjiaHelper.setNickNameHelper(helper.getNickName());
        kanjiaHelper.setPhoneNumberHelper("");
        // 当前操作的实际砍价金额
        kanjiaHelper.setHelpPrice(helpPrice);
        kanjiaHelper.setCreateTime(nowDate);
        kanjiaHelper.setUpdateTime(nowDate);
        kanjiaHelper.setOperatorId(helper.getId());
        kanjiaHelper.setOperatorName(helper.getNickName());
        // 保存当前助力信息
        this.facKanjiaHelperMapper.insertSelective(kanjiaHelper);
        log.info(String.format("----------[kanjiaHelp] insert join helper success, kanjiaHelper:%s", JSON.toJSONString(kanjiaHelper)));
        // 更新当前惭怍对应的砍价活动在此次助力后的实际价格
        BigDecimal real = DecimalUtils.subtract(kanjiaJoiner.getCurrentPrice(), helpPrice);

        kanjiaJoiner.setCurrentPrice(real);
        kanjiaJoiner.setHelpAmount(leftHelpAmount);
        kanjiaJoiner.setUpdateTime(nowDate);
        kanjiaJoiner.setOperatorId(helper.getId());
        kanjiaJoiner.setOperatorName(helper.getNickName());
        joinerExample.clear();
        joinerExample.createCriteria().andIsDeletedEqualTo(false).andIdEqualTo(kanjiaJoiner.getId());
        this.facKanjiaJoinerMapper.updateByExampleSelective(kanjiaJoiner, joinerExample);
        log.info(String.format("----------[kanjiaHelp] update join current price success, kanjiaJoiner:%s", JSON.toJSONString(kanjiaJoiner)));

        KjHelperVo kjHelperVo = new KjHelperVo();
        kjHelperVo.setCutPrice(helpPrice);

        return kjHelperVo;
    }

    @Override
    public KjHelperVo kanjiaMyHelp(KanjiaReq req) throws FacException {
        // 当前指定人对应的助力信息
        FacKanjiaExample example = new FacKanjiaExample();
        example.createCriteria().andIsDeletedEqualTo(false).andIdEqualTo(req.getKjid());
        List<FacKanjia> kanjias = this.facKanjiaMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(kanjias)) {
            throw new FacException("当前商品砍价活动已不存在，请联系管理员");
        }
        FacKanjia kanjia = kanjias.get(0);
        // 当前操作涉及到的人员信息：参加砍价活动的人(joinerUser)、助力人员(token)
        FacBuyerExample buyerExample = new FacBuyerExample();
        buyerExample.or().andTokenEqualTo(req.getToken()); // 助力的人
        buyerExample.or().andIdEqualTo(req.getJoinerUser()); // 参与当前商品砍价活动的人
        List<FacBuyer> buyers = this.facBuyerMapper.selectByExample(buyerExample);
        if (CollectionUtils.isEmpty(buyers)) {
            throw new FacException("当前人员信息不存在，请联系管理员");
        }
        FacBuyer joiner = null, helper = null;
        if (buyers.size() == 1) {
            joiner = buyers.get(0);
            helper = buyers.get(0);
        } else {
            for (FacBuyer item : buyers) {
                if (item.getId().equals(req.getJoinerUser())) {
                    joiner = item;
                } else {
                    helper = item;
                }
            }
        }
        if (joiner == null) {
            throw new FacException("缺失商品砍价活动参与人员信息");
        }
        if (helper == null) {
            throw new FacException("缺失商品砍价活动助力人员信息");
        }
        FacKanjiaJoinerExample joinerExample = new FacKanjiaJoinerExample();
        joinerExample.createCriteria().andIsDeletedEqualTo(false).andKanjiaIdEqualTo(req.getKjid()).andTokenEqualTo(joiner.getToken());
        List<FacKanjiaJoiner> kanjiaJoiners = this.facKanjiaJoinerMapper.selectByExample(joinerExample);
        if (CollectionUtils.isEmpty(kanjiaJoiners)) {
            log.info(String.format("----------[kanjiaHelp] lose FacKanjiaJoiner"));
            throw new FacException("缺失当前人员参与商品砍价活动信息");
        }
        FacKanjiaJoiner kanjiaJoiner = kanjiaJoiners.get(0);

        FacKanjiaHelperExample helperExample = new FacKanjiaHelperExample();
        helperExample.createCriteria().andIsDeletedEqualTo(false).andKanjiaIdEqualTo(kanjia.getId())
                .andJoinIdEqualTo(Long.valueOf(kanjiaJoiner.getId())).andTokenHelperEqualTo(req.getToken());
        List<FacKanjiaHelper> kanjiaHelpers = this.facKanjiaHelperMapper.selectByExample(helperExample);
        if (CollectionUtils.isEmpty(kanjiaHelpers)) {
            throw new FacException("您暂未参加当前商品砍价活动");
        }
        FacKanjiaHelper kanjiaHelper = kanjiaHelpers.get(0);
        KjHelperVo helperVo = new KjHelperVo();
        helperVo.setCutPrice(kanjiaHelper.getHelpPrice());
        helperVo.setNick(kanjiaHelper.getNickNameHelper());
        helperVo.setDateAdd(TimeUtils.date2Str(kanjiaHelper.getCreateTime(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM_SS));

        return helperVo;
    }

    private String getFirstNotBlankPic(String pics) {
        if (StringUtils.isBlank(pics)) {
            return "";
        }
        String[] picsArr = pics.split(",");
        for (int i = 0, size = picsArr.length; i < size; i++) {
            if (StringUtils.isNotBlank(picsArr[i])) {
                return picsArr[i];
            }
        }
        return "";
    }

    private void checkKanjiaSF(FacKanjia kanjia) throws FacException {
        BigDecimal toCutMoney = DecimalUtils.subtract(kanjia.getOriginalPrice(), kanjia.getPrice());
        int toCutMoneyI = DecimalUtils.convertFen(toCutMoney);
        int minMoney = DecimalUtils.convertFen(kanjia.getMin());
        int maxMoney = DecimalUtils.convertFen(kanjia.getMax());
        int helpCount = kanjia.getHelpPeopleCount();
        String helpAmount = KJAlgorithmUtil.splitReducePrice(toCutMoneyI, minMoney, maxMoney, helpCount);
        if (StringUtils.isBlank(helpAmount)) {
            throw new FacException("当前设置的砍价金额范围与当前商品价格生成的助力金额为空，请重新设置砍价范围");
        }
        log.info(String.format("------[checkKanjiaSF] success, kanjia:[%s], helpCount:%s"
                , JSON.toJSONString(kanjia), helpAmount));
    }
}
