package com.hwadee.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * (News)实体类
 */
public class News implements Serializable {
    private static final long serialVersionUID = 1L;

    private int newsId;
    private String teamId;
    private String newsPath;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    private Date newsTime;

    public News(int newsId, String teamId, String newsPath, String title, Date newsTime, Integer headline, Integer state) {
        this.newsId = newsId;
        this.teamId = teamId;
        this.newsPath = newsPath;
        this.title = title;
        this.newsTime = newsTime;
        this.headline = headline;
        this.state = state;
    }

    public News(int newsId, String teamId, String newsPath, Date newsTime, Integer headline, Integer state) {
        this.newsId = newsId;
        this.teamId = teamId;
        this.newsPath = newsPath;
        this.newsTime = newsTime;
        this.headline = headline;
        this.state = state;
    }

    public News() {
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getNewsPath() {
        return newsPath;
    }

    public void setNewsPath(String newsPath) {
        this.newsPath = newsPath;
    }

    public Date getNewsTime() {
        return newsTime;
    }

    public void setNewsTime(Date newsTime) {
        this.newsTime = newsTime;
    }

    public Integer getHeadline() {
        return headline;
    }

    public void setHeadline(Integer headline) {
        this.headline = headline;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    private Integer headline;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    private Integer state;
    private String content;
    private String tag;

    @Override
    public String toString() {
        return "News{" +
                "newsId=" + newsId +
                ", teamId='" + teamId + '\'' +
                ", newsPath='" + newsPath + '\'' +
                ", title='" + title + '\'' +
                ", newsTime=" + newsTime +
                ", headline=" + headline +
                ", state=" + state +
                ", content='" + content + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
