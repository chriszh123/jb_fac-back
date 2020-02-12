package com.ruoyi.web.controller.mry;

import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.mry.vo.CustomerDiagramVo;
import com.ruoyi.mry.model.MryCustomer;
import com.ruoyi.mry.model.MryCustomerCard;
import com.ruoyi.mry.service.MryCustomerCardService;
import com.ruoyi.mry.service.MryDataAnalysisService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/mry/dataanalysis")
public class MryDataAnalysisController extends BaseController {

    private String prefix = "mry/dataanalysis";

    @Autowired
    private MryDataAnalysisService dataAnalysisService;
    @Autowired
    private MryCustomerCardService customerCardService;

    @RequiresPermissions("mry:dataanalysis:customerAnalysis")
    @GetMapping("/customerAnalysis")
    public String customerAnalysis() {
        return prefix + "/customerAnalysis";
    }

    @PostMapping("/getCustomersByShopId")
    @ResponseBody
    public List<MryCustomer> getCustomersByShopId(MryCustomerCard customerCard) {
        List<MryCustomer> customers = this.customerCardService.getCustomersByShopId(customerCard);
        return customers;
    }

    @PostMapping("/queryRecentCustomerInfo")
    @ResponseBody
    public CustomerDiagramVo queryRecentCustomerInfo(Short shopId, Long customerId, String startDate, String endDate) {
        // 默认当前一周日期内(Basic area chart)
        CustomerDiagramVo vo = this.dataAnalysisService.queryRecentCustomerInfo(shopId, customerId, startDate, endDate);
        return vo;
    }

    @RequiresPermissions("mry:dataanalysis:newcustomer")
    @GetMapping("/newcustomer")
    public String newcustomer() {
        return prefix + "/newcustomer";
    }

    @PostMapping("/queryRecentNewCustomerInfo")
    @ResponseBody
    public CustomerDiagramVo queryRecentNewCustomerInfo(Short shopId, String startDate, String endDate) {
        // 默认当前一周日期内(Basic area chart)
        CustomerDiagramVo vo = this.dataAnalysisService.queryRecentNewCustomerInfo(shopId, startDate, endDate);
        return vo;
    }

    @RequiresPermissions("mry:dataanalysis:shopmoney")
    @GetMapping("/shopmoney")
    public String shopmoney() {
        return prefix + "/shopmoney";
    }

    @PostMapping("/queryRecentShopMoneyInfo")
    @ResponseBody
    public CustomerDiagramVo queryRecentShopMoneyInfo(Short shopId, String startDate, String endDate) {
        // 默认当前一周日期内(Basic area chart)
        CustomerDiagramVo vo = this.dataAnalysisService.queryRecentShopMoneyInfo(shopId, startDate, endDate);
        return vo;
    }

    @RequiresPermissions("mry:dataanalysis:customermoney")
    @GetMapping("/customermoney")
    public String customermoney() {
        return prefix + "/customermoney";
    }

    @PostMapping("/queryRecentCustomerMoneyInfo")
    @ResponseBody
    public CustomerDiagramVo queryRecentCustomerMoneyInfo(Short shopId, Long customerId, String startDate, String endDate) {
        // 默认当前一周日期内(Basic area chart)
        CustomerDiagramVo vo = this.dataAnalysisService.queryRecentCustomerMoneyInfo(shopId, customerId, startDate, endDate);
        return vo;
    }
}
