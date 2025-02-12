package com.hwadee.springboot.dao;

import com.hwadee.springboot.entity.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VideoDao {

    void insertVideo(Video video);

    Video getVideoById(int videoId);

    void updateVideoPath(Video video);


    /**
     * 获取视频列表
     *
     * @param offset 偏移量
     * @param limit 每页显示的数据条数
     * @return 视频列表
     */
    List<Video> getVideoList(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 获取视频总数
     *
     * @return 视频总数
     */
    int countVideos();
}
