package com.xwwx.douyin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xwwx.douyin.system.domain.Module;
import com.xwwx.douyin.system.domain.vo.ModuleVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: 可乐罐
 * @date: 2022/3/18 11:55
 * @description:模块服务接口
 */
public interface ModuleService extends IService<Module> {
    //获取全部菜单功能
    public List<Module> getAllModuleByUser(@Param("usercode") String usercode);

    /**
     * 查找出所有的分类
     * @return 菜单树
     */
    public List<ModuleVO> listWithTree(String usercode);

    /**
     * 递归查找所有的下级分类
     * 在这一级别的分类中找下级分类
     *
     * @param root 当前级别的分类
     * @param all  全部分类
     * @return 下一级分类
     */
    public List<ModuleVO> getChildrens(ModuleVO root, List<ModuleVO> all);
    /**
     * 取全部菜单功能树
     * @return
     */
    public List<ModuleVO> getModuleFunctionTree();
    /**
     * 根据角色id 取菜单树
     * @param roleId
     * @return
     */
    public List<ModuleVO> getModuleByRoleId(String roleId);
    /**
     * 获取拦截url列表
     * @return
     */
    public List<String> getUrlInterceptorModuleList();
    /**
     * 删除模块
     * @param id
     */
    public void deleteModule(String id);
    /**
     * 设置菜单显示状态
     * @param id
     * @param isshow
     */
    public void isShowModule(String id,Integer isshow);
}
