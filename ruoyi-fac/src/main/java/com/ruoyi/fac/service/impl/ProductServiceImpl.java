package com.ruoyi.fac.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fac.mapper.ProductMapper;
import com.ruoyi.fac.domain.Product;
import com.ruoyi.fac.service.IProductService;
import com.ruoyi.common.support.Convert;
import com.ruoyi.fac.util.TimeUtils;

/**
 * 商品 服务层实现
 *
 * @author ruoyi
 * @date 2018-12-24
 */
@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductMapper productMapper;

    /**
     * 查询商品信息
     *
     * @param id 商品ID
     * @return 商品信息
     */
    @Override
    public Product selectProductById(Long id) {
        Product product = productMapper.selectProductById(id);
        if (product.getRushStart() != null) {
            product.setRushStartStr(TimeUtils.date2Str(product.getRushStart(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM));
        }
        if (product.getRushEnd() != null) {
            product.setRushEndStr(TimeUtils.date2Str(product.getRushEnd(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM));
        }
        if (product.getWriteoffStart() != null) {
            product.setWriteoffStartStr(TimeUtils.date2Str(product.getWriteoffStart(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM));
        }
        if (product.getWriteoffEnd() != null) {
            product.setWriteoffEndStr(TimeUtils.date2Str(product.getWriteoffEnd(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM));
        }

        return product;
    }

    /**
     * 查询商品列表
     *
     * @param product 商品信息
     * @return 商品集合
     */
    @Override
    public List<Product> selectProductList(Product product) {
        product.setIsDeleted(0);
        return productMapper.selectProductList(product);
    }

    /**
     * 新增商品
     *
     * @param product 商品信息
     * @return 结果
     */
    @Override
    public int insertProduct(Product product) {
        return productMapper.insertProduct(product);
    }

    /**
     * 修改商品
     *
     * @param product 商品信息
     * @return 结果
     */
    @Override
    public int updateProduct(Product product) {
        return productMapper.updateProduct(product);
    }

    /**
     * 删除商品对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteProductByIds(String ids) {
        return productMapper.deleteProductByIds(Convert.toStrArray(ids));
    }

}
