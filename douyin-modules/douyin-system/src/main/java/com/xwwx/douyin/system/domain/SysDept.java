package com.xwwx.douyin.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.xwwx.douyin.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: 可乐罐
 * @date: 2022/3/23 15:58
 * @description:部门对象
 */
@Data
@Accessors(chain = true)//lombok链式
@TableName("sys_dept")
public class SysDept extends BaseEntity {
    @ApiModelProperty(value = "id",hidden = false)
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    @ApiModelProperty(value = "上级代码")
    private String pid;
    @ApiModelProperty(value = "部门名称")
    private String deptName;
    @ApiModelProperty(value = "管理员")
    private String manager;
    @ApiModelProperty(value = "电话")
    private String telphone;
    @ApiModelProperty(value = "排序")
    private Integer sort;
    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;
}
