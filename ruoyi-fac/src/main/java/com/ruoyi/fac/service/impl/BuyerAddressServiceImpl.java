package com.ruoyi.fac.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fac.domain.Buyer;
import com.ruoyi.fac.mapper.BuyerMapper;
import com.ruoyi.fac.util.TimeUtils;
import com.ruoyi.fac.vo.client.ShippingAddress;
import com.ruoyi.system.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fac.mapper.BuyerAddressMapper;
import com.ruoyi.fac.domain.BuyerAddress;
import com.ruoyi.fac.service.IBuyerAddressService;
import com.ruoyi.common.support.Convert;
import org.springframework.util.CollectionUtils;

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
    @Autowired
    private BuyerMapper buyerMapper;

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
        shippingAddress.setProvinceId(Long.valueOf(address.getProvinceId()));
        shippingAddress.setProvinceStr(address.getProvinceStr());
        shippingAddress.setCityId(Long.valueOf(address.getCityId()));
        shippingAddress.setCityStr(address.getCityStr());
        shippingAddress.setDistrictId(Long.valueOf(address.getDistrictId()));
        shippingAddress.setAreaStr(address.getDistrictStr());
        shippingAddress.setCode(address.getCode());
        shippingAddress.setDateAdd(TimeUtils.date2Str(address.getCreateTime(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM_SS));
        if (address.getUpdateTime() != null) {
            shippingAddress.setDateUpdate(TimeUtils.date2Str(address.getUpdateTime(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM_SS));
        }
        shippingAddress.setId(address.getId());
        shippingAddress.setIsDefault("true");
        shippingAddress.setLinkMan(address.getLinkMan());
        shippingAddress.setMobile(address.getPhoneNumber());
        shippingAddress.setUserId(address.getOperatorId());

        return shippingAddress;
    }

    @Override
    public List<ShippingAddress> shippingAddressList(String token) {
        List<BuyerAddress> buyerAddresses = this.buyerAddressMapper.selectBuyerAddressByTokenId(token);
        if (CollectionUtils.isEmpty(buyerAddresses)) {
            return null;
        }
        List<ShippingAddress> shippingAddresses = new ArrayList<>();
        for (BuyerAddress item : buyerAddresses) {
            ShippingAddress shippingAddress = new ShippingAddress();
            shippingAddresses.add(shippingAddress);
            shippingAddress.setAddress(item.getAddress());
            shippingAddress.setAreaStr(item.getDistrictStr());
            shippingAddress.setCityId(Long.valueOf(item.getCityId()));
            shippingAddress.setCityStr(item.getCityStr());
            shippingAddress.setCode(item.getCode());
            shippingAddress.setDateAdd(TimeUtils.date2Str(item.getCreateTime(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM_SS));
            if (item.getUpdateTime() != null) {
                shippingAddress.setDateUpdate(TimeUtils.date2Str(item.getUpdateTime(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM_SS));
            }
            shippingAddress.setDistrictId(Long.valueOf(item.getDistrictId()));
            shippingAddress.setId(item.getId());
            shippingAddress.setIsDefault(item.getIsDefault().intValue() == 1 ? "true" : "false");
            shippingAddress.setLinkMan(item.getLinkMan());
            shippingAddress.setMobile(item.getPhoneNumber());
            shippingAddress.setProvinceId(Long.valueOf(item.getProvinceId()));
            shippingAddress.setProvinceStr(item.getProvinceStr());
            shippingAddress.setUserId(item.getBuyerId());
        }

        return shippingAddresses;
    }

    @Override
    public ShippingAddress detailShippingAddress(String token, String id) {
        BuyerAddress buyerAddress = this.buyerAddressMapper.selectBuyerAddressByTokenAndId(token, Long.valueOf(id));
        if (buyerAddress == null) {
            return null;
        }
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setAddress(buyerAddress.getAddress());
        shippingAddress.setAreaStr(buyerAddress.getDistrictStr());
        shippingAddress.setCityId(Long.valueOf(buyerAddress.getCityId()));
        shippingAddress.setCityStr(buyerAddress.getCityStr());
        shippingAddress.setCode(buyerAddress.getCode());
        shippingAddress.setDateAdd(TimeUtils.date2Str(buyerAddress.getCreateTime(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM_SS));
        if (buyerAddress.getUpdateTime() != null) {
            shippingAddress.setDateUpdate(TimeUtils.date2Str(buyerAddress.getUpdateTime(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM_SS));
        }
        shippingAddress.setDistrictId(Long.valueOf(buyerAddress.getDistrictId()));
        shippingAddress.setId(buyerAddress.getId());
        shippingAddress.setIsDefault(buyerAddress.getIsDefault().intValue() == 1 ? "true" : "false");
        shippingAddress.setLinkMan(buyerAddress.getLinkMan());
        shippingAddress.setMobile(buyerAddress.getPhoneNumber());
        shippingAddress.setProvinceId(Long.valueOf(buyerAddress.getProvinceId()));
        shippingAddress.setProvinceStr(buyerAddress.getProvinceStr());
        shippingAddress.setUserId(buyerAddress.getBuyerId());

        return shippingAddress;
    }

    @Override
    public void updateAddress(ShippingAddress shippingAddress) {
        BuyerAddress buyerAddress = new BuyerAddress();
        buyerAddress.setId(shippingAddress.getId());
        buyerAddress.setProvinceId(shippingAddress.getProvinceId().intValue());
        buyerAddress.setProvinceStr(shippingAddress.getProvinceStr());
        buyerAddress.setCityId(shippingAddress.getCityId().intValue());
        buyerAddress.setCityStr(shippingAddress.getCityStr());
        buyerAddress.setDistrictId(shippingAddress.getDistrictId().intValue());
        buyerAddress.setDistrictStr(shippingAddress.getAreaStr());
        buyerAddress.setAddress(shippingAddress.getAddress());
        buyerAddress.setLinkMan(shippingAddress.getLinkMan());
        buyerAddress.setPhoneNumber(shippingAddress.getMobile());
        buyerAddress.setCode(shippingAddress.getCode());
        buyerAddress.setUpdateTime(new Date());
        buyerAddress.setIsDefault(StringUtils.equals("true", shippingAddress.getIsDefault()) ? 1 : 0);

        this.buyerAddressMapper.updateBuyerAddress(buyerAddress);
    }

    @Override
    public void deleteAddress(String token, String id) {
        BuyerAddress buyerAddress = new BuyerAddress();
        buyerAddress.setId(Long.valueOf(id));
        buyerAddress.setUpdateTime(new Date());
        buyerAddress.setIsDeleted(1);

        this.buyerAddressMapper.updateBuyerAddress(buyerAddress);
    }

    @Override
    public Long addAddress(ShippingAddress shippingAddress) {
        Buyer buyer = this.buyerMapper.selectBuyerByOpenId(shippingAddress.getToken());
        if (buyer == null) {
            return -1L;
        }
        Date nowDate = new Date();
        BuyerAddress buyerAddress = new BuyerAddress();
        buyerAddress.setBuyerId(buyer.getId());
        buyerAddress.setToken(shippingAddress.getToken());
        buyerAddress.setOpenId(shippingAddress.getToken());

        buyerAddress.setProvinceId(shippingAddress.getProvinceId().intValue());
        buyerAddress.setProvinceStr(shippingAddress.getProvinceStr());
        buyerAddress.setCityId(shippingAddress.getCityId().intValue());
        buyerAddress.setCityStr(shippingAddress.getCityStr());
        buyerAddress.setDistrictId(shippingAddress.getDistrictId().intValue());
        buyerAddress.setDistrictStr(shippingAddress.getAreaStr());
        buyerAddress.setAddress(shippingAddress.getAddress());
        buyerAddress.setLinkMan(shippingAddress.getLinkMan());
        buyerAddress.setPhoneNumber(shippingAddress.getMobile());
        buyerAddress.setCode(shippingAddress.getCode());
        buyerAddress.setCreateTime(nowDate);
        buyerAddress.setUpdateTime(nowDate);
        buyerAddress.setIsDefault(StringUtils.equals("true", shippingAddress.getIsDefault()) ? 1 : 0);
        buyerAddress.setIsDeleted(0);
        this.buyerAddressMapper.insertBuyerAddress(buyerAddress);

        return buyerAddress.getId();
    }

    private BuyerAddress convertBuyerAddress(ShippingAddress shippingAddress, SysUser user) {
        BuyerAddress buyerAddress = new BuyerAddress();
        Date nowDate = new Date();


        return buyerAddress;
    }

}