package com.hwadee.springboot.controller;

import com.hwadee.springboot.entity.Result;
import com.hwadee.springboot.entity.News;
import com.hwadee.springboot.service.NewsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("news")
public class NewsController {
    @Resource
    private NewsService newsService;

    @PostMapping("/getAllNews")
    public Result getAllNews(@RequestBody Map<String, Object> request) {
        Result res = new Result();
        try {
            int page = (int) request.getOrDefault("page", 1);
            int limit = (int) request.getOrDefault("limit", 4);

            List<News> newsList = newsService.getAllNews(page, limit);
            int total = newsService.count();

            res.setCode(1);
            res.setResult(new Object() {
                public List<News> getNewsList() {
                    return newsList;
                }

                public int getTotal() {
                    return total;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(0);
            res.setResult("Error retrieving news");
        }
        return res;
    }

    @PostMapping("/getAllHeadline")
    public Result getAllHeadline(@RequestBody Map<String, Object> request) {
        Result res = new Result();
        try {
            int page = (int) request.getOrDefault("page", 1);
            int limit = (int) request.getOrDefault("limit", 4);

            List<News> newsList = newsService.getAllHeadline(page, limit);
            int total = newsService.countAllHeadline();

            res.setCode(1);
            res.setResult(new Object() {
                public List<News> getNewsList() {
                    return newsList;
                }

                public int getTotal() {
                    return total;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error retrieving headline news: " + e.getMessage());
        }
        return res;
    }

    @PostMapping("/getAllNotHeadlineNews")
    public Result getAllNotHeadlineNews(@RequestBody Map<String, Object> request) {
        Result res = new Result();
        try {
            int page = (int) request.getOrDefault("page", 1);
            int limit = (int) request.getOrDefault("limit", 4);

            List<News> newsList = newsService.getAllNotHeadline(page, limit);
            int total = newsService.countAllNotHeadline();

            res.setCode(1);
            res.setResult(new Object() {
                public List<News> getNewsList() {
                    return newsList;
                }

                public int getTotal() {
                    return total;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error retrieving non-headline news: " + e.getMessage());
        }
        return res;
    }



























    /**
     * 查询所有非头条新闻
     *
     * @return 新闻列表
     */
    @PostMapping("/getNewsAudit")
    public Result getNewsAudit() {
        Result res = new Result();
        try {
            List<News> result = newsService.getNewsAudit();
            res.setCode(1);
            res.setResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error fetching news audit: " + e.getMessage());
        }
        return res;
    }

    /**
     * 更新新闻审核状态
     *
     * @param data 新闻ID和状态
     * @return 成功或失败信息
     */
    @PostMapping("/updateNewsState")
    public Result updateNewsState(@RequestBody Map<String, Integer> data) {
        Result res = new Result();
        try {
            int newsId = data.get("newsId");
            int state = data.get("state");
            String result = newsService.updateNewsState(newsId, state);
            res.setCode(result.equals("Update successful") ? 1 : 0);
            res.setResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error updating news state: " + e.getMessage());
        }
        return res;
    }

    /**
     * 更新新闻头条状态
     *
     * @param data 新闻ID
     * @return 成功或失败信息
     */
    @PostMapping("/updateNewsHeadline")
    public Result updateNewsHeadline(@RequestBody Map<String, Integer> data) {
        Result res = new Result();
        try {
            int newsId = data.get("newsId");
            String result = newsService.updateNewsHeadline(newsId);
            res.setCode(result.equals("Update successful") ? 1 : 0);
            res.setResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error updating news headline: " + e.getMessage());
        }
        return res;
    }

    /**
     * 上传新闻
     *
     * @param news 新闻实例
     * @return 结果
     */
    @PostMapping("/uploadNews")
    public Result uploadNews(@RequestBody News news) {
        Result res = new Result();
        try {
            int result = newsService.uploadNews(news);
            if (result > 0) {
                res.setCode(1);
                res.setResult("Upload successful");
            } else {
                res.setCode(0);
                res.setResult("Upload failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error uploading news: " + e.getMessage());
        }
        return res;
    }


    /**
     * 根据新闻ID查询新闻信息
     *
     * @param data 请求数据，包括新闻ID
     * @return 新闻实例
     */
    @PostMapping("/getNewsById")
    public Result getNewsById(@RequestBody Map<String, Integer> data) {
        Result res = new Result();
        try {
            int newsId = data.get("newsId");
            News result = newsService.getNewsById(newsId);
            res.setCode(1);
            res.setResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error fetching news by ID: " + e.getMessage());
        }
        return res;
    }
}
