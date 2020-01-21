package com.ruoyi.mry.service;

import com.ruoyi.mry.vo.CustomerDiagramVo;

public interface MryDataAnalysisService {

    CustomerDiagramVo queryRecentCustomerInfo(Short shopId, Long customerId, String startDate, String endDate);

    CustomerDiagramVo queryRecentNewCustomerInfo(Short shopId, String startDate, String endDate);

    CustomerDiagramVo queryRecentShopMoneyInfo(Short shopId, String startDate, String endDate);

    CustomerDiagramVo queryRecentCustomerMoneyInfo(Short shopId, Long customerId, String startDateStr, String endDateStr);
}
