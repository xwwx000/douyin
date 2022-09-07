package com.xwwx.douyin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xwwx.douyin.system.domain.Module;
import com.xwwx.douyin.system.domain.vo.ModuleVO;
import com.xwwx.douyin.system.mapper.ModuleMapper;
import com.xwwx.douyin.system.service.ModuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author: 可乐罐
 * @date: 2022/3/18 11:56
 * @description:模块服务实现
 */
@Slf4j
@Service
public class ModuleServiceImpl extends ServiceImpl<ModuleMapper, Module> implements ModuleService {
    @Autowired
    private ModuleMapper moduleMapper;
    //获取菜单
    public List<Module> getAllModuleByUser(String usercode){
        List<Module> moduleList = this.baseMapper.getAllModuleByUser(usercode);
        //moduleList.forEach(System.out::println);
        return moduleList;
    }

    //根据用户取左侧菜单树
    @Override
    public List<ModuleVO> listWithTree(String usercode) {
        // 1.查询出所有的模块
        List<ModuleVO> entities = this.baseMapper.getModuleByUser(usercode);
        // 2.组装成父子的树形结构
        List<ModuleVO> level1Menus = new ArrayList<>();
        // 找到所有的一级分类
        for (ModuleVO entity : entities) {
            if (entity.getPid().equals("0")) {
                level1Menus.add(entity);
            }
        }
        for (ModuleVO level1Menu : level1Menus) {
            level1Menu.setChildren(getChildrens(level1Menu, entities));
        }
        //排序
        level1Menus.sort(new Comparator<ModuleVO>() {
            @Override
            public int compare(ModuleVO o1, ModuleVO o2) {
                return (o1.getSort() == null ? 0 : o1.getSort()) - (o2.getSort() == null ? 0 : o2.getSort());
            }
        });
        return level1Menus;
    }

    @Override
    public List<ModuleVO> getChildrens(ModuleVO root, List<ModuleVO> all) {
        List<ModuleVO> children = new ArrayList<>();
        for (ModuleVO a : all) {
            if (a.getPid().equals(root.getId())) {
                a.setChildren(getChildrens(a, all));
                children.add(a);
            }
        }
        //排序
        children.sort(new Comparator<ModuleVO>() {
            @Override
            public int compare(ModuleVO o1, ModuleVO o2) {
                return (o1.getSort() == null ? 0 : o1.getSort()) - (o2.getSort() == null ? 0 : o2.getSort());
            }
        });
        return children;
    }

    /**
     * 取全部菜单功能树
     * @return
     */
    public List<ModuleVO> getModuleFunctionTree(){
        // 1.查询出所有的模块
        List<ModuleVO> entities = this.baseMapper.getModuleFunctionTree();
        // 2.组装成父子的树形结构
        List<ModuleVO> level1Menus = new ArrayList<>();
        // 找到所有的一级分类
        for (ModuleVO entity : entities) {
            if (entity.getPid().equals("0")) {
                level1Menus.add(entity);
            }
        }
        for (ModuleVO level1Menu : level1Menus) {
            level1Menu.setChildren(getChildrens(level1Menu, entities));
        }
        //排序
        level1Menus.sort(new Comparator<ModuleVO>() {
            @Override
            public int compare(ModuleVO o1, ModuleVO o2) {
                return (o1.getSort() == null ? 0 : o1.getSort()) - (o2.getSort() == null ? 0 : o2.getSort());
            }
        });
        return level1Menus;
    }
    public List<ModuleVO> getModuleByRoleId(String roleId){
        List<ModuleVO> moduleVOList = this.baseMapper.getModuleByRoleId(roleId);
        return moduleVOList;
    }
    /**
     * 获取拦截url列表
     * @return
     */
    public List<String> getUrlInterceptorModuleList(){
        QueryWrapper<Module> queryWrapper = new QueryWrapper<Module>();
        queryWrapper.eq("type",2);
        queryWrapper.eq("is_show",1);
        List<String> urlInterceptorList = new ArrayList<>();
        List<Module> modules = moduleMapper.getUrlInterceptorModuleList(queryWrapper);
        if(modules!=null && !modules.isEmpty()) {
            for (Module module : modules) {
                urlInterceptorList.add(module.getUrl());
            }
        }
        return urlInterceptorList;
    }

    /**
     * 删除模块
     * @param id
     */
    public void deleteModule(String id){
        QueryWrapper<Module> queryWrapper = new QueryWrapper<Module>();
        queryWrapper.eq("pid",id);
        List<Module> list = this.baseMapper.selectList(queryWrapper);
        if(list!=null && !list.isEmpty()){
            for (Module module:list){
                deleteModule(module.getId());
            }
            this.baseMapper.deleteModule(id);
        }else{
            this.baseMapper.deleteModule(id);
        }
    }

    /**
     * 设置菜单显示状态
     * @param id
     * @param isshow
     */
    public void isShowModule(String id,Integer isshow){
        QueryWrapper<Module> queryWrapper = new QueryWrapper<Module>();
        queryWrapper.eq("pid",id);
        List<Module> list = this.baseMapper.selectList(queryWrapper);
        if(list!=null && !list.isEmpty()){
            for (Module module:list){
                isShowModule(module.getId(),isshow);
            }
            this.baseMapper.isShowModule(id,isshow);
        }else{
            this.baseMapper.isShowModule(id,isshow);
        }
    }
}