package

        com.ruoyi.web.controller.fac;

import java.util.List;

import com.ruoyi.fac.vo.OrderVo;
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
import com.ruoyi.fac.domain.Order;
import com.ruoyi.fac.service.IOrderService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.ExcelUtil;

/**
 * 订单 信息操作处理
 *
 * @author ruoyi
 * @date 2018-12-24
 */
@Controller
@RequestMapping("/fac/order")
public class OrderController extends BaseController {
    private String prefix = "fac/order";

    @Autowired
    private IOrderService orderService;

    @RequiresPermissions("fac:order:view")
    @GetMapping()
    public String order() {
        return prefix + "/order";
    }

    /**
     * 查询订单列表
     */
    @RequiresPermissions("fac:order:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Order order) {
        startPage();
        List<Order> list = orderService.selectOrderList(order);
        return getDataTable(list);
    }


    /**
     * 导出订单列表
     */
    @RequiresPermissions("fac:order:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Order order) {
        List<Order> list = orderService.selectOrderList(order);
        ExcelUtil<Order> util = new ExcelUtil<Order>(Order.class);
        return util.exportExcel(list, "order");
    }

    /**
     * 新增订单
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存订单
     */
    @RequiresPermissions("fac:order:add")
    @Log(title = "订单", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Order order) {
        return toAjax(orderService.insertOrder(order));
    }

    /**
     * 修改订单
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        OrderVo orderVo = orderService.detailOrderById(id);
        mmap.put("order", orderVo);
        return prefix + "/edit";
    }

    /**
     * 修改保存订单
     */
    @RequiresPermissions("fac:order:edit")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Order order) {
        return toAjax(orderService.updateOrder(order));
    }

    /**
     * 删除订单
     */
    @RequiresPermissions("fac:order:remove")
    @Log(title = "订单", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(orderService.deleteOrderByIds(ids));
    }

    /**
     * 取消订单
     */
    @RequiresPermissions("fac:order:cancel")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PostMapping("/cancel")
    @ResponseBody
    public AjaxResult cacel(String ids) {
        return toAjax(orderService.cancelOrderByIds(ids));
    }

}
