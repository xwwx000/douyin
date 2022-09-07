package com.xwwx.douyin.common.core.handler;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xwwx.douyin.common.core.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * @author: 可乐罐
 * @date: 2021/12/27 9:40
 * @description:全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> errorHandler(HttpServletRequest req, Exception e) {

        log.info(e.getMessage());
        R resp = new R().setCode(-1);

        String detailMsg = e.getMessage();
        detailMsg = detailMsg == null ? e.getCause().getMessage() : detailMsg;
        if (detailMsg == null && e instanceof UndeclaredThrowableException) {
            detailMsg = ((UndeclaredThrowableException) e).getUndeclaredThrowable().getMessage();
        }
        resp.setMsg(detailMsg);
        resp.setData(new JSONObject().fluentPut("url", req.getRequestURL().toString()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(JSON.toJSONString(resp), headers, HttpStatus.OK);
    }
}
