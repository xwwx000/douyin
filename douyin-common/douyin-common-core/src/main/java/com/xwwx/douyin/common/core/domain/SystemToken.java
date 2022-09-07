package com.xwwx.douyin.common.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 可乐罐
 * @date: 2021/12/28 21:23
 * @description:系统用户对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemToken implements Serializable {
    private String id;
    private String userCode;
    private String userName;
    private String deptId;
    private Date createTime;
    private String token;
}

