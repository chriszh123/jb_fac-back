package com.ruoyi.web.controller.fac;

import com.ruoyi.framework.web.base.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 工具Controller
 *
 * @author zgf
 * @date 2019-01-18
 */
@Controller
@RequestMapping("/fac/tool")
public class ToolController extends BaseController {
    private String prefix = "fac/tool";

    @RequiresPermissions("fac:tailoring:view")
    @GetMapping("/tailoring")
    public String tailoring() {
        return prefix + "/tool";
    }

}
