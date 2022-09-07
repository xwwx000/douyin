package com.xwwx.douyin.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xwwx.douyin.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: 可乐罐
 * @date: 2022/3/18 11:51
 * @description:模块
 */
@Data
@TableName("sys_module")
public class Module extends BaseEntity {
    @ApiModelProperty(value = "id")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    @ApiModelProperty(value = "模块名称")
    private String moduleName;
    @ApiModelProperty(value = "等级1,2,3等")
    private Integer level;
    @ApiModelProperty(value = "类型1,菜单，2功能")
    private Integer type;
    @ApiModelProperty(value = "对应功能地址")
    private String url;
    @ApiModelProperty(value = "父级id")
    private String pid;
    @ApiModelProperty(value = "排序")
    private Integer sort;
    @ApiModelProperty(value = "副标题")
    private String subtitle;
    @ApiModelProperty(value = "图标")
    private String icon;
    @ApiModelProperty(value = "是否显示1显示 0隐藏")
    private Integer isShow;
    /**
     * 下级分类
     */
    @TableField(exist = false)
    private List<Module> children;
}

