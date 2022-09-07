package com.xwwx.douyin.gateway.service;

import com.xwwx.douyin.common.core.domain.R;

/**
 * @author: 可乐罐
 * @date: 2022/1/6 9:58
 * @description:
 */
public interface ValidateCodeService {
    /**
     * 生成验证码
     */
    public R createCapcha() throws Exception;

    /**
     * 校验验证码
     */
    public void checkCapcha(String code, String uuid) throws Exception;
}
