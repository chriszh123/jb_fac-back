package

        com.ruoyi.web.controller.fac;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.fac.domain.Channel;
import com.ruoyi.fac.service.IChannelService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.ExcelUtil;

/**
 * 渠道 信息操作处理
 *
 * @author ruoyi
 * @date 2018-12-24
 */
@Controller
@RequestMapping("/fac/channel")
public class ChannelController extends BaseController {
    private String prefix = "fac/channel";

    @Autowired
    private IChannelService channelService;

    @RequiresPermissions("fac:channel:view")
    @GetMapping()
    public String channel() {
        return prefix + "/channel";
    }

    /**
     * 查询渠道列表
     */
    @RequiresPermissions("fac:channel:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Channel channel) {
        startPage();
        List<Channel> list = channelService.selectChannelList(channel);
        return getDataTable(list);
    }


    /**
     * 导出渠道列表
     */
    @RequiresPermissions("fac:channel:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Channel channel) {
        List<Channel> list = channelService.selectChannelList(channel);
        ExcelUtil<Channel> util = new ExcelUtil<Channel>(Channel.class);
        return util.exportExcel(list, "channel");
    }

    /**
     * 新增渠道
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存渠道
     */
    @RequiresPermissions("fac:channel:add")
    @Log(title = "渠道", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Channel channel) {
        return toAjax(channelService.insertChannel(channel));
    }

    /**
     * 修改渠道
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        Channel channel = channelService.selectChannelById(id);
        mmap.put("channel", channel);
        return prefix + "/edit";
    }

    /**
     * 修改保存渠道
     */
    @RequiresPermissions("fac:channel:edit")
    @Log(title = "渠道", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Channel channel) {
        return toAjax(channelService.updateChannel(channel));
    }

    /**
     * 删除渠道
     */
    @RequiresPermissions("fac:channel:remove")
    @Log(title = "渠道", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(channelService.deleteChannelByIds(ids));
    }

}
