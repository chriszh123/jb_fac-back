package com.ruoyi.fac.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fac.mapper.BuyerBusinessMapper;
import com.ruoyi.fac.domain.BuyerBusiness;
import com.ruoyi.fac.service.IBuyerBusinessService;
import com.ruoyi.common.support.Convert;

/**
 * 买者与商家商品关联 服务层实现
 *
 * @author ruoyi
 * @date 2018-12-24
 */
@Service
public class BuyerBusinessServiceImpl implements IBuyerBusinessService {
    @Autowired
    private BuyerBusinessMapper buyerBusinessMapper;

    /**
     * 查询买者与商家商品关联信息
     *
     * @param id 买者与商家商品关联ID
     * @return 买者与商家商品关联信息
     */
    @Override
    public BuyerBusiness selectBuyerBusinessById(Integer id) {
        return buyerBusinessMapper.selectBuyerBusinessById(id);
    }

    /**
     * 查询买者与商家商品关联列表
     *
     * @param buyerBusiness 买者与商家商品关联信息
     * @return 买者与商家商品关联集合
     */
    @Override
    public List<BuyerBusiness> selectBuyerBusinessList(BuyerBusiness buyerBusiness) {
        buyerBusiness.setIsDeleted(0);
        return buyerBusinessMapper.selectBuyerBusinessList(buyerBusiness);
    }

    /**
     * 新增买者与商家商品关联
     *
     * @param buyerBusiness 买者与商家商品关联信息
     * @return 结果
     */
    @Override
    public int insertBuyerBusiness(BuyerBusiness buyerBusiness) {
        return buyerBusinessMapper.insertBuyerBusiness(buyerBusiness);
    }

    /**
     * 修改买者与商家商品关联
     *
     * @param buyerBusiness 买者与商家商品关联信息
     * @return 结果
     */
    @Override
    public int updateBuyerBusiness(BuyerBusiness buyerBusiness) {
        return buyerBusinessMapper.updateBuyerBusiness(buyerBusiness);
    }

    /**
     * 删除买者与商家商品关联对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBuyerBusinessByIds(String ids) {
        return buyerBusinessMapper.deleteBuyerBusinessByIds(Convert.toStrArray(ids));
    }

}
