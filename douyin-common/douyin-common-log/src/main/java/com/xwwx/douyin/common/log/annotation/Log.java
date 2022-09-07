package com.xwwx.douyin.common.log.annotation;

import com.xwwx.douyin.common.log.enums.BusinessType;
import com.xwwx.douyin.common.log.enums.OperatorType;

import java.lang.annotation.*;

/**
 * @author: 可乐罐
 * @date: 2021/12/27 10:11
 * @description:自定义操作日志记录注解
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log
{
    /**
     * 模块
     */
    public String title() default "";

    /**
     * 用户名
     */
    public String userName() default "";

    /**
     * 功能
     */
    public BusinessType businessType() default BusinessType.OTHER;

    /**
     * 操作人类别
     */
    public OperatorType operatorType() default OperatorType.MANAGE;

    /**
     * 是否保存请求的参数
     */
    public boolean isSaveRequestData() default true;

    /**
     * 是否保存响应的参数
     */
    public boolean isSaveResponseData() default true;
}
