package com.blog.myblog.request;
import javax.validation.constraints.NotEmpty;

public class UserLoginRequest {

    private String loginName;

    private String name;

    @NotEmpty(message = "【密码】不能为空")
    private String password;

    @NotEmpty(message = "【手机号】不能为空")
    private String phoneNumber;


    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "UserLoginRequest{" +
                "loginName='" + loginName + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}