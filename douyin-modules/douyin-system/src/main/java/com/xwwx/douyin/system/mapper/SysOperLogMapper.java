package com.xwwx.douyin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xwwx.douyin.api.system.domain.SysOperLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: 可乐罐
 * @date: 2021/12/27 13:02
 * @description:系统日志mapper
 */
@Mapper
public interface SysOperLogMapper extends BaseMapper<SysOperLog> {
}
