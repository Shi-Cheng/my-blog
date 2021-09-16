package com.blog.myblog.response;
import com.blog.myblog.domain.RoleMenu;

public class RoleMenuResponse extends RoleMenu {

    private String pId;

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    @Override
    public String toString() {
        return "RoleMenuResponse{" +
                ", pId='" + pId + '\'' +
                '}';
    }
}