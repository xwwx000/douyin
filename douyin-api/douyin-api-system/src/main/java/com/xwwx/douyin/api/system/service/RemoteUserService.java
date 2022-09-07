package com.xwwx.douyin.api.system.service;

import com.xwwx.douyin.api.system.domain.SysUser;
import com.xwwx.douyin.api.system.factory.RemoteUserFallbackFactory;
import com.xwwx.douyin.common.core.constant.ServiceNameConstants;
import com.xwwx.douyin.common.core.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @author: 可乐罐
 * @date: 2021/12/27 16:25
 * @description:用户feign
 */
@FeignClient(contextId = "remoteUserService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteUserFallbackFactory.class)
public interface RemoteUserService {
    /**
     * 通过用户名密码查询用户信息
     * @param user
     * @return
     */
    @PostMapping("/device/system/user/getUser")
    public R<SysUser> getUser(@RequestBody Map<String,String> user);

}
