package com.xwwx.douyin.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xwwx.douyin.system.domain.SysUser;
import com.xwwx.douyin.system.domain.vo.SysUserVO;

import java.util.Map;

/**
 * @author: 可乐罐
 * @date: 2021/12/27 19:49
 * @description:系统用户接口
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 根据用户名密码取用户对象
     * @param user
     * @return
     */
    public SysUser getUser(Map<String,String> user);
    /**
     * 获取全部用户
     * @param param
     * @return Page<UserVO>
     */
    public Page<SysUserVO> getUserList(Map<String,Object> param);
    /**
     * 设置用户状态
     */
    public void setStatus(SysUser sysUser);

    /**
     * 删除用户
     * @param id
     */
    public void deleteUser(String id);
    /**
     * 添加用户
     * @param sysUserVO
     */
    public void addUser(SysUserVO sysUserVO);
    /**
     * 修改用户
     * @param sysUserVO
     */
    public void updateUser(SysUserVO sysUserVO);
}
