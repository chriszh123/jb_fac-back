/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2020/1/17
 * Description: 客户服务项目消费管理
 */
package com.ruoyi.web.controller.mry;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.fac.constant.FacConstant;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.mry.exception.MryException;
import com.ruoyi.mry.model.*;
import com.ruoyi.mry.service.MryCustomerCardService;
import com.ruoyi.mry.service.MryCustomerProItemService;
import com.ruoyi.mry.service.MryServiceProService;
import com.ruoyi.mry.service.MryShopService;
import com.ruoyi.system.domain.SysUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 客户服务项目消费管理
 * @author zhangguifeng
 * @create 2020-01-17 17:11
 **/
@Controller
@RequestMapping("/mry/customerproitem")
public class MryCustomerProItemController extends BaseController {
    private String prefix = "mry/customerproitem";

    @Autowired
    private MryCustomerProItemService customerProItemService;
    @Autowired
    private MryCustomerCardService customerCardService;
    @Autowired
    private MryServiceProService serviceProService;
    @Autowired
    private MryShopService shopService;

    @RequiresPermissions("mry:customerproitem:view")
    @GetMapping()
    public String customerpro() {
        return prefix + "/customerproitem";
    }

    @RequiresPermissions("mry:customerproitem:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(MryCustomerProItem customerProItem) {
        final MryShop shop = new MryShop();
        final List<MryShop> shops = this.shopService.selectShops(shop);
        final MryServicePro servicePro = new MryServicePro();
        servicePro.setShopId(customerProItem.getShopId());
        final List<MryServicePro> servicePros = this.serviceProService.selectMryServicePros(servicePro);

        MryCustomerCard customerCard = new MryCustomerCard();
        if (customerProItem.getShopId() != null) {
            customerCard.setShopId(customerProItem.getShopId());
        }
        if (StringUtils.isNotBlank(customerProItem.getCustomerName()) && StringUtils.isNotBlank(customerProItem.getCustomerName().trim())) {
            customerCard.setCustomerName(customerProItem.getCustomerName());
        }
        final Map<Long, MryCustomer> customers = this.customerCardService.listCustomers(customerCard);
        List<MryCustomerProItem> list = CollUtil.newArrayList();
        startPage();
        if (MapUtil.isNotEmpty(customers)) {
            list = customerProItemService.selectCustomerProItems(customerProItem, shops, customers, servicePros);
        }
        return getDataTable(list);
    }

    @RequiresPermissions("mry:customerproitem:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(MryCustomerProItem customerProItem) {
        final MryShop shop = new MryShop();
        final List<MryShop> shops = this.shopService.selectShops(shop);
        MryCustomerCard customerCard = new MryCustomerCard();
        if (customerProItem.getShopId() != null) {
            customerCard.setShopId(customerProItem.getShopId());
        }
        if (StringUtils.isNotBlank(customerProItem.getCustomerName()) && StringUtils.isNotBlank(customerProItem.getCustomerName().trim())) {
            customerCard.setCustomerName(customerProItem.getCustomerName());
        }
        final Map<Long, MryCustomer> customers = this.customerCardService.listCustomers(customerCard);
        final MryServicePro servicePro = new MryServicePro();
        servicePro.setShopId(customerProItem.getShopId());
        final List<MryServicePro> servicePros = this.serviceProService.selectMryServicePros(servicePro);
        List<MryCustomerProItem> list = CollUtil.newArrayList();
        if (CollUtil.isNotEmpty(customers)) {
            list = customerProItemService.selectCustomerProItems(customerProItem, shops, customers, servicePros);
        }
        ExcelUtil<MryCustomerProItem> util = new ExcelUtil<>(MryCustomerProItem.class);
        return util.exportExcel(list, "客户消费明细资料");
    }

    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    @RequiresPermissions("mry:customerproitem:add")
    @Log(title = "客户消费明细资料", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(MryCustomerProItem customerProItem) {
        SysUser user = ShiroUtils.getSysUser();
        if (user != null) {
            customerProItem.setOperatorId(user.getUserId());
            customerProItem.setOperatorName(user.getUserName());
        } else {
            return AjaxResult.error(FacConstant.ERROR_MSG_LOGIN_USER_NULL);
        }
        return toAjax(customerProItemService.insertCustomerProItem(customerProItem));
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        MryCustomerProItem customerProItem = customerProItemService.selectCustomerProItemById(id);
        mmap.put("customerproitem", customerProItem);
        return prefix + "/edit";
    }

    @RequiresPermissions("mry:customerproitem:edit")
    @Log(title = "客户消费明细资料", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MryCustomerProItem customerProItem) {
        SysUser user = ShiroUtils.getSysUser();
        if (user != null) {
            customerProItem.setOperatorId(user.getUserId());
            customerProItem.setOperatorName(user.getUserName());
        } else {
            return AjaxResult.error(FacConstant.ERROR_MSG_LOGIN_USER_NULL);
        }
        try {
            return toAjax(customerProItemService.updateCustomerProItem(customerProItem));
        } catch (MryException me) {
            return AjaxResult.error(me.getMessage());
        } catch (Exception ex) {
            return AjaxResult.error();
        }
    }

    @RequiresPermissions("mry:customerproitem:remove")
    @Log(title = "客户基本资料", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            SysUser user = ShiroUtils.getSysUser();
            return toAjax(customerProItemService.deleteCustomerProItemByIds(ids, user));
        } catch (Exception ex) {
            return error(ex.getMessage());
        }
    }

    @PostMapping("/getCustomersByShopId")
    @ResponseBody
    public List<MryCustomer> getCustomersByShopId(MryCustomerCard customerCard) {
        List<MryCustomer> customers = this.customerCardService.getCustomersByShopId(customerCard);
        return customers;
    }

    @PostMapping("/getCustomerCardsByShopCustomer")
    @ResponseBody
    public List<MryCustomerCard> getCustomerCardsByShopCustomer(MryCustomerCard customerCard) {
        List<MryCustomerCard> customerCards = this.customerCardService.getCustomerCardsByShopCustomer(customerCard);
        return customerCards;
    }

    @PostMapping("/getServiceProsByShopId")
    @ResponseBody
    public List<MryServicePro> getServiceProsByShopId(MryCustomerCard customerCard) {
        List<MryServicePro> servicePros = this.customerCardService.getServiceProsByShopId(customerCard);
        return servicePros;
    }

    @PostMapping("/getStaffsByShopId")
    @ResponseBody
    public List<MryStaff> getStaffsByShopId(MryCustomerCard customerCard) {
        List<MryStaff> staffList = this.customerCardService.getStaffsByShopId(customerCard);
        return staffList;
    }
}
