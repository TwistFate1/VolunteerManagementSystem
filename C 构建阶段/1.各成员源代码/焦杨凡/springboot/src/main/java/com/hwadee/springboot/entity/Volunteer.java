package com.hwadee.springboot.entity;

import java.io.Serializable;
import java.util.List;

/**
 * (Volunteer)实体类
 */
public class Volunteer implements Serializable {
    private static final long serialVersionUID = 1L;

    private String volunteerId;
    private String name;
    private String password;
    private String phone;
    private Integer gender;
    private String identity;
    private String nation;
    private String email;
    private String area;
    private List<String> teamNames; // 新添加的字段，用于表示志愿者所属团队名称列表

    private String teamName;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getServiceHours() {
        return serviceHours;
    }

    @Override
    public String toString() {
        return "Volunteer{" +
                "volunteerId='" + volunteerId + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", gender=" + gender +
                ", identity='" + identity + '\'' +
                ", nation='" + nation + '\'' +
                ", email='" + email + '\'' +
                ", area='" + area + '\'' +
                ", teamNames=" + teamNames +
                ", serviceHours=" + serviceHours +
                ", avatar='" + avatar + '\'' +
                '}';
    }

    public void setServiceHours(Integer serviceHours) {
        this.serviceHours = serviceHours;
    }

    private Integer serviceHours;

    public Volunteer(String volunteerId, String name, String password, String phone, Integer gender, String identity, String nation, String email, String area, List<String> teamNames, Integer serviceHours, String avatar) {
        this.volunteerId = volunteerId;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.gender = gender;
        this.identity = identity;
        this.nation = nation;
        this.email = email;
        this.area = area;
        this.teamNames = teamNames;
        this.serviceHours = serviceHours;
        this.avatar = avatar;
    }

    private String avatar;

    public Volunteer(String volunteerId, String name, String password, String phone, Integer gender, String identity, String nation, String email, String area, List<String> teamNames, String avatar) {
        this.volunteerId = volunteerId;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.gender = gender;
        this.identity = identity;
        this.nation = nation;
        this.email = email;
        this.area = area;
        this.teamNames = teamNames;
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Volunteer() {
    }

    public Volunteer(String volunteerId, String name, String password, String phone, Integer gender, String identity, String nation, String email, String area, List<String> teamNames) {
        this.volunteerId = volunteerId;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.gender = gender;
        this.identity = identity;
        this.nation = nation;
        this.email = email;
        this.area = area;
        this.teamNames = teamNames;
    }

    public String getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(String volunteerId) {
        this.volunteerId = volunteerId;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public List<String> getTeamNames() {
        return teamNames;
    }

    public void setTeamNames(List<String> teamNames) {
        this.teamNames = teamNames;
    }






    //增加一项常量
    private boolean add;

    public boolean isAdd() {
        return add;
    }

    public void setAdd(boolean add) {
        this.add = add;
    }
}
