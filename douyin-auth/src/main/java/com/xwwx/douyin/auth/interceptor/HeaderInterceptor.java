package com.xwwx.douyin.auth.interceptor;

import com.xwwx.douyin.common.core.constant.TokenConstants;
import com.xwwx.douyin.common.core.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: 可乐罐
 * @date: 2022/1/13 9:44
 * @description:
 */
@Slf4j
@Component
public class HeaderInterceptor implements AsyncHandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        String header = ServletUtils.getHeader(request, TokenConstants.TOKEN_USER);
        log.info("-------HeaderInterceptor  preHandle---------");
        log.info(header);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        log.info("-------HeaderInterceptor  afterCompletion---------");
    }
}
