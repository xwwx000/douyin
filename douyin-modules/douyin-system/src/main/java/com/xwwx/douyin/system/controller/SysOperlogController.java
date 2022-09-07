package com.xwwx.douyin.system.controller;

import com.xwwx.douyin.api.system.domain.SysOperLog;
import com.xwwx.douyin.common.core.domain.R;
import com.xwwx.douyin.system.service.SysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 可乐罐
 * @date: 2021/12/27 12:21
 * @description:系统操作日志
 */
@RestController
@RequestMapping("/device/system/operlog")
public class SysOperlogController {

    @Autowired
    private SysOperLogService sysOperLogService;
    @PostMapping("/saveOperLog")
    public R saveOperLog(@RequestBody SysOperLog operLog){

        return sysOperLogService.saveOperLog(operLog) > 0 ? R.ok() : R.error() ;
    }
}
