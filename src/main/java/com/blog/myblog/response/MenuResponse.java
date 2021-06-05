package com.blog.myblog.response;

public class MenuResponse implements Comparable<MenuResponse> {
    private Long id;

    private Long pid;

    private String name;

    private String desc;

    private Integer type;

    private Integer orderNum;

    private String linkUrl;

    private String btnId;

    private String css;

    private String permission;

    public MenuResponse(MenuResponse menuResponse) {
        this.id = menuResponse.getId();
        this.pid = menuResponse.getPid();
        this.name = menuResponse.getName();
        this.desc = menuResponse.getDesc();
        this.type = menuResponse.getType();
        this.orderNum = menuResponse.getOrderNum();
        this.linkUrl = menuResponse.getLinkUrl();
        this.btnId = menuResponse.getBtnId();
        this.css = menuResponse.getCss();
        this.permission = menuResponse.getPermission();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getBtnId() {
        return btnId;
    }

    public void setBtnId(String btnId) {
        this.btnId = btnId;
    }

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "MenuResponse{" +
                "id=" + id +
                ", pid=" + pid +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", type=" + type +
                ", orderNum=" + orderNum +
                ", linkUrl='" + linkUrl + '\'' +
                ", btnId='" + btnId + '\'' +
                ", css='" + css + '\'' +
                ", permission='" + permission + '\'' +
                '}';
    }

    @Override
    public int compareTo(MenuResponse o) {
        return 0;
    }
}
