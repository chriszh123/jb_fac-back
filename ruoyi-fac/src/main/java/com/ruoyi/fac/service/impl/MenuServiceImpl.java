package com.ruoyi.fac.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fac.mapper.MenuMapper;
import com.ruoyi.fac.domain.Menu;
import com.ruoyi.fac.service.IMenuService;
import com.ruoyi.common.support.Convert;

/**
 * 菜单管理 服务层实现
 * 
 * @author ruoyi
 * @date 2018-12-24
 */
@Service
public class MenuServiceImpl implements IMenuService 
{
	@Autowired
	private MenuMapper menuMapper;

	/**
     * 查询菜单管理信息
     * 
     * @param id 菜单管理ID
     * @return 菜单管理信息
     */
    @Override
	public Menu selectMenuById(Integer id)
	{
	    return menuMapper.selectMenuById(id);
	}
	
	/**
     * 查询菜单管理列表
     * 
     * @param menu 菜单管理信息
     * @return 菜单管理集合
     */
	@Override
	public List<Menu> selectMenuList(Menu menu)
	{
	    return menuMapper.selectMenuList(menu);
	}
	
    /**
     * 新增菜单管理
     * 
     * @param menu 菜单管理信息
     * @return 结果
     */
	@Override
	public int insertMenu(Menu menu)
	{
	    return menuMapper.insertMenu(menu);
	}
	
	/**
     * 修改菜单管理
     * 
     * @param menu 菜单管理信息
     * @return 结果
     */
	@Override
	public int updateMenu(Menu menu)
	{
	    return menuMapper.updateMenu(menu);
	}

	/**
     * 删除菜单管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteMenuByIds(String ids)
	{
		return menuMapper.deleteMenuByIds(Convert.toStrArray(ids));
	}
	
}
