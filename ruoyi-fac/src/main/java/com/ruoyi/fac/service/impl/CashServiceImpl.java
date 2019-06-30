package com.ruoyi.fac.service.impl;

import com.ruoyi.common.support.Convert;
import com.ruoyi.fac.domain.Buyer;
import com.ruoyi.fac.domain.Cash;
import com.ruoyi.fac.enums.ScoreTypeEnum;
import com.ruoyi.fac.mapper.BuyerMapper;
import com.ruoyi.fac.mapper.CashMapper;
import com.ruoyi.fac.mapper.FacBuyerMapper;
import com.ruoyi.fac.mapper.FacBuyerSignMapper;
import com.ruoyi.fac.model.FacBuyer;
import com.ruoyi.fac.model.FacBuyerExample;
import com.ruoyi.fac.model.FacBuyerSign;
import com.ruoyi.fac.service.ICashService;
import com.ruoyi.fac.util.DecimalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 买家提现记录 服务层实现
 *
 * @author ruoyi
 * @date 2018-12-24
 */
@Service
public class CashServiceImpl implements ICashService {
    private static final Logger logger = LoggerFactory.getLogger(CashServiceImpl.class);

    @Autowired
    private CashMapper cashMapper;
    @Autowired
    private BuyerMapper buyerMapper;
    @Autowired
    private FacBuyerMapper facBuyerMapper;
    @Autowired
    private FacBuyerSignMapper facBuyerSignMapper;

    /**
     * 查询买家提现记录信息
     *
     * @param id 买家提现记录ID
     * @return 买家提现记录信息
     */
    @Override
    public Cash selectCashById(Integer id) {
        return cashMapper.selectCashById(id);
    }

    /**
     * 查询买家提现记录列表
     *
     * @param cash 买家提现记录信息
     * @return 买家提现记录集合
     */
    @Override
    public List<Cash> selectCashList(Cash cash) {
        cash.setIsDeleted(0);
        List<Cash> cashList = this.cashMapper.selectCashList(cash);
        if (!CollectionUtils.isEmpty(cashList)) {
            for (Cash cash1 : cashList) {
                // 失败场景下打款时间为空
                if (cash1.getStatus().intValue() == 2) {
                    cash1.setPayTime(null);
                }
            }
        }
        return cashList;
    }

    /**
     * 新增买家提现记录
     *
     * @param cash 买家提现记录信息
     * @return 结果
     */
    @Override
    public int insertCash(Cash cash) {
        Date now = new Date();
        cash.setCreateTime(now);
        cash.setUpdateTime(now);
        cash.setIsDeleted(0);
        return cashMapper.insertCash(cash);
    }

    /**
     * 修改买家提现记录
     *
     * @param cash 买家提现记录信息
     * @return 结果
     */
    @Override
    public int updateCash(Cash cash) {
        Date now = new Date();
        cash.setUpdateTime(now);
        cash.setPayTime(now);
        int rows = this.cashMapper.updateCash(cash);
        // 如果更新成功，且打款成功需要更新当前用户对应的可用余额+增加一条提现的操作记录(fac_buyer_sign)
        Cash db = this.cashMapper.selectCashById(cash.getId());
        if (db != null && db.getUserId() != null && cash.getStatus() != null && cash.getStatus().intValue() == 3) {
            Buyer buyer = this.buyerMapper.selectBuyerById(db.getUserId());
            if (buyer != null) {
                // 1.更细用户余额信息
                FacBuyer facBuyer = new FacBuyer();
                BigDecimal oldBalance = buyer.getBalance() != null ? buyer.getBalance() : DecimalUtils.getDefaultDecimal();
                BigDecimal newBalance = DecimalUtils.subtract(oldBalance, db.getCash());
                facBuyer.setBalance(newBalance);
                facBuyer.setUpdateTime(now);
                FacBuyerExample buyerExample = new FacBuyerExample();
                buyerExample.createCriteria().andIsDeletedEqualTo(false).andIdEqualTo(db.getUserId());
                rows = this.facBuyerMapper.updateByExampleSelective(facBuyer, buyerExample);
                logger.info("------------update facBuyer balance success, buyerId:%s, oldBalance:%s,newBalance:%s"
                        , db.getUserId(), oldBalance, newBalance);
                // 2.增加一条提现记录
                FacBuyerSign facBuyerSign = new FacBuyerSign();
                facBuyerSign.setToken(buyer.getToken());
                facBuyerSign.setNickName(buyer.getNickName());
                facBuyerSign.setType(ScoreTypeEnum.CASH_OUT.getValue());
                facBuyerSign.setPoint(Short.valueOf("0"));
                facBuyerSign.setMount(db.getCash());
                facBuyerSign.setSignTime(now);
                facBuyerSign.setCreateTime(now);
                facBuyerSign.setUpdateTime(now);
                facBuyerSign.setIsDeleted(false);
                rows = this.facBuyerSignMapper.insertSelective(facBuyerSign);
                logger.info("------------add cash out record success, buyerId:%s, mount:%s", db.getUserId(), db.getCash());
            }
        }

        return rows;
    }

    /**
     * 删除买家提现记录对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCashByIds(String ids) {
        return cashMapper.deleteCashByIds(Convert.toStrArray(ids));
    }

}
