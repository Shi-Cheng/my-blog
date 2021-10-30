package com.blog.myblog.domain;
public class RoleMenuCust {
    private Long id;

    private String roleId;

    private String menuId;

    private String pId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    @Override
    public String toString() {
        return "RoleMenuCust{" +
                "id=" + id +
                ", roleId='" + roleId + '\'' +
                ", menuId='" + menuId + '\'' +
                ", pId='" + pId + '\'' +
                '}';
    }
}