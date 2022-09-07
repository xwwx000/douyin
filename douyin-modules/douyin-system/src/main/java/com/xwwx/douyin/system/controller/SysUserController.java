package com.xwwx.douyin.system.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xwwx.douyin.common.core.controller.BaseController;
import com.xwwx.douyin.common.core.domain.R;
import com.xwwx.douyin.common.core.utils.StringUtils;
import com.xwwx.douyin.common.log.annotation.Log;
import com.xwwx.douyin.system.common.Utils;
import com.xwwx.douyin.system.domain.Module;
import com.xwwx.douyin.system.domain.SysUser;
import com.xwwx.douyin.system.domain.vo.ModuleVO;
import com.xwwx.douyin.system.domain.vo.SysUserVO;
import com.xwwx.douyin.system.service.ModuleService;
import com.xwwx.douyin.system.service.SysUserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 可乐罐
 * @date: 2021/12/25 0:07
 * @description:
 */
@RestController
@RequestMapping("/device/system/user")
@Slf4j
public class SysUserController extends BaseController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ModuleService moduleService;
    @PostMapping("/getUser")
    public R<SysUser> getUser(@RequestBody Map<String,String> user){
        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(user.get("username"), user.get("password"))) {
            return R.error("用户、密码不能为空");
        }
        SysUser sysUser = sysUserService.getUser(user);
        if(sysUser == null){
            return R.error("用户、密码错误");
        }
        return R.ok(sysUser);
    }
    @ApiOperation(value = "用户列表")
    @GetMapping("/getUserList")
    @Log(title="用户管理")
    public R getUserList(String pageSize,String pageNo,String userName,String sortField,String ascDesc){
        System.out.println("sortField"+sortField);
        System.out.println("ascDesc"+ascDesc);
        if(StringUtils.isEmpty(pageSize)){
            return R.error("pageSize不能为空");
        }
        if(StringUtils.isEmpty(pageNo)){
            return R.error("pageNo不能为空");
        }
        Map pMap = new HashMap();
        pMap.put("pageSize",Integer.parseInt(pageSize));
        pMap.put("pageNo",Integer.parseInt(pageNo));
        pMap.put("sortField",sortField);
        pMap.put("ascDesc",ascDesc);
        Page<SysUserVO> userList = sysUserService.getUserList(pMap);
        Map<String,Object> rMap = Utils.packagePage(userList);
        return R.ok(rMap);
    }
    @ApiOperation(value = "获取用户拥有的菜单")
    @GetMapping("/menu")
    public R menu(){
        String usercode = (String)getSystemToken().getUserCode();
        List<ModuleVO> moduleList = moduleService.listWithTree(usercode);
        String str = JSONArray.toJSONString(moduleList);
        String replaceStr = str.replace("\"children\":[]", "");
        List<Map> rmoduleList = JSONArray.parseArray(replaceStr, Map.class);
        return R.ok(rmoduleList);
    }
    @ApiOperation(value = "根据用户取拥有的模块")
    @GetMapping("/getModulesByUser")
    public R getModulesByUser(){
        String usercode = (String)getSystemToken().getUserCode();
        List<Module> allModuleByUser = moduleService.getAllModuleByUser(usercode);
        return R.ok(allModuleByUser);
    }
    @ApiOperation(value = "获取所有菜单")
    @GetMapping("/getModuleFunctionTree")
    public R getModuleFunctionTree(){
        List<ModuleVO> moduleList = moduleService.getModuleFunctionTree();
        String str = JSONArray.toJSONString(moduleList);
        String replaceStr = str.replace("\"children\":[],", "");
        List<Map> rmoduleList = JSONArray.parseArray(replaceStr, Map.class);
        return R.ok(rmoduleList);
    }
    @ApiOperation(value = "删除模块")
    @PostMapping("/deleteModule")
    public R deleteModule(@RequestBody Map<String,String> module){

        if(StringUtils.isEmpty(module) || module.get("id")==null){
            return R.error("模块ID不能为空");
        }
        moduleService.deleteModule(module.get("id"));
        return R.ok();
    }
    @ApiOperation(value = "删除模块")
    @PutMapping("/isShowModule")
    public R isShowModule(@RequestBody Map<String,String> module){
        if(StringUtils.isEmpty(module) || module.get("id")==null){
            return R.error("模块ID不能为空");
        }
        if(module.get("isshow")==null){
            return R.error("状态不能为空");
        }
        moduleService.isShowModule(module.get("id"),Integer.valueOf(module.get("isshow")));
        return R.ok();
    }
    @ApiOperation(value = "设置用户状态")
    @PutMapping("/setStatus")
    public R setStatus(@RequestBody SysUser sysUser){
        if(sysUser.getId()==null){
            return R.error("用户id不能为空");
        }
        if(sysUser.getStatus()==null){
            return R.error("用户状态不能为空");
        }

        sysUserService.setStatus(sysUser);
        return R.ok();
    }
    @ApiOperation(value = "删除用户")
    @DeleteMapping("/deleteUser")
    public R deleteUser(@RequestBody Map param){

        if(param.get("id")==null){
            return R.error("用户ID不能为空");
        }
        sysUserService.deleteUser((String)param.get("id"));
        return R.ok();
    }
    /**
     * 添加用户
     * @param sysUserVO
     */
    @ApiOperation(value = "增加用户")
    @PostMapping("/addUser")
    public R addUser(@RequestBody SysUserVO sysUserVO){

        if(StringUtils.isEmpty(sysUserVO.getUserCode())){
            return R.error("用户代码不能为空");
        }
        if(StringUtils.isEmpty(sysUserVO.getUserName())){
            return R.error("用户名称不能为空");
        }
        if(StringUtils.isEmpty(sysUserVO.getDeptId())){
            return R.error("部门不能为空");
        }
        if(sysUserVO.getUserType() == null){
            return R.error("用户类型不能为空");
        }
        if(sysUserVO.getAllowsRoles() == null){
            return R.error("用户角色不能为空");
        }

        sysUserService.addUser(sysUserVO);

        return R.ok();
    }
    /**
     * 添加用户
     * @param sysUserVO
     */
    @ApiOperation(value = "修改用户")
    @PutMapping("/updateUser")
    public R updateUser(@RequestBody SysUserVO sysUserVO){

        if(StringUtils.isEmpty(sysUserVO.getUserCode())){
            return R.error("用户代码不能为空");
        }
        if(StringUtils.isEmpty(sysUserVO.getUserName())){
            return R.error("用户名称不能为空");
        }
        if(StringUtils.isEmpty(sysUserVO.getDeptId())){
            return R.error("部门不能为空");
        }
        if(sysUserVO.getUserType() == null){
            return R.error("用户类型不能为空");
        }
        if(sysUserVO.getAllowsRoles() == null){
            return R.error("用户角色不能为空");
        }

        sysUserService.updateUser(sysUserVO);

        return R.ok();
    }
}
