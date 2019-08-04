package com.ruoyi.web.controller.fac.client;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.fac.exception.FacException;
import com.ruoyi.fac.service.IFacKanjiaJoinerService;
import com.ruoyi.fac.vo.client.FacResult;
import com.ruoyi.fac.vo.client.req.KanjiaJoinerVo;
import com.ruoyi.framework.web.base.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/fac/client/kanjia")
public class KanjiaClientController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(KanjiaClientController.class);

    @Autowired
    private IFacKanjiaJoinerService facKanjiaJoinerService;

    /**
     * 新增参与商品砍价活动人员记录
     */
    @Log(title = "参与商品砍价活动人员记录", businessType = BusinessType.INSERT)
    @PostMapping("/join")
    @ResponseBody
    public FacResult joinKanjia(@RequestBody KanjiaJoinerVo joinerVo) {
        try {
            int rows = this.facKanjiaJoinerService.insertKanjiaJoiner(joinerVo);
            return FacResult.success(rows);
        } catch (FacException fe) {
            log.error(fe.getMessage(), fe);
            return FacResult.error(fe.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return FacResult.error();
        }
    }
}
