package com.ruoyi.fac.service;

import com.ruoyi.fac.model.FacKanjiaHelper;
import com.ruoyi.system.domain.SysUser;

import java.util.List;

/**
 * 帮砍价人员明细 服务层
 *
 * @author ruoyi
 * @date 2019-07-30
 */
public interface IFacKanjiaHelperService {
    List<FacKanjiaHelper> selectFacKanjiaHelperList(FacKanjiaHelper facKanjiaHelper);

    int deleteFacKanjiaHelperByIds(String ids, SysUser user);
}
