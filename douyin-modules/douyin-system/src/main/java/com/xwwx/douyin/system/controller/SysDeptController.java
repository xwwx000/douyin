package com.xwwx.douyin.system.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xwwx.douyin.common.core.domain.R;
import com.xwwx.douyin.common.core.utils.StringUtils;
import com.xwwx.douyin.system.common.Utils;
import com.xwwx.douyin.system.domain.SysDept;
import com.xwwx.douyin.system.domain.vo.SysDeptVO;
import com.xwwx.douyin.system.service.SysDeptService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 可乐罐
 * @date: 2022/3/23 15:56
 * @description:部门控制器
 */
@RestController
@RequestMapping("/device/system/dept")
@Slf4j
public class SysDeptController {
    @Autowired
    private SysDeptService sysDeptService;
    @ApiOperation(value = "获取部门列表", notes = "")
    @GetMapping("/getDeptList")
    /**
     * 获取部门分页列表
     * 列表包含子部门
     */
    public R getDeptList(String pageSize, String pageNo){
        if(StringUtils.isEmpty(pageSize)){
            return R.error("pageSize不能为空");
        }
        if(StringUtils.isEmpty(pageNo)){
            return R.error("pageNo不能为空");
        }
        Map pMap = new HashMap();
        pMap.put("pageSize",Integer.parseInt(pageSize));
        pMap.put("pageNo",Integer.parseInt(pageNo));
        Page<SysDeptVO> deptList = sysDeptService.getSysDeptList(pMap);
        Map<String,Object> rMap = Utils.packagePage(deptList);
        String str = JSONArray.toJSONStringWithDateFormat(rMap.get("list"),"yyyy-MM-dd HH:mm:ss");
        String replaceStr = str.replace("\"children\":[]", "\"children1\":[]");
        List<Map> rsysDeptVOList = JSONArray.parseArray(replaceStr, Map.class);
        rMap.put("list",rsysDeptVOList);
        return R.ok(rMap);
    }
    @ApiOperation(value = "获取部门树", notes = "")
    @GetMapping("/deptListWithTree")
    public R deptListWithTree(){
        List<SysDeptVO> sysDeptVOList = sysDeptService.deptListWithTree();
        String str = JSONArray.toJSONString(sysDeptVOList);
        String replaceStr = str.replace("\"children\":[]", "");
        List<Map> rsysDeptVOList = JSONArray.parseArray(replaceStr, Map.class);
        return R.ok(rsysDeptVOList);
    }
    @ApiOperation(value = "增加部门", notes = "")
    @PostMapping("/addDept")
    public R addDept(@RequestBody SysDept sysDept){
        if(sysDept.getPid() == null){
            return R.error("上级部门编码不能为空");
        }
        if(StringUtils.isEmpty(sysDept.getDeptName())){
            return R.error("部门名称不能为空");
        }
        sysDeptService.addDept(sysDept);
        return R.ok();
    }
    @ApiOperation(value = "增加部门", notes = "")
    @PostMapping("/updateDept")
    public R updateDept(@RequestBody SysDept sysDept){
        if(sysDept.getId() == null){
            return R.error("部门编码不能为空");
        }
        if(StringUtils.isEmpty(sysDept.getDeptName())){
            return R.error("部门名称不能为空");
        }
        sysDeptService.updateDept(sysDept);
        return R.ok();
    }
    @ApiOperation(value = "删除部门", notes = "")
    @DeleteMapping("/deleteDept")
    public R deleteDept(@RequestBody SysDeptVO sysDept){
        System.out.println(sysDept);
        if(sysDept.getId() == null){
            return R.error("部门编码不能为空");
        }
        sysDeptService.deleteDept(sysDept);
        return R.ok();
    }
}
