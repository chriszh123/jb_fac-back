package com.ruoyi.fac.mapper;

import com.ruoyi.fac.domain.ProductWriteoff;

import java.util.List;

/**
 * 核销记录 数据层
 *
 * @author ruoyi
 * @date 2019-01-06
 */
public interface ProductWriteoffMapper {
    /**
     * 查询核销记录信息
     *
     * @param id 核销记录ID
     * @return 核销记录信息
     */
    ProductWriteoff selectProductWriteoffById(Integer id);

    /**
     * 查询核销记录列表
     *
     * @param productWriteoff 核销记录信息
     * @return 核销记录集合
     */
    List<ProductWriteoff> selectProductWriteoffList(ProductWriteoff productWriteoff);

    /**
     * 新增核销记录
     *
     * @param productWriteoff 核销记录信息
     * @return 结果
     */
    int insertProductWriteoff(ProductWriteoff productWriteoff);

    /**
     * 修改核销记录
     *
     * @param productWriteoff 核销记录信息
     * @return 结果
     */
    int updateProductWriteoff(ProductWriteoff productWriteoff);

    /**
     * 删除核销记录
     *
     * @param id 核销记录ID
     * @return 结果
     */
    int deleteProductWriteoffById(Integer id);

    /**
     * 批量删除核销记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteProductWriteoffByIds(String[] ids);

}