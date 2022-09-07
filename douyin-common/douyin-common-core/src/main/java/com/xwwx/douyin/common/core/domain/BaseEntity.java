package com.xwwx.douyin.common.core.domain;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.*;

/**
 * @author: 可乐罐
 * @date: 2021/12/27 9:22
 * @description:
 */
@Data
@Accessors(chain = true)
public class BaseEntity<T> implements Serializable {

    @ApiModelProperty(hidden = true)
    @TableField(fill = FieldFill.INSERT)
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(hidden = true)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @TableField(exist = false)
    @ApiModelProperty(hidden = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int pageSize = 10;

    @TableField(exist = false)
    @ApiModelProperty(hidden = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int pageNo = 1;

    public Page mkPage() {
        return new Page(pageNo, pageSize).addOrder(OrderItem.desc("id"));
    }

    public Map<String, Object> toDbMap() {
        Map<String, Object> map = JSON.parseObject(JSON.toJSONString(this));
        Map<String, Object> underscoreMap = new HashMap<>();
        map.forEach((k, v) -> underscoreMap.put(StringUtils.camelToUnderline(k), v));
        return underscoreMap;
    }

    public static final List<String> toColName(String... fieldName) {
        List<String> ret = new ArrayList<>();
        for (String s : fieldName) {
            ret.add(StringUtils.camelToUnderline(s));
        }
        return ret;
    }
}