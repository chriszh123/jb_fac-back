package com.ruoyi.fac.service;

import com.ruoyi.fac.domain.Buyer;
import com.ruoyi.fac.vo.UserDiagramVo;

import java.util.List;
import java.util.Map;

/**
 * 买者用户 服务层
 *
 * @author ruoyi
 * @date 2018-12-24
 */
public interface IBuyerService {
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
     * 删除买者用户信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteBuyerByIds(String ids);

    /**
     * 获取用户-商家商品数数据
     *
     * @param buyer
     * @return
     */
    List<Map<String, Object>> bizProdTreeData(Buyer buyer);

    /**
     * 查询指定日期内每日的新增人数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    UserDiagramVo queryRecentUserInfo(String startDate, String endDate);
}
