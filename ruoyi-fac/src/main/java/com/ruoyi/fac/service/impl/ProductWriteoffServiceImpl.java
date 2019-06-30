package com.ruoyi.fac.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fac.domain.FacProductWriteoff;
import com.ruoyi.fac.domain.Order;
import com.ruoyi.fac.mapper.FacProductWriteOffBeanMapper;
import com.ruoyi.fac.mapper.FacProductWriteoffMapper;
import com.ruoyi.fac.mapper.OrderMapper;
import com.ruoyi.fac.model.FacOrder;
import com.ruoyi.fac.model.FacProductWriteOffBean;
import com.ruoyi.fac.model.FacProductWriteOffBeanExample;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fac.service.IProductWriteoffService;
import com.ruoyi.common.support.Convert;

/**
 * 核销记录 服务层实现
 *
 * @author ruoyi
 * @date 2018-12-24
 */
@Service
public class ProductWriteoffServiceImpl implements IProductWriteoffService {
    @Autowired
    private FacProductWriteoffMapper productWriteoffMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private FacProductWriteOffBeanMapper facProductWriteOffBeanMapper;

    /**
     * 查询核销记录信息
     *
     * @param id 核销记录ID
     * @return 核销记录信息
     */
    @Override
    public FacProductWriteoff selectProductWriteoffById(Integer id) {
        return productWriteoffMapper.selectFacProductWriteoffById(id);
    }

    /**
     * 查询核销记录列表
     *
     * @param productWriteoff 核销记录信息
     * @return 核销记录集合
     */
    @Override
    public List<FacProductWriteOffBean> selectProductWriteoffList(FacProductWriteoff productWriteoff) {
        if (productWriteoff.getProductId() == null) {
            return new ArrayList<>();
        }
        // 当前商品涉及到的订单
        List<Long> prodIds = new ArrayList<>();
        prodIds.add(productWriteoff.getProductId());
        List<FacOrder> orders = this.orderMapper.selectProductsByProdAndStatus(prodIds, null);
        if (CollectionUtils.isEmpty(orders)) {
            return new ArrayList<>();
        }
        List<String> orderNos = new ArrayList<>();
        for (FacOrder item : orders) {
            orderNos.add(item.getOrderNo());
        }
        // 当前订单对应的核销记录
        List<Integer> status = new ArrayList<>();
        if (productWriteoff.getStatus() != null) {
            status.add(productWriteoff.getStatus());
        }

        FacProductWriteOffBeanExample beanExample = new FacProductWriteOffBeanExample();
        beanExample.createCriteria().andIsDeletedEqualTo(false).andOrderNoIn(orderNos).andProdIdEqualTo(productWriteoff.getProductId());
        beanExample.setOrderByClause(" create_time desc ");
        List<FacProductWriteOffBean> productWriteoffs = this.facProductWriteOffBeanMapper.selectByExample(beanExample);

        return productWriteoffs;
    }

    /**
     * 新增核销记录
     *
     * @param productWriteoff 核销记录信息
     * @return 结果
     */
    @Override
    public int insertProductWriteoff(FacProductWriteoff productWriteoff) {
        return productWriteoffMapper.insertFacProductWriteoff(productWriteoff);
    }

    /**
     * 修改核销记录
     *
     * @param productWriteoff 核销记录信息
     * @return 结果
     */
    @Override
    public int updateProductWriteoff(FacProductWriteoff productWriteoff) {
        return productWriteoffMapper.updateFacProductWriteoff(productWriteoff);
    }

    /**
     * 删除核销记录对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteProductWriteoffByIds(String ids) {
        return productWriteoffMapper.deleteFacProductWriteoffByIds(Convert.toStrArray(ids));
    }

}
