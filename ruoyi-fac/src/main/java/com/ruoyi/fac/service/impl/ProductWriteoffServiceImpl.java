package com.ruoyi.fac.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fac.mapper.ProductWriteoffMapper;
import com.ruoyi.fac.domain.ProductWriteoff;
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
    private ProductWriteoffMapper productWriteoffMapper;

    /**
     * 查询核销记录信息
     *
     * @param id 核销记录ID
     * @return 核销记录信息
     */
    @Override
    public ProductWriteoff selectProductWriteoffById(Integer id) {
        return productWriteoffMapper.selectProductWriteoffById(id);
    }

    /**
     * 查询核销记录列表
     *
     * @param productWriteoff 核销记录信息
     * @return 核销记录集合
     */
    @Override
    public List<ProductWriteoff> selectProductWriteoffList(ProductWriteoff productWriteoff) {
        productWriteoff.setIsDeleted(0);
        return productWriteoffMapper.selectProductWriteoffList(productWriteoff);
    }

    /**
     * 新增核销记录
     *
     * @param productWriteoff 核销记录信息
     * @return 结果
     */
    @Override
    public int insertProductWriteoff(ProductWriteoff productWriteoff) {
        return productWriteoffMapper.insertProductWriteoff(productWriteoff);
    }

    /**
     * 修改核销记录
     *
     * @param productWriteoff 核销记录信息
     * @return 结果
     */
    @Override
    public int updateProductWriteoff(ProductWriteoff productWriteoff) {
        return productWriteoffMapper.updateProductWriteoff(productWriteoff);
    }

    /**
     * 删除核销记录对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteProductWriteoffByIds(String ids) {
        return productWriteoffMapper.deleteProductWriteoffByIds(Convert.toStrArray(ids));
    }

}
