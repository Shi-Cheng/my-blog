package com.blog.myblog.request;
public class DeleteRequest {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DeleteRequest{" +
                "id=" + id +
                '}';
    }
}
