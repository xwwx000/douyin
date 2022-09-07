package com.xwwx.douyin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xwwx.douyin.system.domain.Role;
import com.xwwx.douyin.system.domain.RoleModule;
import com.xwwx.douyin.system.domain.UserRole;
import com.xwwx.douyin.system.domain.vo.ModuleVO;
import com.xwwx.douyin.system.mapper.RoleMapper;
import com.xwwx.douyin.system.service.ModuleService;
import com.xwwx.douyin.system.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author: 可乐罐
 * @date: 2022/3/19 19:44
 * @description:角色服务实现
 */
@Slf4j
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private ModuleService moduleService;
    /**
     * 分页取角色列表
     */
    public Page<Role> getRoleList(Map<String,Object> param){
        Page<Role> page = new Page<>((Integer)param.get("pageNo"),(Integer)param.get("pageSize"));
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        if(param.get("roleName") != null){
            queryWrapper.like("role_name", param.get("roleName"));
        }
        queryWrapper.orderByAsc("sort");
        Page<Role> roleList = this.baseMapper.getRoleList(page,queryWrapper);
        return roleList;
    }
    /**
     * 根据用户取角色列表
     * @param userid
     * @return
     */
    @Override
    public List<Role> getRoleListByUser(String userid) {
        List<Role> roleListByUser = this.baseMapper.getRoleListByUser(userid);
        return roleListByUser;
    }

    /**
     * 批量插入用户与角色关系
     * @param allowsRoles
     * @param userid
     */
    public void addUserRole(String[] allowsRoles,String userid){
        //分解角色列表
        for(String roleid : allowsRoles){
            UserRole userRole = new UserRole();
            userRole.setId(IdWorker.getIdStr());
            userRole.setUserId(userid);
            userRole.setRoleId(roleid);
            this.baseMapper.insertUserRole(userRole);
        }
    }
    /**
     * 增加角色
     * @param role
     */
    public void addRole(Role role){
        this.baseMapper.insert(role);
    }
    /**
     * 修改角色
     * @param role
     */
    public void updateRole(Role role){
        this.baseMapper.updateById(role);
    }

    /**
     * 删除用户对于角色
     * @throws Exception
     */
    public void deleteUserRole(String userId){
        this.baseMapper.deleteUserRole(userId);
    }
    /**
     * 修改角色权限
     * @param roleId
     * @param modules
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    public void updateRoleModule(String roleId,List<String> modules){
        this.baseMapper.deleteRoleModuleByRoleId(roleId);
        if(modules!=null && !modules.isEmpty()){
            //插入角色对应菜单
            List<RoleModule> roleModuleList = new ArrayList<>();
            for(String moduleId : modules){
                log.info(moduleId);
                RoleModule rm = new RoleModule();
                rm.setId(IdWorker.getIdStr());
                rm.setRoleId(roleId);
                rm.setModuleId(moduleId);
                roleModuleList.add(rm);
            }
            this.baseMapper.insertRoleModule(roleModuleList);
        }
    }

    /**
     * 角色权限分配 树形
     * @param roleId
     * @return
     */
    public List<ModuleVO> listWithTree(String roleId) {
        // 1.查询出所有的分类
        List<ModuleVO> entities = moduleService.getModuleByRoleId(roleId);
        // 2.组装成父子的树形结构
        List<ModuleVO> level1Menus = new ArrayList<>();
        // 找到所有的一级分类
        for (ModuleVO entity : entities) {
            if (entity.getPid().equals("0")) {
                level1Menus.add(entity);
            }
        }
        for (ModuleVO level1Menu : level1Menus) {
            level1Menu.setChildren(getChildrens(level1Menu, entities));
        }
        //排序
        level1Menus.sort(new Comparator<ModuleVO>() {
            @Override
            public int compare(ModuleVO o1, ModuleVO o2) {
                return (o1.getSort() == null ? 0 : o1.getSort()) - (o2.getSort() == null ? 0 : o2.getSort());
            }
        });
        return level1Menus;
    }

    public List<ModuleVO> getChildrens(ModuleVO root, List<ModuleVO> all) {
        List<ModuleVO> children = new ArrayList<>();
        for (ModuleVO a : all) {
            if (a.getPid().equals(root.getId())) {
                a.setChildren(getChildrens(a, all));
                children.add(a);
            }
        }
        children.sort(new Comparator<ModuleVO>() {
            @Override
            public int compare(ModuleVO o1, ModuleVO o2) {
                return (o1.getSort() == null ? 0 : o1.getSort()) - (o2.getSort() == null ? 0 : o2.getSort());
            }
        });
        return children;
    }
    /**
     * 删除权限
     * @param roleId
     */
    @Transactional(rollbackFor=Exception.class)
    public void deleteRole(String roleId) throws Exception{
        //删除角色
        this.baseMapper.deleteById(roleId);
        //删除角色对应module
        this.baseMapper.deleteRoleModuleByRoleId(roleId);
        //删除角色对应用户
        this.baseMapper.deleteUserRoleByRole(roleId);
    }
}
