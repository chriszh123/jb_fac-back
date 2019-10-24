package com.ruoyi.fac.service.impl;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fac.exception.FacException;
import com.ruoyi.fac.mapper.FacKanjiaJoinerMapper;
import com.ruoyi.fac.model.FacKanjiaJoiner;
import com.ruoyi.fac.model.FacKanjiaJoinerExample;
import com.ruoyi.fac.service.IFacKanjiaJoinerService;
import com.ruoyi.fac.vo.client.req.KanjiaJoinerVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 参与商品砍价活动人员记录 服务层实现
 *
 * @author ruoyi
 * @date 2019-07-30
 */
@Service
@Slf4j
public class FacKanjiaJoinerServiceImpl implements IFacKanjiaJoinerService {
    @Autowired
    private FacKanjiaJoinerMapper facKanjiaJoinerMapper;


    @Override
    public List<FacKanjiaJoiner> selectFacKanjiaJoinerList(FacKanjiaJoiner facKanjiaJoiner) {
        FacKanjiaJoinerExample example = new FacKanjiaJoinerExample();
        FacKanjiaJoinerExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(false);
        if (facKanjiaJoiner.getKanjiaId() != null) {
            criteria.andKanjiaIdEqualTo(facKanjiaJoiner.getKanjiaId());
        }
        if (facKanjiaJoiner.getProdId() != null) {
            criteria.andKanjiaIdEqualTo(facKanjiaJoiner.getProdId());
        }
        if (StringUtils.isNotBlank(facKanjiaJoiner.getProdName())) {
            criteria.andProdNameLike("%" + facKanjiaJoiner.getProdName().trim() + "%");
        }
        if (StringUtils.isNotBlank(facKanjiaJoiner.getPhoneNumber())) {
            criteria.andPhoneNumberLike("%" + facKanjiaJoiner.getPhoneNumber().trim() + "%");
        }
        if (StringUtils.isNotBlank(facKanjiaJoiner.getNickName())) {
            criteria.andNickNameLike("%" + facKanjiaJoiner.getNickName().trim() + "%");
        }
        if (facKanjiaJoiner.getStatus() != null) {
            criteria.andStatusEqualTo(facKanjiaJoiner.getStatus());
        }
        example.setOrderByClause("create_time desc");
        List<FacKanjiaJoiner> joiners = this.facKanjiaJoinerMapper.selectByExample(example);

        return joiners;
    }

    @Override
    public int insertKanjiaJoiner(KanjiaJoinerVo joinerVo) throws FacException {
        return 0;
    }
}
