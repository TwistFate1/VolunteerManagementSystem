package com.hwadee.springboot.entity;

import java.io.Serializable;

/**
 * (Admin)实体类
 */
public class Admin implements Serializable {
    private static final long serialVersionUID = 1L;

    private String adminId;
    private String password;
    private Integer status;
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId='" + adminId + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", avatar='" + avatar + '\'' +
                '}';
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Admin(String adminId, String password, Integer status, String avatar) {
        this.adminId = adminId;
        this.password = password;
        this.status = status;
        this.avatar = avatar;
    }

    public Admin() {
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Admin(String adminId, String password, Integer status) {
        this.adminId = adminId;
        this.password = password;
        this.status = status;
    }
}
