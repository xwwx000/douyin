package com.xwwx.douyin.common.log.service;

import com.xwwx.douyin.api.system.domain.SysOperLog;
import com.xwwx.douyin.api.system.service.RemoteLogService;
import com.xwwx.douyin.common.core.constant.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 异步调用日志服务
 *
 * @author 可乐罐
 */
@Service
public class AsyncLogService {
    @Autowired
    private RemoteLogService remoteLogService;

    /**
     * 保存系统日志记录
     */
    @Async
    public void saveSysLog(SysOperLog sysOperLog) {
        remoteLogService.saveOperLog(sysOperLog, SecurityConstants.INNER);
    }
}
