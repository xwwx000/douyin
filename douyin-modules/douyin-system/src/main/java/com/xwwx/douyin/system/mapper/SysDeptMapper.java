package com.xwwx.douyin.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xwwx.douyin.system.domain.SysDept;
import com.xwwx.douyin.system.domain.vo.SysDeptVO;
import com.xwwx.douyin.system.domain.vo.SysUserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: 可乐罐
 * @date: 2022/3/23 16:05
 * @description:部门mapper
 */
@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {
    /**
     * 获取全部部门列表
     * @return
     */
    @Select("select * from sys_dept where deleted=0")
    public List<SysDeptVO> getAllSysDeptList();
    /**
     * 获取分页部门列表
     * @return
     */
    @Select("select * from sys_dept ${ew.customSqlSegment}")
    public Page<SysDeptVO> getSysDeptList(Page<SysUserVO> page, @Param(Constants.WRAPPER) Wrapper<SysUserVO> userWrapper);

    /**
     * 批量删除部门
     * @param deptIds
     */
    public void deleteDept(@Param(value = "deptIds") List<String> deptIds);
}
