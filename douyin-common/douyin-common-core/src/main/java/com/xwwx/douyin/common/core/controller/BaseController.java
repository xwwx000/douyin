package com.xwwx.douyin.common.core.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xwwx.douyin.common.core.constant.TokenConstants;
import com.xwwx.douyin.common.core.domain.SystemToken;
import com.xwwx.douyin.common.core.exception.ServiceException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;

/**
 * @author: 可乐罐
 * @date: 2021/12/28 21:39
 * @description:
 */
public class BaseController {
    @Resource
    private HttpServletRequest request;
    /**
     * token中取后台管理用户对象
     * @return
     */
    protected SystemToken getSystemToken() {
        try {
            String token = URLDecoder.decode(request.getHeader(TokenConstants.TOKEN_USER), "UTF-8").replace("\\", "");
            System.out.println("token:" + token);
            //String tokenStr = token.substring(1, token.length() - 1);
            return JSON.toJavaObject(JSONObject.parseObject(token),
                    SystemToken.class);
        } catch (Exception e) {
            throw new ServiceException("根据token解析用户异常");
        }
    }
}
