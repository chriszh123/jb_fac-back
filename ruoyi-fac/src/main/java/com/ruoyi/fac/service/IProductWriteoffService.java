package com.ruoyi.fac.service;

import com.ruoyi.fac.domain.FacProductWriteoff;
import com.ruoyi.fac.model.FacProductWriteOffBean;

import java.util.List;

/**
 * 核销记录 服务层
 *
 * @author ruoyi
 * @date 2018-12-24
 */
public interface IProductWriteoffService {
    /**
     * 查询核销记录信息
     *
     * @param id 核销记录ID
     * @return 核销记录信息
     */
    FacProductWriteoff selectProductWriteoffById(Integer id);

    /**
     * 查询核销记录列表
     *
     * @param productWriteoff 核销记录信息
     * @return 核销记录集合
     */
    List<FacProductWriteOffBean> selectProductWriteoffList(FacProductWriteoff productWriteoff);

    /**
     * 新增核销记录
     *
     * @param productWriteoff 核销记录信息
     * @return 结果
     */
    int insertProductWriteoff(FacProductWriteoff productWriteoff);

    /**
     * 修改核销记录
     *
     * @param productWriteoff 核销记录信息
     * @return 结果
     */
    int updateProductWriteoff(FacProductWriteoff productWriteoff);

    /**
     * 删除核销记录信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteProductWriteoffByIds(String ids);

}
