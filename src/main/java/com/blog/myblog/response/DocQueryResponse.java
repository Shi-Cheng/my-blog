package com.blog.myblog.response;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class DocQueryResponse {
    private Long id;

    private Long ebookId;

    private Long parentId;

    private String name;

    private Integer sort;

    private Integer viewCount;

    private Integer voteCount;

    private List<DocQueryResponse> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEbookId() {
        return ebookId;
    }

    public void setEbookId(Long ebookId) {
        this.ebookId = ebookId;
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

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public List<DocQueryResponse> getChildren() {
        return children;
    }

    public void setChildren(List<DocQueryResponse> docQueryResponses) {
        this.children = docQueryResponses;
    }

    @Override
    public String toString() {
        return "DocQueryResponse{" +
                "id=" + id +
                ", ebookId=" + ebookId +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                ", sort=" + sort +
                ", viewCount=" + viewCount +
                ", voteCount=" + voteCount +
                ", children=" + children +
                '}';
    }
}