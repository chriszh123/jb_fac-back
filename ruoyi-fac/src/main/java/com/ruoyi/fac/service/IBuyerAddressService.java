package com.ruoyi.fac.service;

import com.ruoyi.fac.domain.BuyerAddress;
import com.ruoyi.fac.vo.client.ShippingAddress;
import com.ruoyi.system.domain.SysUser;

import java.util.List;

/**
 * 买者用户收货地址 服务层
 *
 * @author ruoyi
 * @date 2019-01-28
 */
public interface IBuyerAddressService {
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
     * @param shippingAddress 买者用户收货地址信息
     * @return 结果
     */
    int insertBuyerAddress(ShippingAddress shippingAddress, SysUser user);

    /**
     * 修改买者用户收货地址
     *
     * @param buyerAddress 买者用户收货地址信息
     * @return 结果
     */
    int updateBuyerAddress(BuyerAddress buyerAddress);

    /**
     * 删除买者用户收货地址信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteBuyerAddressByIds(String ids);

    /**
     * 指定用户对应默认的收获地址
     *
     * @param token
     * @return
     */
    ShippingAddress getDefaultAddress(String token);

    List<ShippingAddress> shippingAddressList(String token);

    ShippingAddress detailShippingAddress(String token, String id);

    void updateAddress(ShippingAddress shippingAddress);

    void deleteAddress(String token, String id);
}
