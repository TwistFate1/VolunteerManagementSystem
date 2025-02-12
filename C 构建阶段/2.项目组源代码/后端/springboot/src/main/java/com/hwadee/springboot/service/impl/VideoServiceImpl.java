package com.hwadee.springboot.service.impl;

import com.hwadee.springboot.dao.VideoDao;
import com.hwadee.springboot.entity.Video;
import com.hwadee.springboot.service.VideoService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service("videoService")
public class VideoServiceImpl implements VideoService {

    @Resource
    private VideoDao videoDao;

    @Override
    public Video uploadVideo(Video video) {
        videoDao.insertVideo(video);
        return videoDao.getVideoById(video.getVideoId());
    }

    @Override
    public String saveVideoFile(MultipartFile file, int videoId) throws IOException {
        String directory = "E:\\shixi\\video\\";
        Files.createDirectories(Paths.get(directory));
        String filePath = directory + videoId + ".mp4";
//        String filePath =videoId + ".mp4";
        File videoFile = new File(filePath);
        file.transferTo(videoFile);
        return filePath;
    }

    @Override
    public void updateVideoPath(Video video) {
        videoDao.updateVideoPath(video);
    }



    @Override
    public List<Video> getVideoList(int page, int limit) {
        int offset = (page - 1) * limit;
        return videoDao.getVideoList(offset, limit);
    }

    @Override
    public int countVideos() {
        return videoDao.countVideos();
    }
}
