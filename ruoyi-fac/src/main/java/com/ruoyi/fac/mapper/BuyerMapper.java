package com.ruoyi.fac.mapper;

import com.ruoyi.fac.domain.Buyer;
import com.ruoyi.fac.vo.QueryVo;
import com.ruoyi.fac.vo.client.req.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 买者用户 数据层
 *
 * @author ruoyi
 * @date 2018-12-24
 */
public interface BuyerMapper {
    /**
     * 查询买者用户信息
     *
     * @param id 买者用户ID
     * @return 买者用户信息
     */
    Buyer selectBuyerById(Long id);

    /**
     * 查询买者用户列表
     *
     * @param buyer 买者用户信息
     * @return 买者用户集合
     */
    List<Buyer> selectBuyerList(Buyer buyer);

    /**
     * 新增买者用户
     *
     * @param buyer 买者用户信息
     * @return 结果
     */
    int insertBuyer(Buyer buyer);

    /**
     * 修改买者用户
     *
     * @param buyer 买者用户信息
     * @return 结果
     */
    int updateBuyer(Buyer buyer);

    /**
     * 删除买者用户
     *
     * @param id 买者用户ID
     * @return 结果
     */
    int deleteBuyerById(Long id);

    /**
     * 批量删除买者用户
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteBuyerByIds(String[] ids);

    List<Buyer> queryRecentUserInfo(QueryVo queryVo);

    /**
     * 指定日期内的用户数
     *
     * @param queryVo
     * @return
     */
    int countBuyers(QueryVo queryVo);

    /**
     * 查询指定token对应的用户
     *
     * @param token
     * @return
     */
    Buyer selectBuyerByOpenId(@Param("token") String token);

    void updateUserInfo(UserInfo userInfo);

    void batchUpdatePoints(@Param("list") List<Buyer> list);
}