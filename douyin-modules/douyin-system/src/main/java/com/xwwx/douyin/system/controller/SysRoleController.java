package com.xwwx.douyin.system.controller;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xwwx.douyin.common.core.domain.R;
import com.xwwx.douyin.common.core.utils.StringUtils;
import com.xwwx.douyin.system.common.Utils;
import com.xwwx.douyin.system.domain.Role;
import com.xwwx.douyin.system.domain.vo.ModuleVO;
import com.xwwx.douyin.system.service.ModuleService;
import com.xwwx.douyin.system.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 可乐罐
 * @date: 2022/3/19 19:56
 * @description:
 */
@Slf4j
@RestController
@RequestMapping("/device/system/role")
@Api(tags = "角色管理")
public class SysRoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private ModuleService moduleService;
    @ApiOperation(value = "获取角色列表", notes = "")
    @GetMapping("/getRoleList")
    public R getRoleList(String pageSize, String pageNo, String roleName){
        if(StringUtils.isEmpty(pageSize)){
            return R.error("pageSize不能为空");
        }
        if(StringUtils.isEmpty(pageNo)){
            return R.error("pageNo不能为空");
        }
        Map pMap = new HashMap();
        pMap.put("pageSize",Integer.parseInt(pageSize));
        pMap.put("pageNo",Integer.parseInt(pageNo));
        pMap.put("roleName",roleName);
        Page<Role> roleList = roleService.getRoleList(pMap);

        Map<String,Object> rMap = Utils.packagePage(roleList);
        return R.ok(rMap);
    }
    @ApiOperation(value = "增加角色", notes = "")
    @PostMapping("/addRole")
    public R addRole(@RequestBody Role role){
        if(StringUtils.isEmpty(role.getRoleName())){
            return R.error("角色名称不能为空");
        }
        try {
            roleService.addRole(role);
        } catch (Exception e) {
            return R.error("新增角色失败");
        }
        return R.ok();
    }
    @ApiOperation(value = "编辑角色", notes = "")
    @PutMapping("/updateRole")
    public R updateRole(@RequestBody Role role){
        if(StringUtils.isEmpty(role.getRoleName())){
            return R.error("角色名称不能为空");
        }
        try {
            roleService.updateRole(role);
        } catch (Exception e) {
            return R.error("更新角色失败");
        }
        return R.ok();
    }
    @ApiOperation(value = "删除角色", notes = "")
    @DeleteMapping("/deleteRole")
    public R deleteRole(String roleId){
        if(roleId == null){
            return R.error("角色id不能为空");
        }
        try {
            roleService.deleteRole(roleId);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("删除角色失败");
        }
        return R.ok();
    }
    @ApiOperation(value = "获取角色权限", notes = "")
    @GetMapping("/getModuleByRoleId")
    public R getModuleByRoleId(String roleId){
        if(roleId == null){
            return R.error("角色id不能为空");
        }
        //List<ModuleVO> moduleList = moduleService.getModuleByRoleId(roleId);

        List<ModuleVO> moduleList = roleService.listWithTree(roleId);

        String str = JSONArray.toJSONString(moduleList);
        String replaceStr = str.replace("\"children\":[],", "");
        System.out.println(replaceStr);
        List<Map> rmoduleList = JSONArray.parseArray(replaceStr, Map.class);
        return R.ok(rmoduleList);
    }
    @ApiOperation(value = "更新角色权限", notes = "")
    @PutMapping("/updateRoleModule")
    public R updateRoleModule(@RequestBody Map param){
        if(param.get("roleId") == null){
            return R.error("角色id不能为空");
        }
        if(param.get("modules") == null){
            return R.error("模块列表不能为空");
        }
        System.out.println("modules" + param.get("modules"));
        List<String> modules = (List<String>) param.get("modules");
        try {
            roleService.updateRoleModule((String)param.get("roleId"),modules);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("更新角色权限失败");
        }
        return R.ok();
    }
}
