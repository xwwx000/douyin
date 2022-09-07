package com.xwwx.douyin.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.xwwx.douyin.api.system.domain.SysUser;
import com.xwwx.douyin.common.core.constant.HttpStatus;
import com.xwwx.douyin.common.core.constant.TokenConstants;
import com.xwwx.douyin.common.core.domain.SystemToken;
import com.xwwx.douyin.common.core.utils.ServletUtils;
import com.xwwx.douyin.common.core.utils.StringUtils;
import com.xwwx.douyin.common.redis.service.RedisService;
import com.xwwx.douyin.gateway.config.properties.IgnoreWhiteProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author: 可乐罐
 * @date: 2021/12/24 20:33
 * @description:用户验证过滤器
 */
@Slf4j
@Component
public class AuthFilter implements GlobalFilter, Ordered {
    @Autowired
    private RedisService redisService;
    // 白名单
    @Autowired
    private IgnoreWhiteProperties ignoreWhiteProperties;

    /**
     * 过滤器
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpRequest.Builder mutate = request.mutate();
        String url = request.getURI().getPath();
        // 跳过白名单
        if (StringUtils.matches(url, ignoreWhiteProperties.getWhites())) {
            return chain.filter(exchange);
        }
        //获取token
        String token = request.getHeaders().getFirst(TokenConstants.TOKEN);

        if (StringUtils.isEmpty(token)) {
            return unauthorizedResponse(exchange, "令牌不能为空");
        }
        if(!token.startsWith(TokenConstants.SYSTEM_PREFIX) && !token.startsWith(TokenConstants.APP_PREFIX)){
            return unauthorizedResponse(exchange, "令牌无效");
        }

//        //验证令牌合法性
//        if(!JwtUtils.checkToken(token)){
//            return unauthorizedResponse(exchange, "令牌已过期或验证不正确！");
//        }

        //验证redis中是否合法
        Object user = redisService.get(token);
        if (!(redisService.hasKey(token)) || StringUtils.isNull(user)) {
            return unauthorizedResponse(exchange, "无效用户");
        }

        //系统用户
        if(token.startsWith(TokenConstants.SYSTEM_PREFIX)){
            //用户信息放入header传递
            addHeader(mutate, TokenConstants.USER_TYPE, "system");
            SystemToken systemToken = new SystemToken();
            systemToken.setId(((SysUser) user).getId());
            systemToken.setUserCode(((SysUser) user).getUserCode());
            systemToken.setUserName(((SysUser) user).getUserName());
            systemToken.setDeptId(((SysUser) user).getDeptId());
            systemToken.setCreateTime(((SysUser) user).getCreateTime());
            systemToken.setToken(token);
            addHeader(mutate, TokenConstants.TOKEN_USER, JSONObject.toJSONString(systemToken));
        }
        //app用户
        if(token.startsWith(TokenConstants.APP_PREFIX)){
        }
        //追加有效时长
        redisService.expire(token,TokenConstants.TOKEN_TIME);

        return chain.filter(exchange.mutate().request(mutate.build()).build());
    }

    @Override
    public int getOrder()
    {
        return -200;
    }

    /**
     * 返回未授权
     * @param exchange
     * @param msg
     * @return
     */
    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String msg) {
        log.error("[鉴权异常处理]请求路径:{}", exchange.getRequest().getPath());
        return ServletUtils.webFluxResponseWriter(exchange.getResponse(), msg, HttpStatus.UNAUTHORIZED);
    }

    /**
     * 写入头文件
     * @param mutate
     * @param name
     * @param value
     */
    private void addHeader(ServerHttpRequest.Builder mutate, String name, Object value) {
        if (value == null) {
            return;
        }
        String valueStr = value.toString();
        String valueEncode = ServletUtils.urlEncode(valueStr);
        mutate.header(name, valueEncode,"UTF-8");
    }
}
