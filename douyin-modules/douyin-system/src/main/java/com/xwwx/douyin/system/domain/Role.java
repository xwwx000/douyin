package com.xwwx.douyin.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xwwx.douyin.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: 可乐罐
 * @date: 2022/3/19 19:26
 * @description:角色
 */
@Data
@TableName("sys_role")
public class Role extends BaseEntity {
    @ApiModelProperty(value = "id",hidden = false)
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    @ApiModelProperty(value = "角色名称")
    private String roleName;
    @ApiModelProperty(value = "是否管理员")
    private Integer isAdmin;
    @ApiModelProperty(value = "角色描述")
    private String remark;
    @ApiModelProperty(value = "排序")
    private Integer sort;
}
