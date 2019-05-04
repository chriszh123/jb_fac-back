package com.ruoyi.web.controller.fac;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.fac.domain.Order;
import com.ruoyi.fac.service.IOrderService;
import com.ruoyi.fac.vo.FacStaticVo;
import com.ruoyi.fac.vo.OrderDiagramVo;
import com.ruoyi.fac.vo.OrderItemVo;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.system.domain.SysUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单 信息操作处理
 *
 * @author ruoyi
 * @date 2018-12-24
 */
@Controller
@RequestMapping("/fac/order")
public class OrderController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private String prefix = "fac/order";
    private String prefix_analysis = "fac/dataAnalysis";

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
        return util.exportExcel(list, "订单");
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
        OrderItemVo orderItemVo = orderService.detailOrderById(id);
        mmap.put("order", orderItemVo);
        return prefix + "/edit";
    }

    /**
     * 修改订单
     */
    @GetMapping("/cacelOrder/{id}")
    public String cacelOrder(@PathVariable("id") Long id, ModelMap mmap) {
        OrderItemVo orderItemVo = new OrderItemVo();
        orderItemVo.setId(id.toString());
        mmap.put("order", orderItemVo);
        return prefix + "/cacelOrder";
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
    public AjaxResult cacel(Order order) {
        SysUser user = ShiroUtils.getSysUser();
        try {
            return toAjax(orderService.cancelOrderByIds(order.getId().toString(), order.getRemarkMngt(), user));
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return AjaxResult.error(ex.getMessage());
        }
    }

    /**
     * 数据分析-订单统计
     */
    @RequiresPermissions("fac:order:orderAnalysis")
    @GetMapping("/orderAnalysis")
    public String orderAnalysis() {
        return prefix_analysis + "/orderAnalysis";
    }

    @PostMapping("/queryRecentOrderInfo")
    @ResponseBody
    public OrderDiagramVo queryRecentOrderInfo(String startDate, String endDate) {
        // 默认当前一周日期内(Multiple X Axes)
        OrderDiagramVo vo = this.orderService.queryRecentOrderInfo(startDate, endDate);
        return vo;
    }

    /**
     * 首页统计
     */
    @RequiresPermissions("fac:order:homepage")
    @GetMapping("/homepage")
    public String homepage() {
        return prefix + "/homepage";
    }

    @PostMapping("/queryFacStaticInfo")
    @ResponseBody
    public FacStaticVo queryFacStaticInfo(String startDate, String endDate) {
        // 默认当前一周日期内(坐标轴刻度与标签对齐)
        FacStaticVo vo = this.orderService.queryFacStaticInfo(startDate, endDate);
        return vo;
    }
}
