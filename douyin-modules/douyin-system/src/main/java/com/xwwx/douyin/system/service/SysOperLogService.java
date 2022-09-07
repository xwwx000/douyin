package com.xwwx.douyin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xwwx.douyin.api.system.domain.SysOperLog;

/**
 * @author: 可乐罐
 * @date: 2021/12/27 14:52
 * @description:系统操作日志接口
 */
public interface SysOperLogService extends IService<SysOperLog> {
    /**
     * 插入日志
     * @param operLog
     * @return
     */
    public int saveOperLog(SysOperLog operLog);
}
