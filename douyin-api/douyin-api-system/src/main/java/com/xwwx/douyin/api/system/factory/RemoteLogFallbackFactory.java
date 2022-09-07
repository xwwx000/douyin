package com.xwwx.douyin.api.system.factory;

import com.xwwx.douyin.api.system.domain.SysLoginInfor;
import com.xwwx.douyin.api.system.domain.SysOperLog;
import com.xwwx.douyin.api.system.service.RemoteLogService;
import com.xwwx.douyin.common.core.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author: 可乐罐
 * @date: 2021/12/27 10:09
 * @description:
 */
@Component
@Slf4j
public class RemoteLogFallbackFactory implements FallbackFactory<RemoteLogService>{
    @Override
    public RemoteLogService create(Throwable cause) {
        log.error("日志服务调用失败:{}", cause.getMessage());
        return new RemoteLogService() {
            public R<Boolean> saveOperLog(SysOperLog sysOperLog, String source) {
                return null;
            }
            public R<Boolean> saveLoginInfor(SysLoginInfor sysLogininfor, String source)
            {
                return null;
            }
        };
    }
}
