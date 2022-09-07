package com.xwwx.douyin.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.xwwx.douyin.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName Device
 * @Author: 可乐罐
 * @Date: 2022/7/25 10:24
 * @Description:设备终端
 * @Version 1.0
 */
@Data
@Accessors(chain = true)
@TableName("sys_device")
public class Device extends BaseEntity {
    @ApiModelProperty(value = "id",hidden = false)
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    @ApiModelProperty(value = "设备代码")
    private String deviceCode;
    @ApiModelProperty(value = "设备名称")
    private String deviceName;
    @ApiModelProperty(value = "设备类型")
    private Integer deviceType;
    @ApiModelProperty(value = "状态")
    private Integer status;
    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;
}
