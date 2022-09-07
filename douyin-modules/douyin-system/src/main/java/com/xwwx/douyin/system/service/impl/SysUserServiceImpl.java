package com.xwwx.douyin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xwwx.douyin.common.core.constant.SecurityConstants;
import com.xwwx.douyin.common.core.exception.UtilException;
import com.xwwx.douyin.common.core.utils.StringUtils;
import com.xwwx.douyin.system.domain.Module;
import com.xwwx.douyin.system.domain.Role;
import com.xwwx.douyin.system.domain.SysUser;
import com.xwwx.douyin.system.domain.vo.SysUserVO;
import com.xwwx.douyin.system.mapper.SysUserMapper;
import com.xwwx.douyin.system.service.ModuleService;
import com.xwwx.douyin.system.service.RoleService;
import com.xwwx.douyin.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: 可乐罐
 * @date: 2021/12/27 19:49
 * @description:用户服务实现
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private ModuleService moduleService;
    @Autowired
    private RoleService roleService;
    /**
     * 根据用户名密码取用户对象
     * @param user
     * @return
     */
    public SysUser getUser(Map<String,String> user){

        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_code",user.get("username")).eq("user_pwd",DigestUtils.md5DigestAsHex(user.get("password").getBytes()));
        SysUser sysUser = this.baseMapper.selectOne(queryWrapper);

        //取拥有的模块列表
        if(sysUser!=null){
            List<Module> allModuleByUser = moduleService.getAllModuleByUser(sysUser.getUserCode());
            List modules = new ArrayList();
            for(Module module:allModuleByUser){
                modules.add(module.getUrl());
            }
            sysUser.setModules(modules);
        }
        return sysUser;
    }
    /**
     * 获取全部用户
     * @param param
     * @return
     */
    public Page<SysUserVO> getUserList(Map<String,Object> param){
        Page<SysUserVO> page = new Page<>((Integer)param.get("pageNo"),(Integer)param.get("pageSize"));
        QueryWrapper<SysUserVO> queryWrapper = new QueryWrapper<>();
        if(param.get("userName")!=null && !String.valueOf(param.get("userName")).isEmpty()){
            queryWrapper.like("user_name",String.valueOf(param.get("userName")));
        }
        //不等于
        //queryWrapper.ne("user_type",99);
        queryWrapper.and(
                QueryWrapper -> QueryWrapper.eq("user_type", 10)
                        .or().eq("user_type", 99)
        );
        queryWrapper.eq("a.deleted",0);
        String ascDesc = (String)param.get("ascDesc");
        if(StringUtils.isEmpty(ascDesc)){
            queryWrapper.orderByAsc("id");
        }else{
            if("ascend".equals(ascDesc)){
                queryWrapper.orderByAsc("a."+StringUtils.toUnderScoreCase((String)param.get("sortField")));
            }else{
                queryWrapper.orderByDesc("a."+StringUtils.toUnderScoreCase((String)param.get("sortField")));
            }
        }

        Page<SysUserVO> userList = this.baseMapper.getUserList(page,queryWrapper);
        List<SysUserVO> records = userList.getRecords();
        for(SysUserVO userVO : records){
            //部门整型数组
            if(!StringUtils.isEmpty(userVO.getAllowsDept())){
                String[] strs = userVO.getAllowsDept().split(",");
                userVO.setAllowsDepts(strs);
            }
            //根据userId取角色列表
            //加入角色列表
            List<Role> roleList = roleService.getRoleListByUser(userVO.getId());
            userVO.setRoleList(roleList);
            if(roleList!=null){
                //组装成逗号分隔形式
                List<String> rolesTmp = new ArrayList<>();
                for(Role role : roleList){
                    rolesTmp.add(role.getId());
                }
                String roles = String.join(",", rolesTmp);
                userVO.setRoles(roles);
            }
            //角色数组
            if(!StringUtils.isEmpty(userVO.getRoles())){
                String[] strs = userVO.getRoles().split(",");
                userVO.setAllowsRoles(strs);
            }
        }
        return userList;
    }

    /**
     * 设置用户状态
     * @param sysUser
     */
    public void setStatus(SysUser sysUser){
        this.baseMapper.setStatus(sysUser.getId(),sysUser.getStatus());
    }
    /**
     * 删除用户
     * @param id
     */
    public void deleteUser(String id){
        this.baseMapper.deleteById(id);
    }

    /**
     * 添加用户
     * @param sysUserVO
     */
    @Transactional(rollbackFor=Exception.class)
    public void addUser(SysUserVO sysUserVO){

        //1.判断该用户代码是否可用
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_code",sysUserVO.getUserCode());
        queryWrapper.and(
                QueryWrapper -> QueryWrapper.eq("deleted", 0)
                        .or().eq("deleted", 1)
        );
        List<SysUser> sysUsers = this.baseMapper.selectList(queryWrapper);
        if(sysUsers!=null&&!sysUsers.isEmpty()){
            //用户已经存在
            throw new UtilException("用户已经存在");
        }
        //2.插入用户表
        SysUser sysUser = new SysUser();
        sysUser.setUserCode(sysUserVO.getUserCode());
        sysUser.setUserName(sysUserVO.getUserName());
        String md5Password = DigestUtils.md5DigestAsHex(SecurityConstants.USER_PASSWORD.getBytes());
        sysUser.setUserPwd(md5Password);
        sysUser.setUserType(sysUserVO.getUserType());
        sysUser.setDeptId(sysUserVO.getDeptId());
        this.baseMapper.insert(sysUser);
        //3.删除用户与权限对照表
        roleService.deleteUserRole(sysUser.getId());
        //4.插入用户与权限对照表
        roleService.addUserRole(sysUserVO.getAllowsRoles(),sysUser.getId());
    }
    /**
     * 修改用户
     * @param sysUserVO
     */
    @Transactional(rollbackFor=Exception.class)
    public void updateUser(SysUserVO sysUserVO){

        //1.判断该用户代码是否可用
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",sysUserVO.getId());
        queryWrapper.and(
                QueryWrapper -> QueryWrapper.eq("deleted", 0)
                        .or().eq("deleted", 1)
        );
        List<SysUser> sysUsers = this.baseMapper.selectList(queryWrapper);
        if(sysUsers==null || sysUsers.isEmpty()){
            //用户不存在
            throw new UtilException("用户不存在");
        }
        //2.更新用户表
        SysUser sysUser = new SysUser();
        sysUser.setId(sysUserVO.getId());
        sysUser.setUserName(sysUserVO.getUserName());
        sysUser.setUserType(sysUserVO.getUserType());
        sysUser.setDeptId(sysUserVO.getDeptId());
        this.baseMapper.updateById(sysUser);
        //3.更新对照表
        roleService.deleteUserRole(sysUserVO.getId());
        roleService.addUserRole(sysUserVO.getAllowsRoles(),sysUser.getId());
    }
}
