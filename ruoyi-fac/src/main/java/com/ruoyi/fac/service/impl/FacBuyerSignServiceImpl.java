package com.ruoyi.fac.service.impl;

import com.ruoyi.fac.mapper.FacBuyerSignMapper;
import com.ruoyi.fac.model.FacBuyerSign;
import com.ruoyi.fac.model.FacBuyerSignExample;
import com.ruoyi.fac.service.IFacBuyerSignService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户签到等积分 服务层实现
 *
 * @author ruoyi
 * @date 2019-05-26
 */
@Service
public class FacBuyerSignServiceImpl implements IFacBuyerSignService {
    @Autowired
    private FacBuyerSignMapper facBuyerSignMapper;

    /**
     * 查询用户签到等积分列表
     *
     * @param facBuyerSign 用户签到等积分信息
     * @return 用户签到等积分集合
     */
    @Override
    public List<FacBuyerSign> selectFacBuyerSignList(FacBuyerSign facBuyerSign) {
        FacBuyerSignExample example = new FacBuyerSignExample();
        FacBuyerSignExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(false);
        if (StringUtils.isNotBlank(facBuyerSign.getNickName())) {
            criteria.andNickNameLike(facBuyerSign.getNickName().trim());
        }
        if (facBuyerSign.getType() != null) {
            criteria.andTypeEqualTo(facBuyerSign.getType());
        }
        example.setOrderByClause(" create_time desc ");
        List<FacBuyerSign> signs = this.facBuyerSignMapper.selectByExample(example);

        return signs;
    }
}
