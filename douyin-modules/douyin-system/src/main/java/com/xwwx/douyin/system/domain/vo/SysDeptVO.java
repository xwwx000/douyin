package com.xwwx.douyin.system.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.xwwx.douyin.system.domain.SysDept;
import lombok.Data;

import java.util.List;

/**
 * @author: 可乐罐
 * @date: 2022/3/23 16:00
 * @description:
 */
@Data
public class SysDeptVO extends SysDept {
    /**
     * 下级分类
     */
    @TableField(exist = false)
    private List<SysDeptVO> children;
}
