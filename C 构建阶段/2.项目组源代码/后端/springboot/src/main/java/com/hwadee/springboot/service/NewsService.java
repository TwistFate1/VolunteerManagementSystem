package com.hwadee.springboot.service;

import com.hwadee.springboot.entity.News;

import java.util.List;

public interface NewsService {
    /**
     * 分页查询所有新闻
     *
     * @param page 当前页数
     * @param limit 每页显示的数据条数
     * @return 新闻列表
     */
    List<News> getAllNews(int page, int limit);

    /**
     * 查询符合条件的总数
     *
     * @return 符合条件的总数
     */
    int count();


    /**
     * 分页查询所有头条新闻
     *
     * @param page 当前页数
     * @param limit 每页显示的数据条数
     * @return 头条新闻列表
     */
    List<News> getAllHeadline(int page, int limit);

    /**
     * 分页查询所有非头条新闻
     *
     * @param page 当前页数
     * @param limit 每页显示的数据条数
     * @return 非头条新闻列表
     */
    List<News> getAllNotHeadline(int page, int limit);

    /**
     * 查询符合条件的头条新闻总数
     *
     * @return 符合条件的头条新闻总数
     */
    int countAllHeadline();

    /**
     * 查询符合条件的非头条新闻总数
     *
     * @return 符合条件的非头条新闻总数
     */
    int countAllNotHeadline();


















    /**
     * 查询所有非头条新闻
     *
     * @return 新闻列表
     */
    List<News> getNewsAudit();

    /**
     * 更新新闻审核状态
     *
     * @param newsId 新闻ID
     * @param state 新的审核状态
     * @return 成功或失败信息
     */
    String updateNewsState(int newsId, int state);

    /**
     * 更新新闻头条状态
     *
     * @param newsId 新闻ID
     * @return 成功或失败信息
     */
    String updateNewsHeadline(int newsId);

    /**
     * 上传新闻
     *
     * @param news 新闻实例
     * @return 影响的行数
     */
    int uploadNews(News news);



    /**
     * 根据新闻ID查询新闻信息
     *
     * @param newsId 新闻ID
     * @return 新闻实例
     */
    News getNewsById(int newsId);
}
