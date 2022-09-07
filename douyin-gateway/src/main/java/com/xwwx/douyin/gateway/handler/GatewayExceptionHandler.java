package com.xwwx.douyin.gateway.handler;

import com.xwwx.douyin.common.core.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author: 可乐罐
 * @date: 2021/12/24 20:28
 * @description:
 */
@Order(-1)
@Configuration
@Slf4j
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex){
        ServerHttpResponse response = exchange.getResponse();

        if (exchange.getResponse().isCommitted()){
            return Mono.error(ex);
        }

        String msg;

        if (ex instanceof NotFoundException) {
            msg = exchange.getRequest().getPath() + "服务未找到";
        }else if (ex instanceof ResponseStatusException) {
            ResponseStatusException responseStatusException = (ResponseStatusException) ex;
            msg = responseStatusException.getMessage();
        }
        else{
            msg = "内部服务器错误";
        }

        log.info("msg:" + msg);
        log.error("[网关异常处理]请求路径:{},异常信息:{}", exchange.getRequest().getPath(), ex.getMessage());

        return ServletUtils.webFluxResponseWriter(response, msg);
    }
}