package com.ruoyi.fac.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

}
