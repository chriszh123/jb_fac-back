package com.ruoyi.fac.service.impl;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fac.cache.ProductCache;
import com.ruoyi.fac.exception.FacException;
import com.ruoyi.fac.mapper.*;
import com.ruoyi.fac.model.*;
import com.ruoyi.fac.service.IFacKanjiaService;
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
                kanjiaInfo.setCurPrice(kjJoiner.getCurrentPrice());
                String statusStr = "进行中";
                if (nowDate.compareTo(kanjia.getStartDate()) < 0) {
                    statusStr = "未开始";
                } else if (nowDate.compareTo(kanjia.getStopDate()) > 0) {
                    statusStr = "已结束";
                }
                kanjiaInfo.setStatusStr(statusStr);
                kanjiaInfo.setDateAdd(TimeUtils.date2Str(kjJoiner.getCreateTime(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM_SS));
                // 助力人数(包括自己)
                FacKanjiaHelperExample helperExample = new FacKanjiaHelperExample();
                helperExample.createCriteria().andKanjiaIdEqualTo(req.getKjid()).andJoinIdEqualTo(Long.valueOf(kjJoiner.getId()));
                int helperNumers = this.facKanjiaHelperMapper.countByExample(helperExample);
                kanjiaInfo.setHelpNumber(helperNumers);
            }
        }

        return vo;
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
}
