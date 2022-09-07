package com.xwwx.douyin.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xwwx.douyin.system.domain.Role;
import com.xwwx.douyin.system.domain.RoleModule;
import com.xwwx.douyin.system.domain.UserRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: 可乐罐
 * @date: 2022/3/19 19:36
 * @description:角色mapper
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 获取全部角色列表
     * @param page
     * @param roleWrapper
     * @return
     */
    @Select("select * from sys_role ${ew.customSqlSegment}")
    public Page<Role> getRoleList(Page<Role> page, @Param(Constants.WRAPPER) Wrapper<Role> roleWrapper);
    /**
     * 根据用户获取角色列表
     * @param userid
     * @return
     */
    @Select("select a.* from sys_role a join sys_user_role b on a.id = b.role_id where b.user_id = #{userid}")
    public List<Role> getRoleListByUser(String userid);

    @Select("delete from sys_role_module where role_id = #{roleId}")
    public void deleteRoleModuleByRoleId(@Param("roleId") String roleId);
    /**
     * 插入用户与角色关系
     * @param userRole
     */
    @Select("insert into sys_user_role(id,user_id,role_id)values(#{userRole.id},#{userRole.userId},#{userRole.roleId})")
    public void insertUserRole(@Param("userRole") UserRole userRole);

    /**
     * 根据用户删除用户角色对照表
     * @param userId
     */
    @Delete("delete from sys_user_role where user_id = #{userId}")
    public void deleteUserRole(@Param("userId") String userId);

    /**
     * 根据角色删除用户角色对照表
     * @param roleId
     */
    @Delete("delete from sys_user_role where role_id = #{roleId}")
    public void deleteUserRoleByRole(@Param("roleId") String roleId);
    /**
     * 批量插入角色与菜单关系
     * @param roleModuleList
     */
    public void insertRoleModule(@Param("roleModuleList")List<RoleModule> roleModuleList);
}