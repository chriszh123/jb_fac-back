package com.ruoyi.fac.service;

import com.ruoyi.fac.exception.FacException;
import com.ruoyi.fac.model.FacBuyer;
import com.ruoyi.fac.model.FacKanjia;
import com.ruoyi.fac.model.FacKanjiaJoiner;
import com.ruoyi.fac.vo.client.req.KanjiaReq;
import com.ruoyi.fac.vo.client.res.KanjiaInfoVo;
import com.ruoyi.fac.vo.client.res.KanjiaListVo;
import com.ruoyi.fac.vo.client.res.KanjiaSetVo;
import com.ruoyi.fac.vo.client.res.KjHelperVo;
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
    KanjiaSetVo queryKanjiaSetFromClient(String prodId) throws FacException;

    // 查询当前商品砍价活动参与信息
    KanjiaInfoVo queryKanJiaInfoFromClient(KanjiaReq req) throws FacException;

    // 用户参加指定商品砍价活动
    void joinKanjia(KanjiaReq req) throws FacException;

    // 帮他砍价，包括自己给自己砍价：俗称的第一刀
    void kanjiaHelp(KanjiaReq req) throws FacException;

    // 当前指定人对应的助力信息
    KjHelperVo kanjiaMyHelp(KanjiaReq req) throws FacException;
}
