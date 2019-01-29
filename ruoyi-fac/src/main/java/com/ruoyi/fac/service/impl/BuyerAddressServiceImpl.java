package com.ruoyi.fac.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fac.util.TimeUtils;
import com.ruoyi.fac.vo.client.ShippingAddress;
import com.ruoyi.system.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fac.mapper.BuyerAddressMapper;
import com.ruoyi.fac.domain.BuyerAddress;
import com.ruoyi.fac.service.IBuyerAddressService;
import com.ruoyi.common.support.Convert;

/**
 * 买者用户收货地址 服务层实现
 *
 * @author ruoyi
 * @date 2019-01-28
 */
@Service
public class BuyerAddressServiceImpl implements IBuyerAddressService {
    @Autowired
    private BuyerAddressMapper buyerAddressMapper;

    /**
     * 查询买者用户收货地址信息
     *
     * @param id 买者用户收货地址ID
     * @return 买者用户收货地址信息
     */
    @Override
    public BuyerAddress selectBuyerAddressById(Long id) {
        return buyerAddressMapper.selectBuyerAddressById(id);
    }

    /**
     * 查询买者用户收货地址列表
     *
     * @param buyerAddress 买者用户收货地址信息
     * @return 买者用户收货地址集合
     */
    @Override
    public List<BuyerAddress> selectBuyerAddressList(BuyerAddress buyerAddress) {
        buyerAddress.setIsDeleted(0);
        return buyerAddressMapper.selectBuyerAddressList(buyerAddress);
    }

    /**
     * 新增买者用户收货地址
     *
     * @param shippingAddress 买者用户收货地址信息
     * @return 结果
     */
    @Override
    public int insertBuyerAddress(ShippingAddress shippingAddress, SysUser user) {
        BuyerAddress buyerAddress = this.convertBuyerAddress(shippingAddress, user);
        return buyerAddressMapper.insertBuyerAddress(buyerAddress);
    }

    /**
     * 修改买者用户收货地址
     *
     * @param buyerAddress 买者用户收货地址信息
     * @return 结果
     */
    @Override
    public int updateBuyerAddress(BuyerAddress buyerAddress) {
        return buyerAddressMapper.updateBuyerAddress(buyerAddress);
    }

    /**
     * 删除买者用户收货地址对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBuyerAddressByIds(String ids) {
        return buyerAddressMapper.deleteBuyerAddressByIds(Convert.toStrArray(ids));
    }

    /**
     * 指定用户对应默认的收获地址
     *
     * @param token
     * @return
     */
    @Override
    public ShippingAddress getDefaultAddress(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        BuyerAddress address = this.buyerAddressMapper.getDefaultAddress(token);
        if (address == null) {
            return null;
        }
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setToken(address.getToken());
        shippingAddress.setAddress(address.getAddress());
        shippingAddress.setProvinceId(address.getProvinceId());
        shippingAddress.setProvinceStr(address.getProvinceStr());
        shippingAddress.setCityId(address.getCityId());
        shippingAddress.setCityStr(address.getCityStr());
        shippingAddress.setDistrictId(address.getDistrictId());
        shippingAddress.setAreaStr(address.getDistrictStr());
        shippingAddress.setCode(address.getCode());
        shippingAddress.setDateAdd(TimeUtils.date2Str(address.getCreateTime(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM_SS));
        if (address.getUpdateTime() != null) {
            shippingAddress.setDateUpdate(TimeUtils.date2Str(address.getUpdateTime(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM_SS));
        }
        shippingAddress.setId(address.getId());
        shippingAddress.setDefault(true);
        shippingAddress.setLinkMan(address.getLinkMan());
        shippingAddress.setMobile(address.getPhoneNumber());
        shippingAddress.setUserId(address.getOperatorId());

        return shippingAddress;
    }

    private BuyerAddress convertBuyerAddress(ShippingAddress shippingAddress, SysUser user) {
        BuyerAddress buyerAddress = new BuyerAddress();
        Date nowDate = new Date();


        return buyerAddress;
    }

}
