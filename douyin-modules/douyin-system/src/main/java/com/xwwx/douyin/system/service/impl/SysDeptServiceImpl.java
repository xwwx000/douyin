package com.xwwx.douyin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xwwx.douyin.system.domain.SysDept;
import com.xwwx.douyin.system.domain.vo.SysDeptVO;
import com.xwwx.douyin.system.domain.vo.SysUserVO;
import com.xwwx.douyin.system.mapper.SysDeptMapper;
import com.xwwx.douyin.system.service.SysDeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author: 可乐罐
 * @date: 2022/3/23 16:05
 * @description:获取部门列表
 */
@Slf4j
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {
    /**
     * 获取全部部门列表
     * @return
     */
    public Page<SysDeptVO> getSysDeptList(Map<String,Object> param){
        Page<SysUserVO> page = new Page<>((Integer)param.get("pageNo"),(Integer)param.get("pageSize"));
        QueryWrapper<SysUserVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted",0);
        queryWrapper.eq("pid","0");
        queryWrapper.orderByAsc("sort");
        Page<SysDeptVO> sysDeptList = this.baseMapper.getSysDeptList(page,queryWrapper);
        List<SysDeptVO> entities = this.baseMapper.getAllSysDeptList();

        for (SysDeptVO level1Menu : sysDeptList.getRecords()) {
            level1Menu.setChildren(getChildrens(level1Menu, entities));
        }

        //排序
        sysDeptList.getRecords().sort(new Comparator<SysDeptVO>() {
            @Override
            public int compare(SysDeptVO o1, SysDeptVO o2) {
                return (o1.getSort() == null ? 0 : o1.getSort()) - (o2.getSort() == null ? 0 : o2.getSort());
            }
        });

        return sysDeptList;
    }
    /**
     * 递归取部门树
     * @return
     */
    public List<SysDeptVO> deptListWithTree(){
        // 1.查询出所有的部门
        List<SysDeptVO> entities = this.baseMapper.getAllSysDeptList();
        // 2.组装一级的树形结构
        List<SysDeptVO> level1Menus = new ArrayList<>();
        // 找到所有的一级分类
        for (SysDeptVO entity : entities) {
            if (entity.getPid().equals("0")) {
                level1Menus.add(entity);
            }
        }

        for (SysDeptVO level1Menu : level1Menus) {
            level1Menu.setChildren(getChildrens(level1Menu, entities));
        }

        //排序
        level1Menus.sort(new Comparator<SysDeptVO>() {
            @Override
            public int compare(SysDeptVO o1, SysDeptVO o2) {
                return (o1.getSort() == null ? 0 : o1.getSort()) - (o2.getSort() == null ? 0 : o2.getSort());
            }
        });
        return level1Menus;
    }
    public List<SysDeptVO> getChildrens(SysDeptVO root, List<SysDeptVO> all) {
        List<SysDeptVO> children = new ArrayList<>();
        for (SysDeptVO a : all) {
            if (a.getPid().equals(root.getId())) {
                a.setChildren(getChildrens(a,all));
                children.add(a);
            }
        }
        children.sort(new Comparator<SysDeptVO>() {
            @Override
            public int compare(SysDeptVO o1, SysDeptVO o2) {
                return (o1.getSort() == null ? 0 : o1.getSort()) - (o2.getSort() == null ? 0 : o2.getSort());
            }
        });
        return children;
    }
    /**
     * 增加部门
     * @param sysDept
     * @return
     */
    public void addDept(SysDept sysDept){
        this.baseMapper.insert(sysDept);
    }
    /**
     * 修改部门
     * @param sysDept
     */
    public void updateDept(SysDept sysDept){
        this.baseMapper.updateById(sysDept);
    }
    /**
     * 删除部门
     * @param sysDept
     */
    public void deleteDept(SysDeptVO sysDept){
        //递归查找部门id
        //1.查询出所有的部门
        List<SysDeptVO> entities = this.baseMapper.getAllSysDeptList();
        //2.取包含子数组
        List<SysDeptVO> childrens = getChildrens(sysDept, entities);
        //3.组装子数组
        List<String> ids = new ArrayList<>();
        ids.add(sysDept.getId());
        for(SysDeptVO sysDeptVO:childrens){
            ids.add(sysDeptVO.getId());
        }
        //long [] array = ids.stream().mapToLong(t->t.longValue()).toArray();
        this.baseMapper.deleteDept(ids);
    }
    
}
