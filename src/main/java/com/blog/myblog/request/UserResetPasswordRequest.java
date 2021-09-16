package com.blog.myblog.request;
public class UserResetPasswordRequest {
    private Long id;

    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserResetPasswordRequest{" +
                "id=" + id +
                ", password='" + password + '\'' +
                '}';
    }
}
