package com.ruoyi.fac.service;

import com.ruoyi.fac.domain.Product;
import com.ruoyi.fac.vo.ProductImgVo;
import com.ruoyi.fac.vo.client.GoodDetailVo;
import com.ruoyi.fac.vo.client.GoodVo;
import com.ruoyi.fac.vo.client.GoodsPriceVo;
import com.ruoyi.fac.vo.client.LogisticsVo;

import java.util.List;

/**
 * 商品 服务层
 *
 * @author ruoyi
 * @date 2018-12-24
 */
public interface IProductService {
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
     * 删除商品信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteProductByIds(String ids);

    /**
     * 获取当前商品对应图片信息
     *
     * @param product
     * @return
     */
    ProductImgVo getProductImgs(Product product);

    /**
     * 查询指定条件下的商品数据
     *
     * @param categoryId
     * @param nameLike
     * @param page
     * @param pageSize
     * @return
     */
    List<GoodVo> goodsList(String categoryId, String nameLike, Integer page, Integer pageSize);

    /**
     * 商品详情
     *
     * @param id
     */
    GoodDetailVo goodsDetail(String id);

    /**
     * 商品价格
     *
     * @param id
     * @return
     */
    GoodsPriceVo goodPrice(String id);

    /**
     * 删除商品信息
     *
     * @param key 需要删除的图片 : 商品id + "+" + imgPath
     * @return 结果
     */
    int deletePic(String key);
}
