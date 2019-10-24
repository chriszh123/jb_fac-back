package com.ruoyi.fac.service.impl;

import com.ruoyi.fac.mapper.FacKanjiaHelperMapper;
import com.ruoyi.fac.model.FacKanjiaHelper;
import com.ruoyi.fac.model.FacKanjiaHelperExample;
import com.ruoyi.fac.model.FacKanjiaJoiner;
import com.ruoyi.fac.service.IFacKanjiaHelperService;
import com.ruoyi.system.domain.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 帮砍价人员明细 服务层实现
 *
 * @author ruoyi
 * @date 2019-07-30
 */
@Service
@Slf4j
public class FacKanjiaHelperServiceImpl implements IFacKanjiaHelperService {
    @Autowired
    private FacKanjiaHelperMapper facKanjiaHelperMapper;


    @Override
    public List<FacKanjiaHelper> selectFacKanjiaHelperList(FacKanjiaHelper facKanjiaHelper) {
        FacKanjiaHelperExample helperExample = new FacKanjiaHelperExample();
        FacKanjiaHelperExample.Criteria criteria = helperExample.createCriteria();
        criteria.andIsDeletedEqualTo(false);
        if (facKanjiaHelper.getKanjiaId() != null) {
            criteria.andKanjiaIdEqualTo(facKanjiaHelper.getKanjiaId());
        }

        if (facKanjiaHelper.getJoinId() != null) {
            criteria.andJoinIdEqualTo(facKanjiaHelper.getJoinId());
        }
        if (facKanjiaHelper.getProdId() != null) {
            criteria.andProdIdEqualTo(facKanjiaHelper.getProdId());
        }
        if (StringUtils.isNotBlank(facKanjiaHelper.getProdName())) {
            criteria.andProdNameLike("%" + facKanjiaHelper.getProdName().trim() + "%");
        }
        if (StringUtils.isNotBlank(facKanjiaHelper.getPhoneNumberJoiner())) {
            criteria.andPhoneNumberJoinerLike("%" + facKanjiaHelper.getPhoneNumberJoiner() + "%");
        }
        if (StringUtils.isNotBlank(facKanjiaHelper.getNickNameJoiner())) {
            criteria.andNickNameJoinerLike("%" + facKanjiaHelper.getNickNameJoiner() + "%");
        }
        List<FacKanjiaHelper> helpers = this.facKanjiaHelperMapper.selectByExample(helperExample);
        return helpers;
    }

    @Override
    public int deleteFacKanjiaHelperByIds(String ids, SysUser user) {
        Date nowDate = new Date();
        FacKanjiaHelper update = new FacKanjiaHelper();
        update.setIsDeleted(true);
        update.setUpdateTime(nowDate);
        if (user != null) {
            update.setOperatorId(user.getUserId());
            update.setOperatorName(user.getUserName());
        }
        List<String> idsList = Arrays.asList(ids.split(","));
        List<Integer> itemIds = idsList.stream().map(item -> Integer.valueOf(item)).collect(Collectors.toList());
        FacKanjiaHelperExample example = new FacKanjiaHelperExample();
        example.createCriteria().andIsDeletedEqualTo(false).andIdIn(itemIds);
        int rows = this.facKanjiaHelperMapper.updateByExampleSelective(update, example);
        return rows;
    }
}
