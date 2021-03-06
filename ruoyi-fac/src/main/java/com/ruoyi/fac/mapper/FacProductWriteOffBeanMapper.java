package com.ruoyi.fac.mapper;

import com.ruoyi.fac.model.FacProductWriteOffBean;
import com.ruoyi.fac.model.FacProductWriteOffBeanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FacProductWriteOffBeanMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fac_product_writeoff
     *
     * @mbggenerated
     */
    int countByExample(FacProductWriteOffBeanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fac_product_writeoff
     *
     * @mbggenerated
     */
    int deleteByExample(FacProductWriteOffBeanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fac_product_writeoff
     *
     * @mbggenerated
     */
    int insert(FacProductWriteOffBean record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fac_product_writeoff
     *
     * @mbggenerated
     */
    int insertSelective(FacProductWriteOffBean record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fac_product_writeoff
     *
     * @mbggenerated
     */
    List<FacProductWriteOffBean> selectByExample(FacProductWriteOffBeanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fac_product_writeoff
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") FacProductWriteOffBean record, @Param("example") FacProductWriteOffBeanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fac_product_writeoff
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") FacProductWriteOffBean record, @Param("example") FacProductWriteOffBeanExample example);
}