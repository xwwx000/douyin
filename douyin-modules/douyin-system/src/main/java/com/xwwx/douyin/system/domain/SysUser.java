package com.xwwx.douyin.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.xwwx.douyin.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @author: 可乐罐
 * @date: 2022/3/21 12:29
 * @description:系统用户
 */
@Data
@Accessors(chain = true)
@TableName("sys_user")
public class SysUser extends BaseEntity {

    @ApiModelProperty(value = "id",hidden = false)
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    @ApiModelProperty(value = "用户代码")
    private String userCode;
    @ApiModelProperty(value = "用户名称")
    private String userName;
    @ApiModelProperty(value = "性别")
    private Integer gender;
    @ApiModelProperty(value = "用户密码")
    private String userPwd;
    @ApiModelProperty(value = "部门id")
    private String deptId;
    @ApiModelProperty(value = "岗位")
    private String job;
    @ApiModelProperty(value = "电话")
    private String mobile;
    @ApiModelProperty(value = "用户类型")
    private Integer userType;
    @ApiModelProperty(value = "允许访问部门")
    private String allowsDept;
    @ApiModelProperty(value = "访问时间")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastTime;
    @ApiModelProperty(value = "最后登录ip")
    private String lastIp;
    @ApiModelProperty(value = "状态")
    private Integer status;
    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;

    //访问模块数组
    @TableField(exist = false)
    private List<String> modules;
}
