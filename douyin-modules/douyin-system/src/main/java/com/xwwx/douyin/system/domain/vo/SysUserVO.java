package com.xwwx.douyin.system.domain.vo;

import com.xwwx.douyin.system.domain.Module;
import com.xwwx.douyin.system.domain.Role;
import com.xwwx.douyin.system.domain.SysUser;
import lombok.Data;

import java.util.List;

/**
 * @author: 可乐罐
 * @date: 2022/3/21 12:32
 * @description:用户扩展类
 */
@Data
public class SysUserVO extends SysUser {
    private String deptName;
    private String genderInfo;
    private String statusInfo;
    private String userTypeInfo;
    private String roles;
    private String[] allowsDepts;
    private String[] allowsRoles;
    private List<Role> roleList;
    private List<Module> moduleList;
}
