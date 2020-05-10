package com.ruoyi.mry.service.impl;

import com.ruoyi.mry.mapper.MryCustomerCardMapper;
import com.ruoyi.mry.mapper.MryCustomerInvestMapper;
import com.ruoyi.mry.mapper.MryCustomerMapper;
import com.ruoyi.mry.model.MryCustomer;
import com.ruoyi.mry.model.MryCustomerCard;
import com.ruoyi.mry.model.MryCustomerInvest;
import com.ruoyi.mry.model.MryCustomerProItem;
import com.ruoyi.mry.util.MryDecimalUtils;
import com.ruoyi.mry.vo.CustomerDiagramVo;
import com.ruoyi.mry.mapper.MryCustomerProItemMapper;
import com.ruoyi.mry.service.MryDataAnalysisService;
import com.ruoyi.mry.util.MryTimeUtils;
import com.ruoyi.mry.vo.MryQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("mryDataAnalysisService")
public class MryDataAnalysisServiceImpl implements MryDataAnalysisService {

    @Autowired
    private MryCustomerProItemMapper customerProItemMapper;
    @Autowired
    private MryCustomerMapper customerMapper;
    @Autowired
    private MryCustomerCardMapper customerCardMapper;
    @Autowired
    private MryCustomerInvestMapper customerInvestMapper;

    @Override
    public CustomerDiagramVo queryRecentCustomerInfo(Short shopId, Long customerId, String startDateStr, String endDateStr) {
        CustomerDiagramVo vo = new CustomerDiagramVo();

        Date startDate, endDate;
        try {
            if (StringUtils.isEmpty(startDateStr) || StringUtils.isEmpty(endDateStr)) {
                // 最近一周日期: 2019-01-04, end = 2019-01-11
                endDate = new Date();
                startDate = MryTimeUtils.getDateByHours(endDate, -168);
            } else {
                startDate = MryTimeUtils.parseTime(startDateStr, MryTimeUtils.DEFAULT_DATE_FORMAT);
                endDate = MryTimeUtils.parseTime(endDateStr, MryTimeUtils.DEFAULT_DATE_FORMAT);
            }
            if (startDate == null || endDate == null) {
                return vo;
            }
            List<Date> datesList = MryTimeUtils.getStaticDates(startDate, endDate);
            String[] xAxisData = new String[datesList.size()];
            String[] seriesData = new String[datesList.size()];
            for (int i = 0, size = datesList.size(); i < size; i++) {
                xAxisData[i] = MryTimeUtils.date2Str(datesList.get(i), "");
                seriesData[i] = "0";
            }
            vo.setXAxisData(xAxisData);
            vo.setSeriesCustomerData(seriesData);

            MryQueryVo queryVo = new MryQueryVo();
            queryVo.setShopId(shopId);
            queryVo.setCustomerId(customerId);
            queryVo.setStartDate(startDate);
            queryVo.setEndDate(endDate);
            List<MryCustomerProItem> customerProItems = this.customerProItemMapper.queryRecentCustomerInfo(queryVo);
            if (CollectionUtils.isEmpty(customerProItems)) {
                return vo;
            }
            Map<Date, Integer> date2Count = new HashMap<>(16);
            Date date;
            int tempCount = 0;
            for (MryCustomerProItem item : customerProItems) {
                date = MryTimeUtils.parseTime(item.getConsumeTime(), MryTimeUtils.DEFAULT_DATE_FORMAT);
                if (!date2Count.containsKey(date)) {
                    date2Count.put(date, 0);
                }
                tempCount = date2Count.get(date);
                date2Count.put(date, ++tempCount);
            }
            for (int i = 0, size = datesList.size(); i < size; i++) {
                if (date2Count.containsKey(datesList.get(i))) {
                    seriesData[i] = date2Count.get(datesList.get(i)).toString();
                }
            }

            vo.setSeriesCustomerData(seriesData);
        } catch (Exception ex) {
            log.info("[queryRecentCustomerInfo] error : " + ex.getMessage(), ex);
        }

        return vo;
    }

    @Override
    public CustomerDiagramVo queryRecentNewCustomerInfo(Short shopId, String startDateStr, String endDateStr) {
        CustomerDiagramVo vo = new CustomerDiagramVo();

        Date startDate, endDate;
        try {
            if (StringUtils.isEmpty(startDateStr) || StringUtils.isEmpty(endDateStr)) {
                // 最近一周日期: 2019-01-04, end = 2019-01-11
                endDate = new Date();
                startDate = MryTimeUtils.getDateByHours(endDate, -168);
            } else {
                startDate = MryTimeUtils.parseTime(startDateStr, MryTimeUtils.DEFAULT_DATE_FORMAT);
                endDate = MryTimeUtils.parseTime(endDateStr, MryTimeUtils.DEFAULT_DATE_FORMAT);
            }
            if (startDate == null || endDate == null) {
                return vo;
            }
            List<Date> datesList = MryTimeUtils.getStaticDates(startDate, endDate);
            String[] xAxisData = new String[datesList.size()];
            String[] seriesData = new String[datesList.size()];
            for (int i = 0, size = datesList.size(); i < size; i++) {
                xAxisData[i] = MryTimeUtils.date2Str(datesList.get(i), "");
                seriesData[i] = "0";
            }
            vo.setXAxisData(xAxisData);
            vo.setSeriesCustomerData(seriesData);

            MryQueryVo queryVo = new MryQueryVo();
            queryVo.setShopId(shopId);
            queryVo.setStartDate(startDate);
            queryVo.setEndDate(endDate);
            List<MryCustomer> customers = this.customerMapper.queryRecentNewCustomerInfo(queryVo);
            if (CollectionUtils.isEmpty(customers)) {
                return vo;
            }
            Map<Date, Integer> date2Count = new HashMap<>(16);
            Date date;
            int tempCount = 0;
            for (MryCustomer item : customers) {
                date = MryTimeUtils.parseTime(item.getCreateTime(), MryTimeUtils.DEFAULT_DATE_FORMAT);
                if (!date2Count.containsKey(date)) {
                    date2Count.put(date, 0);
                }
                tempCount = date2Count.get(date);
                date2Count.put(date, ++tempCount);
            }
            for (int i = 0, size = datesList.size(); i < size; i++) {
                if (date2Count.containsKey(datesList.get(i))) {
                    seriesData[i] = date2Count.get(datesList.get(i)).toString();
                }
            }
            vo.setSeriesCustomerData(seriesData);
        } catch (Exception ex) {
            log.info("[queryRecentNewCustomerInfo] error : " + ex.getMessage(), ex);
        }

        return vo;
    }

    @Override
    public CustomerDiagramVo queryRecentShopMoneyInfo(Short shopId, String startDateStr, String endDateStr) {
        // 指定店面在指定时间内的客户充值金额
        CustomerDiagramVo vo = new CustomerDiagramVo();

        Date startDate, endDate;
        try {
            if (StringUtils.isEmpty(startDateStr) || StringUtils.isEmpty(endDateStr)) {
                // 最近一周日期: 2019-01-04, end = 2019-01-11
                endDate = new Date();
                startDate = MryTimeUtils.getDateByHours(endDate, -168);
            } else {
                startDate = MryTimeUtils.parseTime(startDateStr, MryTimeUtils.DEFAULT_DATE_FORMAT);
                endDate = MryTimeUtils.parseTime(endDateStr, MryTimeUtils.DEFAULT_DATE_FORMAT);
            }
            if (startDate == null || endDate == null) {
                return vo;
            }
            List<Date> datesList = MryTimeUtils.getStaticDates(startDate, endDate);
            String[] xAxisData = new String[datesList.size()];
            String[] seriesData = new String[datesList.size()];
            for (int i = 0, size = datesList.size(); i < size; i++) {
                xAxisData[i] = MryTimeUtils.date2Str(datesList.get(i), "");
                seriesData[i] = "0";
            }
            vo.setXAxisData(xAxisData);
            vo.setSeriesCustomerData(seriesData);

            // 1.客户办卡时充值
            MryQueryVo queryVo = new MryQueryVo();
            queryVo.setShopId(shopId);
            queryVo.setStartDate(startDate);
            queryVo.setEndDate(endDate);
            List<MryCustomerCard> customerCards = this.customerCardMapper.queryRecentShopMoneyInfo1(queryVo);
            if (CollectionUtils.isEmpty(customerCards)) {
                return vo;
            }
            Map<Date, BigDecimal> date2Count = new HashMap<>(16);
            Date date;
            BigDecimal tempCount = MryDecimalUtils.getDefaultDecimal();
            for (MryCustomerCard item : customerCards) {
                date = MryTimeUtils.parseTime(item.getCreateTime(), MryTimeUtils.DEFAULT_DATE_FORMAT);
                if (!date2Count.containsKey(date)) {
                    date2Count.put(date, MryDecimalUtils.getDefaultDecimal());
                }
                tempCount = date2Count.get(date);
                date2Count.put(date, MryDecimalUtils.add(tempCount, item.getPrice()));
            }

            // 2.客户后期继续充值
            List<MryCustomerInvest> customerInvests = this.customerInvestMapper.queryRecentShopMoneyInfo(queryVo);
            if (CollectionUtils.isNotEmpty(customerInvests)) {
                tempCount = MryDecimalUtils.getDefaultDecimal();
                for (MryCustomerInvest item : customerInvests) {
                    date = MryTimeUtils.parseTime(item.getCreateTime(), MryTimeUtils.DEFAULT_DATE_FORMAT);
                    if (!date2Count.containsKey(date)) {
                        date2Count.put(date, MryDecimalUtils.getDefaultDecimal());
                    }
                    tempCount = date2Count.get(date);
                    date2Count.put(date, MryDecimalUtils.add(tempCount, item.getInvestPrice()));
                }
            }

            for (int i = 0, size = datesList.size(); i < size; i++) {
                if (date2Count.containsKey(datesList.get(i))) {
                    seriesData[i] = date2Count.get(datesList.get(i)).toString();
                }
            }
            vo.setSeriesCustomerData(seriesData);
        } catch (Exception ex) {
            log.info("[queryRecentShopMoneyInfo] error : " + ex.getMessage(), ex);
        }

        return vo;
    }

    @Override
    public CustomerDiagramVo queryRecentCustomerMoneyInfo(Short shopId, Long customerId, String startDateStr, String endDateStr) {
        CustomerDiagramVo vo = new CustomerDiagramVo();

        Date startDate, endDate;
        try {
            if (StringUtils.isEmpty(startDateStr) || StringUtils.isEmpty(endDateStr)) {
                // 最近一周日期: 2019-01-04, end = 2019-01-11
                endDate = new Date();
                startDate = MryTimeUtils.getDateByHours(endDate, -168);
            } else {
                startDate = MryTimeUtils.parseTime(startDateStr, MryTimeUtils.DEFAULT_DATE_FORMAT);
                endDate = MryTimeUtils.parseTime(endDateStr, MryTimeUtils.DEFAULT_DATE_FORMAT);
            }
            if (startDate == null || endDate == null) {
                return vo;
            }
            List<Date> datesList = MryTimeUtils.getStaticDates(startDate, endDate);
            String[] xAxisData = new String[datesList.size()];
            String[] seriesData = new String[datesList.size()];
            for (int i = 0, size = datesList.size(); i < size; i++) {
                xAxisData[i] = MryTimeUtils.date2Str(datesList.get(i), "");
                seriesData[i] = "0";
            }
            vo.setXAxisData(xAxisData);
            vo.setSeriesCustomerData(seriesData);

            // 1.客户办卡时充值
            MryQueryVo queryVo = new MryQueryVo();
            queryVo.setShopId(shopId);
            queryVo.setStartDate(startDate);
            queryVo.setEndDate(endDate);
            List<MryCustomerCard> customerCards = this.customerCardMapper.queryRecentShopMoneyInfo1(queryVo);
            if (CollectionUtils.isEmpty(customerCards)) {
                return vo;
            }
            Map<Date, BigDecimal> date2Count = new HashMap<>(16);
            Date date;
            BigDecimal tempCount = MryDecimalUtils.getDefaultDecimal();
            for (MryCustomerCard item : customerCards) {
                date = MryTimeUtils.parseTime(item.getCreateTime(), MryTimeUtils.DEFAULT_DATE_FORMAT);
                if (!date2Count.containsKey(date)) {
                    date2Count.put(date, MryDecimalUtils.getDefaultDecimal());
                }
                tempCount = date2Count.get(date);
                date2Count.put(date, MryDecimalUtils.add(tempCount, item.getPrice()));
            }

            // 2.客户后期继续充值
            List<MryCustomerInvest> customerInvests = this.customerInvestMapper.queryRecentShopMoneyInfo(queryVo);
            if (CollectionUtils.isNotEmpty(customerInvests)) {
                tempCount = MryDecimalUtils.getDefaultDecimal();
                for (MryCustomerInvest item : customerInvests) {
                    date = MryTimeUtils.parseTime(item.getCreateTime(), MryTimeUtils.DEFAULT_DATE_FORMAT);
                    if (!date2Count.containsKey(date)) {
                        date2Count.put(date, MryDecimalUtils.getDefaultDecimal());
                    }
                    tempCount = date2Count.get(date);
                    date2Count.put(date, MryDecimalUtils.add(tempCount, item.getInvestPrice()));
                }
            }

            for (int i = 0, size = datesList.size(); i < size; i++) {
                if (date2Count.containsKey(datesList.get(i))) {
                    seriesData[i] = date2Count.get(datesList.get(i)).toString();
                }
            }
            vo.setSeriesCustomerData(seriesData);
        } catch (Exception ex) {
            log.info("[queryRecentCustomerMoneyInfo] error : " + ex.getMessage(), ex);
        }

        return vo;
    }
}
