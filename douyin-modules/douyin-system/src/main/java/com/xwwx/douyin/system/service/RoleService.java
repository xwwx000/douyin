package com.xwwx.douyin.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xwwx.douyin.system.domain.Role;
import com.xwwx.douyin.system.domain.vo.ModuleVO;

import java.util.List;
import java.util.Map;

/**
 * @author: 可乐罐
 * @date: 2022/3/19 19:43
 * @description:角色接口
 */
public interface RoleService  extends IService<Role> {

    /**
     * 分页取角色列表
     */
    public Page<Role> getRoleList(Map<String,Object> param);

    /**
     * 根据用户取角色列表
     * @return
     */
    public List<Role> getRoleListByUser(String userid);
    /**
     * 批量插入角色用户对照表
     * @param allowsRoles
     * @param userid
     */
    public void addUserRole(String[] allowsRoles,String userid);

    /**
     * 增加角色
     * @param role
     */
    public void addRole(Role role);
    /**
     * 删除用户对于角色
     * @throws Exception
     */
    public void deleteUserRole(String userId);
    /**
     * 修改角色
     * @param role
     */
    public void updateRole(Role role);
    /**
     * 修改角色权限
     * @param roleId
     * @param modules
     * @throws Exception
     */
    public void updateRoleModule(String roleId,List<String> modules);
    /**
     * 角色权限分配 树形
     * @param roleId
     * @return
     */
    public List<ModuleVO> listWithTree(String roleId);

    /**
     * 删除权限
     * @param roleId
     */
    public void deleteRole(String roleId) throws Exception;
}
