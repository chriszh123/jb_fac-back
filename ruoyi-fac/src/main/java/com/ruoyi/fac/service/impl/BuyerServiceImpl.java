package com.ruoyi.fac.service.impl;

import com.ruoyi.common.support.Convert;
import com.ruoyi.fac.constant.FacConstant;
import com.ruoyi.fac.domain.Business;
import com.ruoyi.fac.domain.Buyer;
import com.ruoyi.fac.domain.BuyerBusiness;
import com.ruoyi.fac.domain.Product;
import com.ruoyi.fac.mapper.BusinessMapper;
import com.ruoyi.fac.mapper.BuyerBusinessMapper;
import com.ruoyi.fac.mapper.BuyerMapper;
import com.ruoyi.fac.mapper.ProductMapper;
import com.ruoyi.fac.service.IBuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 买者用户 服务层实现
 *
 * @author ruoyi
 * @date 2018-12-24
 */
@Service
public class BuyerServiceImpl implements IBuyerService {
    @Autowired
    private BuyerMapper buyerMapper;

    @Autowired
    private BusinessMapper businessMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private BuyerBusinessMapper buyerBusinessMapper;

    /**
     * 查询买者用户信息
     *
     * @param id 买者用户ID
     * @return 买者用户信息
     */
    @Override
    public Buyer selectBuyerById(Long id) {
        return buyerMapper.selectBuyerById(id);
    }

    /**
     * 查询买者用户列表
     *
     * @param buyer 买者用户信息
     * @return 买者用户集合
     */
    @Override
    public List<Buyer> selectBuyerList(Buyer buyer) {
        buyer.setIsDeleted(0);
        return buyerMapper.selectBuyerList(buyer);
    }

    /**
     * 新增买者用户
     *
     * @param buyer 买者用户信息
     * @return 结果
     */
    @Override
    public int insertBuyer(Buyer buyer) {
        return buyerMapper.insertBuyer(buyer);
    }

    /**
     * 修改买者用户
     *
     * @param buyer 买者用户信息
     * @return 结果
     */
    @Override
    public int updateBuyer(Buyer buyer) {
        // 删除用户与商家商品关联表
        BuyerBusiness buyerBusiness = new BuyerBusiness();
        buyerBusiness.setUserId(buyer.getId());
        this.buyerBusinessMapper.deleteBuyerBusinessByUserId(buyerBusiness);
        // 批量插入新的用户与商家商品关联关系
        if (!ObjectUtils.isEmpty(buyer.getProdIds())) {
            List<BuyerBusiness> list = new ArrayList<>();
            Date now = new Date();
            String prodRef = null;
            String[] prodRefArr = null;
            for (int i = 0, size = buyer.getProdIds().length; i < size; i++) {
                buyerBusiness = new BuyerBusiness();
                // nodeType-id-pId
                prodRef = buyer.getProdIds()[i];
                prodRefArr = prodRef.split("-");
                if (prodRefArr.length < 3 || !FacConstant.NODE_FIELD_TYPE_PROD.equals(prodRefArr[0])) {
                    continue;
                }
                buyerBusiness.setUserId(buyer.getId());
                buyerBusiness.setBusinessId(Long.valueOf(prodRefArr[2]));
                buyerBusiness.setBusinessProdId(Long.valueOf(prodRefArr[1]));
                buyerBusiness.setCreateTime(now);
                buyerBusiness.setUpdateTime(now);
                buyerBusiness.setOperatorId(buyer.getOperatorId());
                buyerBusiness.setOperatorName(buyer.getOperatorName());
                buyerBusiness.setIsDeleted(0);
                list.add(buyerBusiness);
            }
            return this.buyerBusinessMapper.batchInsert(list);
        }
        return 0;
    }

    /**
     * 删除买者用户对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBuyerByIds(String ids) {
        return buyerMapper.deleteBuyerByIds(Convert.toStrArray(ids));
    }

    /**
     * 获取用户-商家商品数数据
     *
     * @param buyer
     * @return
     */
    @Override
    public List<Map<String, Object>> bizProdTreeData(Buyer buyer) {
        List<Map<String, Object>> data = new ArrayList<>();
        Business business = new Business();
        business.setIsDeleted(0);
        List<Business> businesses = this.businessMapper.selectBusinessList(business);
        if (CollectionUtils.isEmpty(businesses)) {
            return data;
        }
        Map<String, Object> node = null;
        // 商家
        List<String> bizIds = new ArrayList<>();
        for (Business business1 : businesses) {
            node = new HashMap<>(16);
            node.put("id", business1.getId().toString());
            node.put("pId", "0");
            node.put("name", business1.getName());
            node.put("title", business1.getName());
            node.put("type", FacConstant.NODE_FIELD_TYPE_BIZ);
            node.put("checked", false);
            data.add(node);
            bizIds.add(business1.getId().toString());
        }
        // 商家对应商品
        String[] bizIdsArr = new String[bizIds.size()];
        bizIds.toArray(bizIdsArr);
        List<Product> products = this.productMapper.selectProducsByBizIds(bizIdsArr);
        if (CollectionUtils.isEmpty(products)) {
            return data;
        }
        // <bizId, List<Product>>
        Map<String, List<Product>> biz2Prods = new HashMap<>(16);
        for (Product product : products) {
            if (!biz2Prods.containsKey(product.getBusinessId().toString())) {
                biz2Prods.put(product.getBusinessId().toString(), new ArrayList<>());
            }
            biz2Prods.get(product.getBusinessId().toString()).add(product);
        }
        // 当前用户购买的商品
        BuyerBusiness buyerBusiness = new BuyerBusiness();
        buyerBusiness.setUserId(buyer.getId());
        buyerBusiness.setIsDeleted(0);
        List<BuyerBusiness> buyerBusinesses = this.buyerBusinessMapper.selectBuyerBusinessList(buyerBusiness);
        for (Map.Entry<String, List<Product>> entry : biz2Prods.entrySet()) {
            for (Product product : entry.getValue()) {
                node = new HashMap<>(16);
                // 商品的Id值暂时用 "商家id + 商品ID"拼起来的值，防止给前端的树id重复
                String prodId = entry.getKey() + product.getId().toString();
                node.put("id", prodId);
                node.put("pId", entry.getKey());
                node.put("name", product.getName());
                node.put("title", product.getName());
                node.put("type", FacConstant.NODE_FIELD_TYPE_PROD);
                // 当前商品是否已购买
                node.put("checked", this.checkProdBuyed(product.getId().toString(), buyerBusinesses));
                data.add(node);
            }
        }

        return data;
    }

    private boolean checkProdBuyed(String prodId, List<BuyerBusiness> buyerBusinesses) {
        if (StringUtils.isEmpty(prodId) || CollectionUtils.isEmpty(buyerBusinesses)) {
            return false;
        }
        for (BuyerBusiness buyerBusiness : buyerBusinesses) {
            if (prodId.equals(buyerBusiness.getBusinessProdId().toString())) {
                return true;
            }
        }
        return false;
    }

}
