package com.ruoyi.fac.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fac.mapper.CashMapper;
import com.ruoyi.fac.domain.Cash;
import com.ruoyi.fac.service.ICashService;
import com.ruoyi.common.support.Convert;

/**
 * 买家提现记录 服务层实现
 *
 * @author ruoyi
 * @date 2018-12-24
 */
@Service
public class CashServiceImpl implements ICashService {
    @Autowired
    private CashMapper cashMapper;

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
        return cashMapper.selectCashList(cash);
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
        return cashMapper.updateCash(cash);
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
