package com.hwadee.springboot.entity;

import java.io.Serializable;

/**
 * (Membership)实体类
 */
public class Membership implements Serializable {
    private static final long serialVersionUID = 1L;

    private String volunteerId;
    private String teamId;
    private Integer state;


    public Membership() {
    }

    public String getVolunteerId() {
        return volunteerId;
    }

    @Override
    public String toString() {
        return "Membership{" +
                "volunteerId='" + volunteerId + '\'' +
                ", teamId='" + teamId + '\'' +
                ", state=" + state +
                '}';
    }

    public void setVolunteerId(String volunteerId) {
        this.volunteerId = volunteerId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Membership(String volunteerId, String teamId, Integer state) {
        this.volunteerId = volunteerId;
        this.teamId = teamId;
        this.state = state;
    }



}
