package com.hwadee.springboot.dao;

import com.hwadee.springboot.entity.News;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NewsDao {
    /**
     * 分页查询所有新闻
     *
     * @param offset 偏移量
     * @param limit 每页显示的数据条数
     * @return 新闻列表
     */
    List<News> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 查询符合条件的总数
     *
     * @return 符合条件的总数
     */
    int count();

    /**
     * 分页查询所有头条新闻
     *
     * @param offset 偏移量
     * @param limit 每页显示的数据条数
     * @return 头条新闻列表
     */
    List<News> queryAllHeadlineByLimit(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 分页查询所有非头条新闻
     *
     * @param offset 偏移量
     * @param limit 每页显示的数据条数
     * @return 非头条新闻列表
     */
    List<News> queryAllNotHeadlineByLimit(@Param("offset") int offset, @Param("limit") int limit);

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
     * @return 更新条数
     */
    int updateNewsState(@Param("newsId") int newsId, @Param("state") int state);

    /**
     * 更新新闻头条状态
     *
     * @param newsId 新闻ID
     * @param headline 新的头条状态
     * @return 更新条数
     */
    int updateNewsHeadline(@Param("newsId") int newsId, @Param("headline") int headline);

    /**
     * 获取新闻头条状态
     *
     * @param newsId 新闻ID
     * @return 新闻头条状态
     */
    int getNewsHeadline(@Param("newsId") int newsId);


    /**
     * 插入新闻
     *
     * @param news 新闻实例
     * @return 影响的行数
     */
    int insertNews(@Param("news") News news);


    /**
     * 根据新闻ID查询新闻信息
     *
     * @param newsId 新闻ID
     * @return 新闻实例
     */
    News getNewsById(@Param("newsId") int newsId);

}
