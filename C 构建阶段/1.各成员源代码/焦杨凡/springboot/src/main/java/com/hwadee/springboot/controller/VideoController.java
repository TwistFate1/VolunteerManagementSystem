package com.hwadee.springboot.controller;

import com.hwadee.springboot.entity.Result;
import com.hwadee.springboot.entity.Video;
import com.hwadee.springboot.service.VideoService;
import org.springframework.core.io.UrlResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("video")
public class VideoController {

    @Resource
    private VideoService videoService;

    /**
     * 上传视频
     *
     * @param file 视频文件
     * @param videoName 视频名称
     * @param postDate 发布日期
     * @param content 内容
     * @param tag 标签
     * @param state 状态
     * @param introduction 简介
     * @return 上传结果
     */
    @PostMapping("/uploadVideo")
    public Result uploadVideo(@RequestParam("file") MultipartFile file,
                              @RequestParam(value = "videoName", required = false) String videoName,
                              @RequestParam(value = "postDate", required = false)  @DateTimeFormat(pattern = "yyyy-MM-dd") Date postDate,
                              @RequestParam(value = "content", required = false) String content,
                              @RequestParam(value = "tag", required = false) String tag,
                              @RequestParam(value = "state", required = false) Integer state,
                              @RequestParam(value = "introduction", required = false) String introduction) {
        Result res = new Result();
        try {

            Video video = new Video();
            video.setVideoName(videoName);
            video.setpostDate(postDate);
            video.setContent(content);
            video.setTag(tag);
            video.setState(state);
            video.setIntroduction(introduction);

            Video savedVideo = videoService.uploadVideo(video);
            String videoPath = videoService.saveVideoFile(file, savedVideo.getVideoId());
            savedVideo.setVideoPath(videoPath);
            videoService.updateVideoPath(savedVideo); // Update video path in the database

            res.setCode(1);
            res.setResult(savedVideo);
        } catch (IOException e) {
            e.printStackTrace();
            res.setCode(0);
            res.setResult("Error uploading video: " + e.getMessage());
        }
        return res;
    }



    @PostMapping("/getVideoInfo")
    public Result getVideoInfo(@RequestBody Map<String, Integer> data) {
        Result res = new Result();
        try {
            int page = data.getOrDefault("page", 1);
            int limit = data.getOrDefault("limit", 4);
            List<Video> videos = videoService.getVideoList(page, limit);
            int total = videoService.countVideos();

            Map<String, Object> result = new HashMap<>();
            result.put("videos", videos);
            result.put("total", total);

            res.setCode(1);
            res.setResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error fetching video info: " + e.getMessage());
        }
        return res;
    }



//    /**
//     * 获取视频文件
//     *
//     * @param data 包含视频文件路径的请求数据
//     * @return 视频文件
//     */
//    @PostMapping("/getVideoFile")
//    public ResponseEntity<Resource> getVideoFile(@RequestBody Map<String, String> data) {
//        try {
//            String videoPath = data.get("videoPath");
//            Path path = Paths.get(videoPath);
//            Resource resource = new UrlResource(path.toUri());
//
//            if (resource.exists() || resource.isReadable()) {
//                return ResponseEntity.ok()
//                        .contentType(MediaType.parseMediaType("video/mp4"))
//                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//                        .body(resource);
//            } else {
//                throw new RuntimeException("File not found or not readable");
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body(null);
//        }
//    }


    /**
     * 获取视频文件
     *
     * @param data 包含视频文件路径的请求数据
     * @return 视频文件的字节数组
     */
    @PostMapping("/getVideoFile")
    public ResponseEntity<byte[]> getVideoFile(@RequestBody Map<String, String> data) {
        try {
            String videoPath = data.get("videoPath");
            Path path = Paths.get(videoPath);

            if (Files.exists(path) && Files.isReadable(path)) {
                byte[] videoBytes = Files.readAllBytes(path);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType("video/mp4"));
                headers.setContentDispositionFormData("attachment", path.getFileName().toString());
                return ResponseEntity.ok().headers(headers).body(videoBytes);
            } else {
                throw new RuntimeException("File not found or not readable");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
