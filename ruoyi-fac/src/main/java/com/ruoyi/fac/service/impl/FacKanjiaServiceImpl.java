package com.ruoyi.fac.service.impl;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fac.cache.ProductCache;
import com.ruoyi.fac.exception.FacException;
import com.ruoyi.fac.mapper.FacKanjiaMapper;
import com.ruoyi.fac.mapper.FacProductMapper;
import com.ruoyi.fac.model.FacKanjia;
import com.ruoyi.fac.model.FacKanjiaExample;
import com.ruoyi.fac.model.FacProduct;
import com.ruoyi.fac.service.IFacKanjiaService;
import com.ruoyi.fac.util.TimeUtils;
import com.ruoyi.fac.vo.kanjia.KanjiaVo;
import com.ruoyi.system.domain.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
}
