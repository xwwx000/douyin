package com.xwwx.douyin.api.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xwwx.douyin.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * @author: 可乐罐
 * @date: 2021/12/27 10:14
 * @description:
 */
public class SysLoginInfor extends BaseEntity {

    /** 用户账号 */
    private String userName;

    /** 状态 0成功 1失败 */
    private String status;

    /** 地址 */
    private String ipaddr;

    /** 描述 */
    private String msg;

    /** 访问时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date accessTime;
}
