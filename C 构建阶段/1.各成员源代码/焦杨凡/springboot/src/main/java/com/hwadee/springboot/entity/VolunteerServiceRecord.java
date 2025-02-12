package com.hwadee.springboot.entity;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * (VolunteerServiceRecord)实体类
 */
public class VolunteerServiceRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer activityId;
    private String volunteerId;
    private Integer serviceHours;
    private Integer state;

    public VolunteerServiceRecord() {
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(String volunteerId) {
        this.volunteerId = volunteerId;
    }

    public Integer getServiceHours() {
        return serviceHours;
    }

    public void setServiceHours(Integer serviceHours) {
        this.serviceHours = serviceHours;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public VolunteerServiceRecord(Integer activityId, String volunteerId, Integer serviceHours, Integer state) {
        this.activityId = activityId;
        this.volunteerId = volunteerId;
        this.serviceHours = serviceHours;
        this.state = state;
    }



    private Date startDate;

    public Date getStartDate() {
        return startDate;
    }

    @Override
    public String toString() {
        return "VolunteerServiceRecord{" +
                "activityId=" + activityId +
                ", volunteerId='" + volunteerId + '\'' +
                ", serviceHours=" + serviceHours +
                ", state=" + state +
                ", startDate=" + startDate +
                '}';
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
