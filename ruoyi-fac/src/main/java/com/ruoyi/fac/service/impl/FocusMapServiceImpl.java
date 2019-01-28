package com.ruoyi.fac.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ruoyi.fac.enums.FocusStatus;
import com.ruoyi.fac.util.TimeUtils;
import com.ruoyi.fac.vo.client.BannerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fac.mapper.FocusMapMapper;
import com.ruoyi.fac.domain.FocusMap;
import com.ruoyi.fac.service.IFocusMapService;
import com.ruoyi.common.support.Convert;
import org.springframework.util.CollectionUtils;

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
        BannerVo bannerVo = null;
        for (int i = 0, size = focusMaps.size(); i < size; i++) {
            FocusMap focusMap1 = focusMaps.get(i);
            // 跳转类型:1-页面；2-商品；3-分类
            if (focusMap1.getJumpType() == null || focusMap1.getJumpType().intValue() != 2) {
                continue;
            }
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

        return bannerVos;
    }

}
