package com.ruoyi.fac.service;

import com.ruoyi.fac.exception.FacException;
import com.ruoyi.fac.model.FacKanjiaJoiner;
import com.ruoyi.fac.vo.client.req.KanjiaJoinerVo;

import java.util.List;

/**
 * 参与商品砍价活动人员记录 服务层
 *
 * @author ruoyi
 * @date 2019-07-30
 */
public interface IFacKanjiaJoinerService {
    List<FacKanjiaJoiner> selectFacKanjiaJoinerList(FacKanjiaJoiner facKanjiaJoiner);

    int insertKanjiaJoiner(KanjiaJoinerVo joinerVo) throws FacException;
}
