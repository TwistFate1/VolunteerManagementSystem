package com.hwadee.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * (VolunteerActivity)实体类
 */
public class VolunteerActivity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer activityId;
    private String teamId;
    private String name;

    public VolunteerActivity() {
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getNumberVolunteers() {
        return numberVolunteers;
    }

    public void setNumberVolunteers(Integer numberVolunteers) {
        this.numberVolunteers = numberVolunteers;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public VolunteerActivity(Integer activityId, String teamId, String name, String location, String content, Integer numberVolunteers, Date postDate, Date startDate, Date endDate, Integer state, String url) {
        this.activityId = activityId;
        this.teamId = teamId;
        this.name = name;
        this.location = location;
        this.content = content;
        this.numberVolunteers = numberVolunteers;
        this.postDate = postDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = state;
        this.url = url;
    }

    private String location;
    private String content;
    private Integer numberVolunteers;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    private Date postDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    private Date endDate;
    private Integer state;
    private String url;
    private String sort;

    @Override
    public String toString() {
        return "VolunteerActivity{" +
                "activityId=" + activityId +
                ", teamId='" + teamId + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", content='" + content + '\'' +
                ", numberVolunteers=" + numberVolunteers +
                ", postDate=" + postDate +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", state=" + state +
                ", url='" + url + '\'' +
                ", sort='" + sort + '\'' +
                '}';
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
