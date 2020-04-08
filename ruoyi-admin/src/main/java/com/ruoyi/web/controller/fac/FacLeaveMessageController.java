package com.ruoyi.web.controller.fac;

import com.ruoyi.fac.service.IFacLeaveMessageService;
import com.ruoyi.framework.web.base.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 用户留言信息管理
 */
@Controller
@RequestMapping("/fac/leavemessage")
public class FacLeaveMessageController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(FacLeaveMessageController.class);

    @Resource
    private IFacLeaveMessageService leaveMessageService;


}
