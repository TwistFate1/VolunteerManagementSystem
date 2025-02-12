package com.hwadee.springboot.dao;

import com.hwadee.springboot.entity.VolunteerActivity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface VolunteerActivityDao {
    /**
     * 分页查询所有志愿活动
     *
     * @param offset 偏移量
     * @param limit 每页显示的数据条数
     * @param name 活动名称（用于模糊搜索）
     * @param location    地点
     * @param sort 分类
     * @return 志愿活动列表
     */
    List<VolunteerActivity> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit, @Param("name") String name,@Param("location") String location,@Param("sort") String sort);

    /**
     * 查询符合条件的总数
     *
     * @param name 活动名称（用于模糊搜索）
     *@param location    地点
     * @param sort 分类
     * @return 符合条件的总数
     */
    int count(@Param("name") String name,@Param("location") String location,@Param("sort") String sort);


    /**
     * 根据志愿团队ID获取该团队发布的所有志愿活动
     *
     * @param teamId 志愿团队ID
     * @return 志愿活动列表
     */
    List<VolunteerActivity> getActivityOfTeam(@Param("teamId") String teamId);


    /**
     * 插入志愿活动
     *
     * @param volunteerActivity 志愿活动实例
     * @return 影响的行数
     */
    int insertActivity(@Param("volunteerActivity") VolunteerActivity volunteerActivity);

    /**
     * 更新志愿活动
     *
     * @param volunteerActivity 志愿活动实例
     * @return 影响的行数
     */
    int updateActivity(@Param("volunteerActivity") VolunteerActivity volunteerActivity);

    /**
     * 删除志愿活动
     *
     * @param activityId 志愿活动ID
     * @return 影响的行数
     */
    int deleteActivity(@Param("activityId") Long activityId);

    /**
     * 根据志愿活动ID查询志愿活动信息
     *
     * @param activityId 志愿活动ID
     * @return 志愿活动实例
     */
    VolunteerActivity getActivityByActivityId(@Param("activityId") int activityId);


    /**
     * 根据团队ID查询志愿活动数量
     *
     * @param teamId 志愿团队ID
     * @return 志愿活动数量
     */
    int getActivityNumByTeamId(@Param("teamId") String teamId);





    /**
     * 获取所有志愿活动的数量
     *
     * @return 志愿活动数量
     */
    int getActivityNumber();


    /**
     * 获取所有志愿活动的内容
     *
     * @return 所有志愿活动的列表
     */
    List<VolunteerActivity> getAllActivity();




    /**
     * 更新志愿活动状态
     *
     * @param activityId 志愿活动ID
     * @param state 状态
     * @return 影响的行数
     */
    int updateActivityState(@Param("activityId") int activityId, @Param("state") int state);







    /**
     * 根据志愿者ID和年份查询志愿者每天的志愿时长
     *
     * @param year 年份
     * @param volunteerId 志愿者ID
     * @return 志愿者每天的志愿时长
     */
    List<Map<String, Object>> getVolunteerHoursPerDayForYear(@Param("year") int year, @Param("volunteerId") String volunteerId);



}
