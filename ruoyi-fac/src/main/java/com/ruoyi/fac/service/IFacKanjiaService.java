package com.ruoyi.fac.service;

import com.ruoyi.fac.exception.FacException;
import com.ruoyi.fac.model.FacKanjia;
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
}
