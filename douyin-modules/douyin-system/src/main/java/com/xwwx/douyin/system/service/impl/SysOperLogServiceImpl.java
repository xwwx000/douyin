package com.xwwx.douyin.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xwwx.douyin.api.system.domain.SysOperLog;
import com.xwwx.douyin.system.mapper.SysOperLogMapper;
import com.xwwx.douyin.system.service.SysOperLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author: 可乐罐
 * @date: 2021/12/27 14:53
 * @description:
 */
@Slf4j
@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements SysOperLogService {

    /**
     * 插入日志
     * @param operLog
     * @return
     */
    public int saveOperLog(SysOperLog operLog) {
        return this.baseMapper.insert(operLog);
    }
}
