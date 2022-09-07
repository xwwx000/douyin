package com.xwwx.douyin.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xwwx.douyin.system.domain.SysDept;
import com.xwwx.douyin.system.domain.vo.SysDeptVO;

import java.util.List;
import java.util.Map;

/**
 * @author: 可乐罐
 * @date: 2022/3/23 16:09
 * @description:部门接口服务
 */
public interface SysDeptService {
    /**
     * 获取全部部门列表
     * @return
     */
    public Page<SysDeptVO> getSysDeptList(Map<String,Object> param);
    /**
     * 递归取部门树
     * @return
     */
    public List<SysDeptVO> deptListWithTree();
    /**
     * 增加部门
     * @param sysDept
     * @return
     */
    public void addDept(SysDept sysDept);

    /**
     * 修改部门
     * @param sysDept
     */
    public void updateDept(SysDept sysDept);
    /**
     * 删除部门
     * @param sysDept
     */
    public void deleteDept(SysDeptVO sysDept);
}
