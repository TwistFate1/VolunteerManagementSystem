package com.hwadee.springboot.service;

import com.hwadee.springboot.entity.VolunteerServiceRecord;

import java.util.List;
import java.util.Map;

public interface VolunteerServiceRecordService {
    /**
     * 插入志愿者服务记录
     *
     * @param volunteerServiceRecord 志愿者服务记录
     * @return 插入结果
     */
    boolean joinActivity(VolunteerServiceRecord volunteerServiceRecord);

    /**
     * 根据志愿者ID和活动ID处理志愿者加入活动的逻辑
     *
     * @param volunteerId 志愿者ID
     * @param activityId 活动ID
     * @return 处理结果
     */
    String handleJoinActivity(String volunteerId, int activityId);








    /**
     * 根据志愿者ID查询相关志愿活动记录
     *
     * @param volunteerId 志愿者ID
     * @return 志愿服务记录列表
     */
    List<VolunteerServiceRecord> getAllRecordsByVolunteerId(String volunteerId);

    /**
     * 根据志愿者ID查询志愿服务时长总和
     *
     * @param volunteerId 志愿者ID
     * @return 志愿服务时长总和
     */
    Integer getTotalServiceHours(String volunteerId);

    /**
     * 根据志愿者ID查询不同状态的志愿活动数量
     *
     * @param volunteerId 志愿者ID
     * @param state 志愿活动状态
     * @return 不同状态的志愿活动数量
     */
    Integer getActivityCountByState(String volunteerId, Integer state);




    /**
     * 检查志愿者是否已加入某个志愿活动
     *
     * @param volunteerId 志愿者ID
     * @param activityId 志愿活动ID
     * @return 活动记录数量
     */
    int checkVolunteerActivityStatus(String volunteerId, Integer activityId);

    /**
     * 根据志愿活动ID查询加入志愿活动的志愿者ID和所有记录
     *
     * @param activityId 志愿活动ID
     * @return 志愿服务记录列表和总人数
     */
    Map<String, Object> getVolunteerIdByActivityId(Long activityId);

//    /**
//     * 根据志愿活动ID查询加入志愿活动的志愿者信息
//     *
//     * @param activityId 志愿活动ID
//     * @return 志愿服务记录列表和统计信息
//     */
//    Map<String, Object> getVolunteerIdByActivityId(int activityId);

    /**
     * 更新志愿者与志愿活动的服务记录
     *
     * @param volunteerServiceRecord 更新的志愿者与志愿活动的服务记录
     * @return 更新结果
     */
    boolean updateVolunteerServiceRecord(VolunteerServiceRecord volunteerServiceRecord);






    /**
     * 根据志愿者ID和志愿活动ID查询对应的志愿时长
     *
     * @param volunteerId 志愿者ID
     * @param activityId 志愿活动ID
     * @return 志愿时长
     */
    Integer getVolunteerServiceHoursPerActivity(String volunteerId, int activityId);

    /**
     * 删除志愿者申请的志愿活动记录
     *
     * @param volunteerId 志愿者ID
     * @param activityId 志愿活动ID
     * @return 删除结果
     */
    boolean deleteApplyForActivity(String volunteerId, int activityId);

    /**
     * 根据志愿者ID和志愿活动ID查询活动记录
     *
     * @param volunteerId 志愿者ID
     * @param activityId 志愿活动ID
     * @return 志愿服务记录
     */
    VolunteerServiceRecord getServiceRecord(String volunteerId, int activityId);



    /**
     * 根据团队ID计算团队中所有志愿者的总志愿时长
     *
     * @param teamId 团队ID
     * @return 总志愿时长
     */
    Integer getTeamTotalTime(String teamId);


    /**
     * 根据团队ID获取志愿活动中已加入的志愿者记录，并按活动分组
     *
     * @param teamId 团队ID
     * @return 分组后的志愿者服务记录
     */
    Map<String, Object> getVolunteerActivityByTeamId(String teamId);










    /**
     * 获取志愿者近五年志愿时长分布
     *
     * @param volunteerId 志愿者ID
     * @return 志愿者近五年志愿时长分布记录
     */
    Map<String, Object> getServiceHourStatistics(String volunteerId);






    /**
     * 根据志愿者ID查询该志愿者的总志愿时长超过了百分之多少的志愿者
     *
     * @param volunteerId 志愿者ID
     * @return 总志愿时长和百分比的Map
     */
    Map<String, Object> getPercentOfVolunteerServiceHours(String volunteerId);



    /**
     * 检查志愿者申请志愿活动情况
     *
     * @param volunteerId 志愿者ID
     * @param activityId 活动ID
     * @return 志愿者活动记录状态
     */
    VolunteerServiceRecord checkVolunteerActivity(String volunteerId, int activityId);

}
