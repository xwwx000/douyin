package com.xwwx.douyin.auth.controller;

import com.xwwx.douyin.api.system.domain.SysUser;
import com.xwwx.douyin.common.core.constant.Constants;
import com.xwwx.douyin.common.core.constant.TokenConstants;
import com.xwwx.douyin.common.core.controller.BaseController;
import com.xwwx.douyin.common.core.domain.R;
import com.xwwx.douyin.common.core.exception.ServiceException;
import com.xwwx.douyin.common.core.utils.JwtUtils;
import com.xwwx.douyin.common.core.utils.StringUtils;
import com.xwwx.douyin.auth.service.TokenService;
import com.xwwx.douyin.common.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 可乐罐
 * @date: 2021/12/26 20:05
 * @description:token 控制器
 */
@Slf4j
@RestController
@RequestMapping("/device/auth/token")
public class TokenController extends BaseController {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private RedisService redisService;

    /**
     * 后台登录
     * @param user
     * @return
     */
    @PostMapping("/system_login")
    public R systemLogin(@RequestBody Map<String,String> user){

        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(user.get("username"),user.get("password"))) {
            throw new ServiceException("用户/密码必须填写");
        }
        SysUser sysUser = tokenService.systemLogin(user);
        sysUser.setUserPwd("");
        String token = JwtUtils.getJwtToken(sysUser.getUserCode());
        Map<String,Object> map = new HashMap<>();
        map.put("token",TokenConstants.SYSTEM_PREFIX + token);
        map.put("user",sysUser);
        //放入redis中
        redisService.set(TokenConstants.SYSTEM_PREFIX + token,sysUser,TokenConstants.TOKEN_TIME);
        return R.ok(map);
    }
    /**
     * app登录
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/app_login")
    public R appLogin(String username, String password){

        log.info("usercode" + getSystemToken().getUserCode());
        return R.ok();
    }
}
