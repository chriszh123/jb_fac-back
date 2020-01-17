package com.ruoyi.mry.mapper;

import com.ruoyi.mry.model.MryCustomerCard;
import com.ruoyi.mry.model.MryCustomerCardExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MryCustomerCardMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mry_customer_card
     *
     * @mbggenerated
     */
    int countByExample(MryCustomerCardExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mry_customer_card
     *
     * @mbggenerated
     */
    int deleteByExample(MryCustomerCardExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mry_customer_card
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mry_customer_card
     *
     * @mbggenerated
     */
    int insert(MryCustomerCard record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mry_customer_card
     *
     * @mbggenerated
     */
    int insertSelective(MryCustomerCard record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mry_customer_card
     *
     * @mbggenerated
     */
    List<MryCustomerCard> selectByExample(MryCustomerCardExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mry_customer_card
     *
     * @mbggenerated
     */
    MryCustomerCard selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mry_customer_card
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") MryCustomerCard record, @Param("example") MryCustomerCardExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mry_customer_card
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") MryCustomerCard record, @Param("example") MryCustomerCardExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mry_customer_card
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(MryCustomerCard record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mry_customer_card
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(MryCustomerCard record);
}