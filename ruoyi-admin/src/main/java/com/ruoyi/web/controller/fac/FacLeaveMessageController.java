package com.ruoyi.web.controller.fac;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.fac.model.FacLeaveMessage;
import com.ruoyi.fac.service.IFacLeaveMessageService;
import com.ruoyi.fac.vo.leavemessage.LeaveMessageVo;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.system.domain.SysUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户留言信息管理
 */
@Controller
@RequestMapping("/fac/leavemessage")
public class FacLeaveMessageController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(FacLeaveMessageController.class);
    private String prefix = "fac/leavemessage";

    @Resource
    private IFacLeaveMessageService leaveMessageService;

    @RequiresPermissions("fac:leavemessage:view")
    @GetMapping()
    public String leavemessage() {
        return prefix + "/leavemessage";
    }

    @RequiresPermissions("fac:leavemessage:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(FacLeaveMessage leavemessage) {
        startPage();
        final List<FacLeaveMessage> list = this.leaveMessageService.listLeaveMessages(leavemessage);
        return getDataTable(list);
    }

    @RequiresPermissions("fac:leavemessage:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(FacLeaveMessage leavemessage) {
        final List<FacLeaveMessage> list = this.leaveMessageService.listLeaveMessages(leavemessage);
        final ExcelUtil<FacLeaveMessage> util = new ExcelUtil<>(FacLeaveMessage.class);
        return util.exportExcel(list, "用户留言信息");
    }

    @GetMapping("/reply/{leaveMsgId}")
    public String replyLeaveMessage(@PathVariable("leaveMsgId") String leaveMsgId, ModelMap mmap) {
        final LeaveMessageVo leaveMessageVo = new LeaveMessageVo();
        leaveMessageVo.setLeaveMsgId(leaveMsgId);
        mmap.put("leavemessage", leaveMessageVo);

        return prefix + "/reply";
    }

    @RequiresPermissions("fac:leavemessage:reply")
    @Log(title = "回复用户留言", businessType = BusinessType.UPDATE)
    @PostMapping("/reply")
    @ResponseBody
    public AjaxResult replyLeaveMessage(FacLeaveMessage leavemessage) {
        SysUser user = ShiroUtils.getSysUser();
        try {
            int result = this.leaveMessageService.replyLeaveMessage(leavemessage, user);
            return toAjax(result);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return AjaxResult.error(ex.getMessage());
        }
    }
}
