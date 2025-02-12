package com.hwadee.springboot.service;

import com.hwadee.springboot.entity.VolunteerActivity;

import java.util.List;
import java.util.Map;

public interface VolunteerActivityService {
    /**
     * 分页查询所有志愿活动
     *
     * @param page 当前页数
     * @param limit 每页显示的数据条数
     * @param name 活动名称（用于模糊搜索）
     *  @param location  未知
     * @param sort 分类
     * @return 志愿活动列表
     */
    List<VolunteerActivity> getAllActivities(int page, int limit, String name,String location,String sort);

    /**
     * 查询符合条件的总数
     *
     * @param name 活动名称（用于模糊搜索）
     * @return 符合条件的总数
 *  @param location  未知
 * @param sort 分类
     */
    int count(String name,String location,String sort);

    /**
     * 根据志愿团队ID获取该团队发布的所有志愿活动
     *
     * @param teamId 志愿团队ID
     * @return 志愿活动列表
     */
    List<VolunteerActivity> getActivityOfTeam(String teamId);



    /**
     * 插入志愿活动
     *
     * @param volunteerActivity 志愿活动实例
     * @return 是否成功
     */
    boolean insertActivity(VolunteerActivity volunteerActivity);

    /**
     * 更新志愿活动
     *
     * @param volunteerActivity 志愿活动实例
     * @return 是否成功
     */
    boolean updateActivity(VolunteerActivity volunteerActivity);

    /**
     * 删除志愿活动
     *
     * @param activityId 志愿活动ID
     * @return 是否成功
     */
    boolean deleteActivity(Long activityId);

    /**
     * 根据志愿活动ID查询志愿活动信息
     *
     * @param activityId 志愿活动ID
     * @return 志愿活动实例
     */
    VolunteerActivity getActivityByActivityId(int activityId);


    /**
     * 根据团队ID查询志愿活动数量
     *
     * @param teamId 志愿团队ID
     * @return 志愿活动数量
     */
    int getActivityNumByTeamId(String teamId);





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
    int updateActivityState(int activityId, int state);







    /**
     * 根据志愿者ID和年份查询志愿者每天的志愿时长
     *
     * @param year 年份
     * @param volunteerId 志愿者ID
     * @return 志愿者每天的志愿时长
     */
    List<Map<String, Object>> getVolunteerHoursPerDayForYear(int year, String volunteerId);

}
