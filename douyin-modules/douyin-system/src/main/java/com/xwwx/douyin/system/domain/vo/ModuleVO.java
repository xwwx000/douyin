package com.xwwx.douyin.system.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author: 可乐罐
 * @date: 2022/3/18 11:52
 * @description:模块扩展
 */
@Data
@ApiModel("模块扩展")
public class ModuleVO {
    private String id;
    private String pid;
    private String path;
    private String name;
    private String icon;
    private Integer sort;
    private Integer level;
    private Integer type;
    private Integer isshow;
    /**
     * 下级分类
     */
    @TableField(exist = false)
    private List<ModuleVO> children;

    private Integer status; //状态0未拥有 1拥有
    private String remark; //菜单描述
}
