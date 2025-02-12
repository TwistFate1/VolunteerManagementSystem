package com.hwadee.springboot.service;

import com.hwadee.springboot.entity.Video;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VideoService {

    /**
     * 上传视频
     *
     * @param video 视频实体
     * @return 上传的视频实体
     */
    Video uploadVideo(Video video);

    /**
     * 保存视频文件
     *
     * @param file 视频文件
     * @param videoId 视频ID
     * @return 保存的视频文件路径
     * @throws IOException 异常
     */
    String saveVideoFile(MultipartFile file, int videoId) throws IOException;

    /**
     * 更新视频路径
     *
     * @param video 视频实体
     */
    void updateVideoPath(Video video);


    /**
     * 获取视频列表
     *
     * @param page 当前页数
     * @param limit 每页显示的数据条数
     * @return 视频列表
     */
    List<Video> getVideoList(int page, int limit);

    /**
     * 获取视频总数
     *
     * @return 视频总数
     */
    int countVideos();
}
