package com.xwwx.douyin.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xwwx.douyin.common.core.domain.R;
import com.xwwx.douyin.system.domain.Device;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @ClassName SysDeviceService
 * @Author: 可乐罐
 * @Date: 2022/7/24 17:19
 * @Description:设备操作服务
 * @Version 1.0
 */
public interface SysDeviceService {

    public Page<Device> getDeviceList(Map<String,Object> param);
    /**
     * 向设备端发送指令
     * @param pMap
     * @return
     */
    public R sendDeviceData(@RequestBody Map<String,Object> pMap);
}
