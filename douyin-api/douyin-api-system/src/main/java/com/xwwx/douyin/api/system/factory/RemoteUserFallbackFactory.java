package com.xwwx.douyin.api.system.factory;

import com.xwwx.douyin.api.system.service.RemoteUserService;
import com.xwwx.douyin.common.core.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 用户服务降级处理
 * 
 * @author xwwx
 */
@Component
@Slf4j
public class RemoteUserFallbackFactory implements FallbackFactory<RemoteUserService> {
    @Override
    public RemoteUserService create(Throwable throwable) {
        log.error("用户服务调用失败:{}", throwable.getMessage());
        return new RemoteUserService() {
            @Override
            public R getUser(Map<String,String> user) {
                return R.error("调用用户接口失败");
            }
        };
    }
}
