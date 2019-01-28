package com.ruoyi.fac.mapper;

import com.ruoyi.fac.domain.BuyerAddress;
import java.util.List;

/**
 * 买者用户收货地址 数据层
 *
 * @author ruoyi
 * @date 2019-01-28
 */
public interface BuyerAddressMapper {
    /**
     * 查询买者用户收货地址信息
     *
     * @param id 买者用户收货地址ID
     * @return 买者用户收货地址信息
     */
        BuyerAddress selectBuyerAddressById(Long id);

    /**
     * 查询买者用户收货地址列表
     *
     * @param buyerAddress 买者用户收货地址信息
     * @return 买者用户收货地址集合
     */
    List<BuyerAddress> selectBuyerAddressList(BuyerAddress buyerAddress);

    /**
     * 新增买者用户收货地址
     *
     * @param buyerAddress 买者用户收货地址信息
     * @return 结果
     */
    int insertBuyerAddress(BuyerAddress buyerAddress);

    /**
     * 修改买者用户收货地址
     *
     * @param buyerAddress 买者用户收货地址信息
     * @return 结果
     */
    int updateBuyerAddress(BuyerAddress buyerAddress);

    /**
     * 删除买者用户收货地址
     *
     * @param id 买者用户收货地址ID
     * @return 结果
     */
    int deleteBuyerAddressById(Long id);

    /**
     * 批量删除买者用户收货地址
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteBuyerAddressByIds(String[] ids);

}