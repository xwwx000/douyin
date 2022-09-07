package com.xwwx.douyin.api.system.service;

import com.xwwx.douyin.api.system.domain.SysLoginInfor;
import com.xwwx.douyin.api.system.domain.SysOperLog;
import com.xwwx.douyin.api.system.factory.RemoteLogFallbackFactory;
import com.xwwx.douyin.common.core.constant.SecurityConstants;
import com.xwwx.douyin.common.core.constant.ServiceNameConstants;
import com.xwwx.douyin.common.core.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author: 可乐罐
 * @date: 2021/12/27 9:21
 * @description:
 */
@FeignClient(contextId = "remoteLogService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteLogFallbackFactory.class)
public interface RemoteLogService {
    /**
     * 保存系统日志
     *
     * @param sysOperLog 日志实体
     * @param source 请求来源
     * @return 结果
     */
    @PostMapping("/device/system/operlog/saveOperLog")
    public R<Boolean> saveOperLog(@RequestBody SysOperLog sysOperLog, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 保存访问记录
     *
     * @param sysLoginInfor 访问实体
     * @param source 请求来源
     * @return 结果
     */
    @PostMapping("/operlog/loginInfor")
    public R<Boolean> saveLoginInfor(@RequestBody SysLoginInfor sysLoginInfor, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
