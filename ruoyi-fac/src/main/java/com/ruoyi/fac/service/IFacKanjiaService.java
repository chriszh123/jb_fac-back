package com.ruoyi.fac.service;

import com.ruoyi.fac.exception.FacException;
import com.ruoyi.fac.model.FacKanjia;
import com.ruoyi.fac.model.FacKanjiaJoiner;
import com.ruoyi.fac.vo.client.res.KanjiaListVo;
import com.ruoyi.fac.vo.client.res.KanjiaSetVo;
import com.ruoyi.fac.vo.kanjia.KanjiaVo;
import com.ruoyi.system.domain.SysUser;

import java.util.List;

/**
 * 商品砍价设置 服务层
 *
 * @author ruoyi
 * @date 2019-07-30
 */
public interface IFacKanjiaService {

    List<FacKanjia> selectFacKanjiaList(FacKanjia kanjia) throws FacException;

    int insertFacKanjia(KanjiaVo kanjia, SysUser user) throws FacException;

    FacKanjia selectFacKanjiaById(Long id) throws FacException;

    int updateFacKanjia(FacKanjia kanjia, SysUser user) throws FacException;

    int deleteFacKanjiaByIds(String ids, SysUser user) throws FacException;

    // 小程序端获取砍价商品列表
    KanjiaListVo queryKanjiaListFromClient() throws FacException;

    // 指定商品对应的砍价活动信息
    KanjiaSetVo queryKanjiaSet(String prodId) throws FacException;
}
