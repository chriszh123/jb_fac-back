package com.ruoyi.mry.mapper;

import com.ruoyi.mry.model.MryCustomerInvest;
import com.ruoyi.mry.model.MryCustomerInvestExample;
import java.util.List;

import com.ruoyi.mry.vo.MryQueryVo;
import org.apache.ibatis.annotations.Param;

public interface MryCustomerInvestMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mry_customer_invest
     *
     * @mbggenerated
     */
    int countByExample(MryCustomerInvestExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mry_customer_invest
     *
     * @mbggenerated
     */
    int deleteByExample(MryCustomerInvestExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mry_customer_invest
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mry_customer_invest
     *
     * @mbggenerated
     */
    int insert(MryCustomerInvest record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mry_customer_invest
     *
     * @mbggenerated
     */
    int insertSelective(MryCustomerInvest record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mry_customer_invest
     *
     * @mbggenerated
     */
    List<MryCustomerInvest> selectByExample(MryCustomerInvestExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mry_customer_invest
     *
     * @mbggenerated
     */
    MryCustomerInvest selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mry_customer_invest
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") MryCustomerInvest record, @Param("example") MryCustomerInvestExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mry_customer_invest
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") MryCustomerInvest record, @Param("example") MryCustomerInvestExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mry_customer_invest
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(MryCustomerInvest record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mry_customer_invest
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(MryCustomerInvest record);

    List<MryCustomerInvest> queryRecentShopMoneyInfo(MryQueryVo queryVo);
}