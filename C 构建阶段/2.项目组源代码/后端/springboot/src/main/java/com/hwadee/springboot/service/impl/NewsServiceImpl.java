package com.hwadee.springboot.service.impl;

import com.hwadee.springboot.dao.NewsDao;
import com.hwadee.springboot.entity.News;
import com.hwadee.springboot.service.NewsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("newsService")
public class NewsServiceImpl implements NewsService {
    @Resource
    private NewsDao newsDao;

    @Override
    public List<News> getAllNews(int page, int limit) {
        int offset = (page - 1) * limit;
        return newsDao.queryAllByLimit(offset, limit);
    }

    @Override
    public int count() {
        return newsDao.count();
    }


    @Override
    public List<News> getAllHeadline(int page, int limit) {
        int offset = (page - 1) * limit;
        return newsDao.queryAllHeadlineByLimit(offset, limit);
    }

    @Override
    public List<News> getAllNotHeadline(int page, int limit) {
        int offset = (page - 1) * limit;
        return newsDao.queryAllNotHeadlineByLimit(offset, limit);
    }

    @Override
    public int countAllHeadline() {
        return newsDao.countAllHeadline();
    }

    @Override
    public int countAllNotHeadline() {
        return newsDao.countAllNotHeadline();
    }























    @Override
    public List<News> getNewsAudit() {
        return newsDao.getNewsAudit();
    }

    @Override
    public String updateNewsState(int newsId, int state) {
        int result = newsDao.updateNewsState(newsId, state);
        return result > 0 ? "Update successful" : "Update failed";
    }

    @Override
    public String updateNewsHeadline(int newsId) {
        int currentHeadline = newsDao.getNewsHeadline(newsId);
        int newHeadline = currentHeadline == 1 ? 0 : 1;
        int result = newsDao.updateNewsHeadline(newsId, newHeadline);
        return result > 0 ? "Update successful" : "Update failed";
    }


    @Override
    public int uploadNews(News news) {
        return newsDao.insertNews(news);
    }

    @Override
    public News getNewsById(int newsId) {
        return newsDao.getNewsById(newsId);
    }
}
