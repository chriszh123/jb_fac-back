package com.ruoyi.fac.mapper;

import com.ruoyi.fac.domain.Menu;

import java.util.List;

/**
 * 菜单管理 数据层
 *
 * @author ruoyi
 * @date 2018-12-24
 */
public interface MenuMapper {
    /**
     * 查询菜单管理信息
     *
     * @param id 菜单管理ID
     * @return 菜单管理信息
     */
    Menu selectMenuById(Integer id);

    /**
     * 查询菜单管理列表
     *
     * @param menu 菜单管理信息
     * @return 菜单管理集合
     */
    List<Menu> selectMenuList(Menu menu);

    /**
     * 新增菜单管理
     *
     * @param menu 菜单管理信息
     * @return 结果
     */
    int insertMenu(Menu menu);

    /**
     * 修改菜单管理
     *
     * @param menu 菜单管理信息
     * @return 结果
     */
    int updateMenu(Menu menu);

    /**
     * 删除菜单管理
     *
     * @param id 菜单管理ID
     * @return 结果
     */
    int deleteMenuById(Integer id);

    /**
     * 批量删除菜单管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteMenuByIds(String[] ids);

}