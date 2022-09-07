package com.xwwx.douyin.auth.service;

import com.xwwx.douyin.api.system.domain.SysUser;
import com.xwwx.douyin.api.system.service.RemoteLogService;
import com.xwwx.douyin.api.system.service.RemoteUserService;
import com.xwwx.douyin.common.core.constant.Constants;
import com.xwwx.douyin.common.core.domain.R;
import com.xwwx.douyin.common.core.exception.ServiceException;
import com.xwwx.douyin.common.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author: 可乐罐
 * @date: 2021/12/27 9:17
 * @description:
 */
@Service
public class TokenService {
    @Autowired
    private RemoteLogService remoteLogService;
    @Autowired
    private RemoteUserService remoteUserService;

    /**
     * 后台登录
     * @param user
     * @return
     */
    public SysUser systemLogin(Map<String,String> user){

        R<SysUser> userResult = remoteUserService.getUser(user);

        //返回错误
        if (-1 == userResult.getCode()) {
            throw new ServiceException(userResult.getMsg());
        }

        //返回对象为空
        if (StringUtils.isNull(userResult) || StringUtils.isNull(userResult.getData())) {
            throw new ServiceException("登录用户：" + user.get("userCode") + " 不存在");
        }

        SysUser sysUser = (SysUser) userResult.getData();

        return sysUser;
    }

    /**
     * app登录
     * @param username
     * @param password
     * @return
     */
    public R appLogin(String username, String password){

        return R.ok();
    }
}
