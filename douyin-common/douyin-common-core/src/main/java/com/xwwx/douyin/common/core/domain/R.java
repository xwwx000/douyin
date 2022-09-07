package com.xwwx.douyin.common.core.domain;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 可乐罐
 * @date: 2021/12/24 20:33
 * @description:响应信息主体
 */
@Data
@Accessors(chain = true)
@ToString
public class R<T> implements Serializable {
    private int code = 0;
    private String msg = "success";
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static R ok() {
        return new R();
    }

    public static <T> R ok(T data) {
        if (data instanceof IPage) {
            return new R().setData(new PageResp((IPage) data));
        }
        return new R().setData(data);
    }

    public static R error(int code, String msg) {
        return new R().setCode(code).setMsg(msg);
    }

    public static R error() {
        return new R().setCode(-1).setMsg("");
    }

    public static R error(String msg) {
        return new R().setCode(-1).setMsg(msg);
    }

    @Data
    public static class PageResp<T> {

        private int totalCount;
        private int pageSize;
        private int totalPage;
        private int currPage;
        private List<?> list;

        public PageResp(IPage<T> page) {
            list = page.getRecords();
            totalCount = (int) page.getTotal();
            pageSize = (int) page.getSize();
            currPage = (int) page.getCurrent();
            totalPage = (int) page.getPages();
        }
    }
}
