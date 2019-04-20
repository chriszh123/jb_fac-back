package com.ruoyi.fac.automapper;

import com.ruoyi.fac.model.FacChannel;
import com.ruoyi.fac.model.FacChannelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FacChannelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fac_channel
     *
     * @mbggenerated
     */
    int countByExample(FacChannelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fac_channel
     *
     * @mbggenerated
     */
    int deleteByExample(FacChannelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fac_channel
     *
     * @mbggenerated
     */
    int insert(FacChannel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fac_channel
     *
     * @mbggenerated
     */
    int insertSelective(FacChannel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fac_channel
     *
     * @mbggenerated
     */
    List<FacChannel> selectByExample(FacChannelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fac_channel
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") FacChannel record, @Param("example") FacChannelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fac_channel
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") FacChannel record, @Param("example") FacChannelExample example);
}