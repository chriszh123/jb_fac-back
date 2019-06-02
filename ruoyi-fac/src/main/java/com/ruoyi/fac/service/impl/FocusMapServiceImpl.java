package com.ruoyi.fac.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fac.domain.Product;
import com.ruoyi.fac.enums.FocusStatus;
import com.ruoyi.fac.enums.ProductStatus;
import com.ruoyi.fac.mapper.ProductMapper;
import com.ruoyi.fac.util.TimeUtils;
import com.ruoyi.fac.vo.client.BannerVo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ruoyi.fac.mapper.FocusMapMapper;
import com.ruoyi.fac.domain.FocusMap;
import com.ruoyi.fac.service.IFocusMapService;
import com.ruoyi.common.support.Convert;

/**
 * 焦点图 服务层实现
 *
 * @author ruoyi
 * @date 2018-12-24
 */
@Service
public class FocusMapServiceImpl implements IFocusMapService {

    @Autowired
    private FocusMapMapper focusMapMapper;
    @Autowired
    private ProductMapper productMapper;

    /**
     * 查询焦点图信息
     *
     * @param id 焦点图ID
     * @return 焦点图信息
     */
    @Override
    public FocusMap selectFocusMapById(Integer id) {
        return focusMapMapper.selectFocusMapById(id);
    }

    /**
     * 查询焦点图列表
     *
     * @param focusMap 焦点图信息
     * @return 焦点图集合
     */
    @Override
    public List<FocusMap> selectFocusMapList(FocusMap focusMap) {
        focusMap.setIsDeleted(0);
        return focusMapMapper.selectFocusMapList(focusMap);
    }

    /**
     * 新增焦点图
     *
     * @param focusMap 焦点图信息
     * @return 结果
     */
    @Override
    public int insertFocusMap(FocusMap focusMap) {
        Date now = new Date();
        focusMap.setCreateTime(now);
        focusMap.setUpdateTime(now);
        focusMap.setIsDeleted(0);
        focusMap.setPicture(focusMap.getImgPath());
        return focusMapMapper.insertFocusMap(focusMap);
    }

    /**
     * 修改焦点图
     *
     * @param focusMap 焦点图信息
     * @return 结果
     */
    @Override
    public int updateFocusMap(FocusMap focusMap) {
        Date now = new Date();
        focusMap.setUpdateTime(now);
        focusMap.setPicture(focusMap.getImgPath());
        return focusMapMapper.updateFocusMap(focusMap);
    }

    /**
     * 删除焦点图对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteFocusMapByIds(String ids) {
        return focusMapMapper.deleteFocusMapByIds(Convert.toStrArray(ids));
    }

    /**
     * 当前轮播图片
     *
     * @return
     */
    @Override
    public List<BannerVo> selectFocusMapList() {
        List<BannerVo> bannerVos = new ArrayList<>();
        FocusMap focusMap = new FocusMap();
        List<FocusMap> focusMaps = this.selectFocusMapList(focusMap);
        if (CollectionUtils.isEmpty(focusMaps)) {
            return bannerVos;
        }
        BannerVo bannerVo;
        final List<Long> productIds = new ArrayList<>();
        for (int i = 0, size = focusMaps.size(); i < size; i++) {
            FocusMap focusMap1 = focusMaps.get(i);
            if (focusMap1.getStatus().intValue() == 2) {
                // 隐藏的焦点图不展示
                continue;
            }
            // 跳转类型:1-页面；2-商品；3-分类
            if (focusMap1.getJumpType() == null || focusMap1.getJumpType().intValue() != 2) {
                continue;
            }
            productIds.add(Long.valueOf(focusMap1.getJumpParams()));
            bannerVo = new BannerVo();
            bannerVos.add(bannerVo);
            bannerVo.setBusinessId(Long.valueOf(focusMap1.getJumpParams()));
            bannerVo.setDateAdd(TimeUtils.date2Str(focusMap1.getCreateTime(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM_SS));
            if (focusMap1.getUpdateTime() != null) {
                bannerVo.setDateUpdate(TimeUtils.date2Str(focusMap1.getUpdateTime(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM_SS));
            }
            bannerVo.setId(focusMap1.getId());
            bannerVo.setLinkUrl("");
            bannerVo.setPaixu(focusMap1.getSort());
            bannerVo.setPicUrl(focusMap1.getPicture());
            bannerVo.setRemark("");
            bannerVo.setStatus(focusMap1.getStatus());
            bannerVo.setStatusStr(FocusStatus.getNameByCode(focusMap1.getStatus().toString()));
            bannerVo.setTitle(focusMap1.getTitle());
            bannerVo.setType(focusMap1.getJumpType().toString());
            bannerVo.setUserId(focusMap1.getOperatorId());
        }

        // 如果跳转的是商品，需要看当前的商品是否处于上架状态
        if (CollectionUtils.isNotEmpty(productIds)) {
            final List<Long> upperIds = new ArrayList<>();
            Long[] ids = new Long[productIds.size()];
            productIds.toArray(ids);
            final List<Product> products = this.productMapper.selectProductsByIds(ids);
            if (CollectionUtils.isNotEmpty(products)) {
                for (Product product : products) {
                    if (ProductStatus.UPPER_SHELF.getValue().equals(product.getStatus())) {
                        upperIds.add(product.getId());
                    }
                }
            }
            final List<BannerVo> result = new ArrayList<>();
            for (BannerVo item : bannerVos) {
                if (productIds.contains(item.getBusinessId())) {
                    if (upperIds.contains(item.getBusinessId())) {
                        result.add(item);
                    }
                } else {
                    result.add(item);
                }
            }
            return result;
        }

        return bannerVos;
    }

}
