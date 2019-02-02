package com.ruoyi.fac.mapper;

import com.ruoyi.fac.domain.Order;
import com.ruoyi.fac.domain.Product;
import com.ruoyi.fac.vo.QueryVo;
import com.ruoyi.fac.vo.condition.QueryGoodVo;

import java.util.List;

/**
 * 商品 数据层
 *
 * @author ruoyi
 * @date 2018-12-24
 */
public interface ProductMapper {
    /**
     * 查询商品信息
     *
     * @param id 商品ID
     * @return 商品信息
     */
    Product selectProductById(Long id);

    /**
     * 查询商品列表
     *
     * @param product 商品信息
     * @return 商品集合
     */
    List<Product> selectProductList(Product product);

    /**
     * 新增商品
     *
     * @param product 商品信息
     * @return 结果
     */
    int insertProduct(Product product);

    /**
     * 修改商品
     *
     * @param product 商品信息
     * @return 结果
     */
    int updateProduct(Product product);

    /**
     * 删除商品
     *
     * @param id 商品ID
     * @return 结果
     */
    int deleteProductById(Long id);

    /**
     * 批量删除商品
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteProductByIds(String[] ids);

    /**
     * 查询指定商家下的对应的商品
     *
     * @param bizIds 商家ids
     * @return
     */
    List<Product> selectProductsByBizIds(String[] bizIds);

    int countProducts(QueryVo queryVo);

    List<Product> goodsList(QueryGoodVo vo);

    List<Product> selectProductsByIds(Long[] ids);
}