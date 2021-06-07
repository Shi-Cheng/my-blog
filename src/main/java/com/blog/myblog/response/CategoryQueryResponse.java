package com.blog.myblog.response;

import java.util.List;

public class CategoryQueryResponse {

    private Long id;

    private Long parentId;

    private String name;

    private Integer sort;

    private List<CategoryQueryResponse> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public List<CategoryQueryResponse> getChildren() {
        return children;
    }

    public void setChildren(List<CategoryQueryResponse> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "CategoryQueryResponse{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                ", sort=" + sort +
                ", children=" + children +
                '}';
    }
}