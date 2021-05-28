package com.blog.myblog.response;

import java.util.List;

public class PageResponse<T> {
    private long total;

    private long page;

    private List<T> list;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageResponse{" +
                "total=" + total +
                ", page=" + page +
                ", list=" + list +
                '}';
    }
}
