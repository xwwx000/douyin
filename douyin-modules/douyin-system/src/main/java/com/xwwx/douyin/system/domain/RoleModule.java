package com.xwwx.douyin.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xwwx.douyin.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: 可乐罐
 * @date: 2022/3/19 19:33
 * @description:角色模块对照
 */
@Data
@TableName("sys_role_module")
public class RoleModule extends BaseEntity {
    @ApiModelProperty(value = "id",hidden = false)
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String roleId;
    private String moduleId;
}