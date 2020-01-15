package com.ruoyi.fac.service.impl;

import com.ruoyi.common.support.Convert;
import com.ruoyi.fac.cache.BuyerCache;
import com.ruoyi.fac.constant.FacConstant;
import com.ruoyi.fac.domain.Business;
import com.ruoyi.fac.domain.Buyer;
import com.ruoyi.fac.domain.BuyerBusiness;
import com.ruoyi.fac.domain.Product;
import com.ruoyi.fac.enums.OrderStatus;
import com.ruoyi.fac.mapper.*;
import com.ruoyi.fac.model.*;
import com.ruoyi.fac.service.IBuyerService;
import com.ruoyi.fac.util.DecimalUtils;
import com.ruoyi.fac.util.TimeUtils;
import com.ruoyi.fac.vo.QueryVo;
import com.ruoyi.fac.vo.UserDiagramVo;
import com.ruoyi.fac.vo.client.UserAmountVo;
import com.ruoyi.fac.vo.client.UserBaseVo;
import com.ruoyi.fac.vo.client.UserDetailVo;
import com.ruoyi.fac.vo.client.req.QuestionReq;
import com.ruoyi.fac.vo.client.req.UserInfo;
import com.ruoyi.system.domain.SysUser;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 买者用户 服务层实现
 *
 * @author ruoyi
 * @date 2018-12-24
 */
@Slf4j
@Service("buyerService")
public class BuyerServiceImpl implements IBuyerService {
    @Autowired
    private BuyerMapper buyerMapper;
    @Autowired
    private BusinessMapper businessMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private BuyerBusinessMapper buyerBusinessMapper;
    @Autowired
    private FacOrderMapper facOrderMapper;
    @Autowired
    private FacBuyerAddressMapper facBuyerAddressMapper;
    @Autowired
    private FacOrderProductMapper facOrderProductMapper;
    @Autowired
    private FacBuyerAddressMapper addressMapper;
    @Autowired
    private FacLeaveMessageMapper leaveMessageMapper;

    @Autowired
    private BuyerCache buyerCache;

    /**
     * 查询买者用户信息
     *
     * @param id 买者用户ID
     * @return 买者用户信息
     */
    @Override
    public Buyer selectBuyerById(Long id) {
        Buyer buyer = buyerMapper.selectBuyerById(id);
        if (StringUtils.equals("1", buyer.getGender())) {
            buyer.setGender("男");
        } else if (StringUtils.equals("2", buyer.getGender())) {
            buyer.setGender("女");
        } else {
            buyer.setGender("其它");
        }
        return buyer;
    }

    /**
     * 查询买者用户列表
     *
     * @param buyer 买者用户信息
     * @return 买者用户集合
     */
    @Override
    public List<Buyer> selectBuyerList(Buyer buyer) {
        buyer.setIsDeleted(0);
        List<Buyer> buyers = buyerMapper.selectBuyerList(buyer);
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(buyers)) {
            List<String> tokens = new ArrayList<>();
            for (Buyer item : buyers) {
                if (StringUtils.isBlank(item.getToken())) {
                    continue;
                }
                tokens.add(item.getToken());
            }
            FacBuyerAddressExample example = new FacBuyerAddressExample();
            example.createCriteria().andIsDeletedEqualTo(false).andTokenIn(tokens);
            List<FacBuyerAddress> addresses = this.facBuyerAddressMapper.selectByExample(example);
            if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(addresses)) {
                Map<String, FacBuyerAddress> token2Phone = new HashMap<>(addresses.size());
                for (FacBuyerAddress item : addresses) {
                    if (item.getIsDefault()) {
                        token2Phone.put(item.getToken(), item);
                    }
                }

                for (Buyer item : buyers) {
                    if (MapUtils.isNotEmpty(token2Phone) && token2Phone.containsKey(item.getToken())) {
                        item.setPhoneNumber(token2Phone.get(item.getToken()).getPhoneNumber());
                        item.setName(token2Phone.get(item.getToken()).getLinkman());
                    }
                }
            }
        }

        return buyers;
    }

    /**
     * 新增买者用户
     *
     * @param buyer 买者用户信息
     * @return 结果
     */
    @Override
    public int insertBuyer(Buyer buyer) {
        return buyerMapper.insertBuyer(buyer);
    }

    /**
     * 修改买者用户
     *
     * @param buyer 买者用户信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBuyer(Buyer buyer) {
        // 删除用户与商家商品关联表
        Buyer buyerDB = this.buyerMapper.selectBuyerById(buyer.getId());
        if (buyerDB == null) {
            return 0;
        }
        BuyerBusiness buyerBusiness = new BuyerBusiness();
        buyerBusiness.setUserId(buyer.getId());
        this.buyerBusinessMapper.deleteBuyerBusinessByUserId(buyerBusiness);
        // 批量插入新的用户与商家商品关联关系
        if (!ObjectUtils.isEmpty(buyer.getProdIds())) {
            List<BuyerBusiness> list = new ArrayList<>();
            Date now = new Date();
            String prodRef = null, prodId = null;
            String[] prodRefArr = null;
            for (int i = 0, size = buyer.getProdIds().length; i < size; i++) {
                buyerBusiness = new BuyerBusiness();
                // nodeType-id-pId,商品id："商家id + 商品ID"
                prodRef = buyer.getProdIds()[i];
                prodRefArr = prodRef.split("-");
                if (prodRefArr.length < 3 || !FacConstant.NODE_FIELD_TYPE_PROD.equals(prodRefArr[0])) {
                    continue;
                }
                buyerBusiness.setUserId(buyer.getId());
                buyerBusiness.setBusinessId(Long.valueOf(prodRefArr[2]));
                buyerBusiness.setToken(buyerDB.getToken());
                prodId = prodRefArr[1].substring(prodRefArr[2].length());
                buyerBusiness.setBusinessProdId(Long.valueOf(prodId));
                buyerBusiness.setCreateTime(now);
                buyerBusiness.setUpdateTime(now);
                buyerBusiness.setOperatorId(buyer.getOperatorId());
                buyerBusiness.setOperatorName(buyer.getOperatorName());
                buyerBusiness.setIsDeleted(0);
                list.add(buyerBusiness);
            }
            if (!CollectionUtils.isEmpty(list)) {
                return this.buyerBusinessMapper.batchInsert(list);
            }
        }

        // 删除相应人员缓存信息
        this.buyerCache.deleteBuyerCache(buyer.getId());
        this.buyerCache.deleteBuyerCache(buyer.getToken());

        return 1;
    }

    /**
     * 删除买者用户对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBuyerByIds(String ids) {
        return buyerMapper.deleteBuyerByIds(Convert.toStrArray(ids));
    }

    /**
     * 获取用户-商家商品数数据
     *
     * @param buyer
     * @return
     */
    @Override
    public List<Map<String, Object>> bizProdTreeData(Buyer buyer) {
        if (buyer == null || buyer.getId() == null) {
            return null;
        }
        List<Map<String, Object>> data = new ArrayList<>();
        Business business = new Business();
        business.setIsDeleted(0);
        List<Business> businesses = this.businessMapper.selectBusinessList(business);
        if (CollectionUtils.isEmpty(businesses)) {
            return data;
        }
        Map<String, Object> node = null;
        // 商家
        List<String> bizIds = new ArrayList<>();
        for (Business business1 : businesses) {
            node = new HashMap<>(16);
            node.put("id", business1.getId().toString());
            node.put("pId", "0");
            node.put("name", business1.getName());
            node.put("title", business1.getName());
            node.put("type", FacConstant.NODE_FIELD_TYPE_BIZ);
            node.put("checked", false);
            data.add(node);
            bizIds.add(business1.getId().toString());
        }
        // 商家对应商品
        String[] bizIdsArr = new String[bizIds.size()];
        bizIds.toArray(bizIdsArr);
        List<Product> products = this.productMapper.selectProductsByBizIds(bizIdsArr);
        if (CollectionUtils.isEmpty(products)) {
            return data;
        }
        // <bizId, List<Product>>
        Map<String, List<Product>> biz2Prods = new HashMap<>(16);
        for (Product product : products) {
            if (!biz2Prods.containsKey(product.getBusinessId().toString())) {
                biz2Prods.put(product.getBusinessId().toString(), new ArrayList<>());
            }
            biz2Prods.get(product.getBusinessId().toString()).add(product);
        }
        // 当前用户购买的商品
        BuyerBusiness buyerBusiness = new BuyerBusiness();
        buyerBusiness.setUserId(buyer.getId());
        buyerBusiness.setIsDeleted(0);
        List<BuyerBusiness> buyerBusinesses = this.buyerBusinessMapper.selectBuyerBusinessList(buyerBusiness);
        for (Map.Entry<String, List<Product>> entry : biz2Prods.entrySet()) {
            for (Product product : entry.getValue()) {
                node = new HashMap<>(16);
                // 商品的Id值暂时用 "商家id + 商品ID"拼起来的值，防止给前端的树id重复
                String prodId = entry.getKey() + product.getId().toString();
                node.put("id", prodId);
                node.put("pId", entry.getKey());
                node.put("name", product.getName());
                node.put("title", product.getName());
                node.put("type", FacConstant.NODE_FIELD_TYPE_PROD);
                // 当前商品是否已购买
                node.put("checked", this.checkProdBuyed(product.getId().toString(), buyerBusinesses));
                data.add(node);
            }
        }

        return data;
    }

    /**
     * 查询指定日期内每日的新增人数
     *
     * @param startDateStr
     * @param endDateStr
     * @return
     */
    @Override
    public UserDiagramVo queryRecentUserInfo(String startDateStr, String endDateStr) {
        UserDiagramVo vo = new UserDiagramVo();
        Date startDate = null, endDate = null;
        try {
            if (StringUtils.isEmpty(startDateStr) || StringUtils.isEmpty(endDateStr)) {
                // 最近一周日期: 2019-01-04, end = 2019-01-11
                endDate = new Date();
                startDate = TimeUtils.getDateByHours(endDate, -168);
            } else {
                startDate = TimeUtils.parseTime(startDateStr, TimeUtils.DEFAULT_DATE_FORMAT);
                endDate = TimeUtils.parseTime(endDateStr, TimeUtils.DEFAULT_DATE_FORMAT);
            }
            if (startDate == null || endDate == null) {
                return vo;
            }
            List<Date> datesList = TimeUtils.getStaticDates(startDate, endDate);
            String[] xAxisData = new String[datesList.size()];
            String[] seriesData = new String[datesList.size()];
            for (int i = 0, size = datesList.size(); i < size; i++) {
                xAxisData[i] = TimeUtils.date2Str(datesList.get(i), "");
                seriesData[i] = "0";
            }
            vo.setxAxisData(xAxisData);
            vo.setSeriesUserData(seriesData);

            QueryVo queryVo = new QueryVo(startDate, endDate);
            List<Buyer> buyers = this.buyerMapper.queryRecentUserInfo(queryVo);
            if (CollectionUtils.isEmpty(buyers)) {
                return vo;
            }
            Map<Date, Integer> date2Count = new HashMap<>(16);
            Date date;
            int tempCount = 0;
            for (Buyer buyer : buyers) {
                date = TimeUtils.parseTime(buyer.getCreateTime(), TimeUtils.DEFAULT_DATE_FORMAT);
                if (!date2Count.containsKey(date)) {
                    date2Count.put(date, 0);
                }
                tempCount = date2Count.get(date);
                date2Count.put(date, ++tempCount);
            }
            for (int i = 0, size = datesList.size(); i < size; i++) {
                if (date2Count.containsKey(datesList.get(i))) {
                    seriesData[i] = date2Count.get(datesList.get(i)).toString();
                }
            }
            vo.setSeriesUserData(seriesData);
        } catch (Exception ex) {
            log.info("[queryRecentUserInfo] error", ex);
        }

        return vo;
    }

    /**
     * 查询指定token对应的用户
     *
     * @param token
     * @return
     */
    @Override
    public Buyer selectBuyerByToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        return this.buyerMapper.selectBuyerByOpenId(token);
    }

    @Override
    public UserDetailVo detailUser(String token) {
        Buyer buyer = this.buyerMapper.selectBuyerByOpenId(token);
        if (buyer == null) {
            return null;
        }
        UserDetailVo vo = new UserDetailVo();
        UserBaseVo base = new UserBaseVo();
        vo.setBase(base);
        base.setId(buyer.getId());
        base.setCity("");
        base.setProvince("");
        base.setDateAdd(TimeUtils.date2Str(buyer.getCreateTime(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM_SS));
        base.setDateLogin("");
        base.setIpAdd("");
        base.setIpLogin("");
        base.setAvatarUrl("");
        base.setNick("");

        return vo;
    }

    @Override
    public UserAmountVo userAmount(String token) {
        Buyer buyer = this.buyerMapper.selectBuyerByOpenId(token);
        if (buyer == null) {
            return null;
        }
        UserAmountVo vo = new UserAmountVo();
        // 余额
        vo.setBalance(Double.valueOf(buyer.getBalance().toString()));
        // 积分
        vo.setScore(buyer.getPoints());
        // 总消费金额
        List<Byte> statuses = new ArrayList<>();
        statuses.add(OrderStatus.TOWRITEOFF.getCode().byteValue());
        statuses.add(OrderStatus.TOEVALUATE.getCode().byteValue());
        statuses.add(OrderStatus.COMPLETED.getCode().byteValue());
        FacOrderExample example = new FacOrderExample();
        example.createCriteria().andIsDeletedEqualTo(false).andTokenEqualTo(token).andStatusIn(statuses);
        List<FacOrder> orders = this.facOrderMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(orders)) {
            List<String> orderNos = new ArrayList<>();
            int useScore = 0;
            for (FacOrder item : orders) {
                orderNos.add(item.getOrderNo());
                useScore = useScore + item.getUserScore();
            }
            FacOrderProductExample orderProductExample = new FacOrderProductExample();
            orderProductExample.createCriteria().andIsDeletedEqualTo(false).andTokenEqualTo(token).andOrderNoIn(orderNos);
            List<FacOrderProduct> orderProducts = this.facOrderProductMapper.selectByExample(orderProductExample);
            if (!CollectionUtils.isEmpty(orderProducts)) {
                BigDecimal total = new BigDecimal("0.00");
                BigDecimal temp;
                for (FacOrderProduct item : orderProducts) {
                    temp = DecimalUtils.mul(item.getPrice(), new BigDecimal(String.valueOf(item.getProdNumber())));
                    total = total.add(temp);
                }
                // 去掉使用的积分
                if (useScore > 0) {
                    total = DecimalUtils.subtract(total, DecimalUtils.division(useScore, 100));
                }
                vo.setTotleConsumed(Double.valueOf(total.toString()));
            }
        }

        return vo;
    }

    /**
     * 保存微信用户信息
     *
     * @param openId
     * @param code
     * @return
     */
    @Override
    public Long saveBuyer(String openId, String code) {
        Date nowDate = new Date();
        Buyer buyer = this.buyerMapper.selectBuyerByOpenId(openId);
        if (buyer != null) {
            // update
        } else {
            // add
            buyer = new Buyer();
            buyer.setNickName("nickName");
            buyer.setName("name");
            buyer.setToken(openId);
            buyer.setOpenId(openId);
            buyer.setBalance(new BigDecimal("0.00"));
            buyer.setPoints(0);
            buyer.setRegistryTime(nowDate);
            buyer.setCreateTime(nowDate);
            buyer.setUpdateTime(nowDate);
            buyer.setIsDeleted(0);
            this.buyerMapper.insertBuyer(buyer);

            buyer = this.selectBuyerByToken(openId);
        }

        return buyer.getId();
    }

    /**
     * 更新用户信息:昵称、用户微信头像
     *
     * @param userInfo
     * @return 错误信息
     */
    @Override
    public String updateUserInfo(UserInfo userInfo) {
        if (userInfo == null || userInfo.getUid() == null) {
            return "用户uid为空";
        }
        this.buyerMapper.updateUserInfo(userInfo);
        return "";
    }

    /**
     * 指定用户信息
     *
     * @param token
     * @return
     */
    @Override
    public UserBaseVo getUserInfo(String token) {
        UserBaseVo vo = new UserBaseVo();
        // 查询当前用户是不是商家，有没有自己的商品
        BuyerBusiness buyerBusiness = new BuyerBusiness();
        buyerBusiness.setToken(token);
        buyerBusiness.setIsDeleted(0);
        List<BuyerBusiness> buyerBusinesses = this.buyerBusinessMapper.selectBuyerBusinessList(buyerBusiness);
        // 用户类型:0-普通购买用户,1-商家
        vo.setUserType(CollectionUtils.isEmpty(buyerBusinesses) ? 0 : 1);

        return vo;
    }

    @Override
    public int deleteUserAddress(String id, SysUser user) {
        if (StringUtils.isBlank(id)) {
            log.error(String.format("--------------------[deleteUserAddress] id is blank."));
            return -1;
        }
        final Date nowDate = new Date();
        final FacBuyerAddress buyerAddress = new FacBuyerAddress();
        buyerAddress.setIsDeleted(true);
        buyerAddress.setUpdateTime(nowDate);
        if (user != null) {
            buyerAddress.setOperatorId(user.getUserId());
            buyerAddress.setOperatorName(user.getUserName());
        }
        final FacBuyerAddressExample addressExample = new FacBuyerAddressExample();
        addressExample.createCriteria().andIdEqualTo(Long.valueOf(id));
        int rows = this.addressMapper.updateByExampleSelective(buyerAddress, addressExample);

        return rows;
    }

    @Override
    public List<FacBuyerAddress> listBuyerAddresses(FacBuyerAddress address) {
        final FacBuyerAddressExample addressExample = new FacBuyerAddressExample();
        final FacBuyerAddressExample.Criteria criteria = addressExample.or();
        criteria.andIsDeletedEqualTo(false);
        if (address != null) {
            // 用户id
            if (address.getBuyerId() != null) {
                criteria.andBuyerIdEqualTo(address.getBuyerId());
            }
            // 手机号码
            if (StringUtils.isNotBlank(address.getPhoneNumber())) {
                String phoneNumber = "%" + address.getPhoneNumber().trim() + "%";
                criteria.andPhoneNumberLike(phoneNumber);
            }
            // 地址
            if (StringUtils.isNotBlank(address.getAddress())) {
                String addressContent = "%" + address.getAddress().trim() + "%";
                criteria.andAddressLike(addressContent);
            }
        }
        addressExample.setOrderByClause(" create_time desc ");
        final List<FacBuyerAddress> addresses = this.addressMapper.selectByExample(addressExample);

        return addresses;
    }

    @Override
    public int editAddress(FacBuyerAddress address) {
        final Date nowDate = new Date();
        address.setUpdateTime(nowDate);
        address.setIsDefault(address.getIsDefault() != null);

        final FacBuyerAddressExample addressExample = new FacBuyerAddressExample();
        addressExample.createCriteria().andIdEqualTo(address.getId());

        int rows = this.addressMapper.updateByExampleSelective(address, addressExample);
        return rows;
    }

    @Override
    public FacBuyerAddress selectAddress(Long id) {
        final FacBuyerAddressExample addressExample = new FacBuyerAddressExample();
        addressExample.createCriteria().andIdEqualTo(id);
        List<FacBuyerAddress> addresses = this.addressMapper.selectByExample(addressExample);
        if (CollectionUtils.isNotEmpty(addresses)) {
            return addresses.get(0);
        }
        return null;
    }

    @Override
    public List<FacLeaveMessage> listLeaveMessage(QuestionReq req) {
        final FacLeaveMessageExample messageExample = new FacLeaveMessageExample();
        FacLeaveMessageExample.Criteria criteria = messageExample.createCriteria();
        criteria.andIsDeletedEqualTo(false).andTokenEqualTo(req.getToken());
        if (req.getPage() != null && req.getSize() != null) {
            messageExample.setStartRow(req.getPage() * req.getSize());
            messageExample.setPageSize(req.getSize());
        }
        messageExample.setOrderByClause(" create_time desc");
        List<FacLeaveMessage> leaveMessages = this.leaveMessageMapper.selectByExample(messageExample);
        return leaveMessages;
    }

    @Override
    public void addLeaveMessage(FacLeaveMessage vo) {
        final Date nowDate = new Date();
        vo.setCreateTime(nowDate);
        vo.setUpdateTime(nowDate);
        vo.setIsDeleted(false);
        vo.setOperatorId(-1L);
        vo.setOperatorName(vo.getToken());
        vo.setMngtRemark("");

        this.leaveMessageMapper.insert(vo);
    }

    /**
     * 删除用户留言信息：客户端用户自己撤回留言
     *
     * @param token
     * @param id
     */
    @Override
    public void removeLeaveMessage(String token, Long id) {
        final FacLeaveMessage update = new FacLeaveMessage();
        update.setIsDeleted(true);
        update.setUpdateTime(new Date());
        update.setOperatorName(token);
        final FacLeaveMessageExample example = new FacLeaveMessageExample();
        example.createCriteria().andIsDeletedEqualTo(false).andTokenEqualTo(token).andIdEqualTo(id);
        this.leaveMessageMapper.updateByExampleSelective(update, example);
    }

    /**
     *  用户留言信息：列表
     *
     * @param message FacLeaveMessage
     * @return List<FacLeaveMessage>
     */
    @Override
    public List<FacLeaveMessage> selectLeaveMessages(FacLeaveMessage message) {
        final FacLeaveMessageExample example = new FacLeaveMessageExample();
        example.createCriteria().andIsDeletedEqualTo(false);
        example.setOrderByClause("create_time desc");
        final List<FacLeaveMessage> messages = this.leaveMessageMapper.selectByExample(example);
        return messages;
    }

    @Override
    public FacLeaveMessage selectLeaveMessage(Long id) {
        final FacLeaveMessageExample example = new FacLeaveMessageExample();
        example.createCriteria().andIsDeletedEqualTo(false).andIdEqualTo(id);
        final List<FacLeaveMessage> messages = this.leaveMessageMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(messages)) {
            return messages.get(0);
        }
        return null;
    }

    private boolean checkProdBuyed(String prodId, List<BuyerBusiness> buyerBusinesses) {
        if (StringUtils.isEmpty(prodId) || CollectionUtils.isEmpty(buyerBusinesses)) {
            return false;
        }
        for (BuyerBusiness buyerBusiness : buyerBusinesses) {
            if (prodId.equals(buyerBusiness.getBusinessProdId().toString())) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd EE");
        Date endDate = new Date();
        Date startDate = TimeUtils.getDateByHours(endDate, -168);
        String start = dateFormat.format(startDate);
        String end = dateFormat.format(endDate);
        System.out.println("start = " + start + ", end = " + end);
    }
}
