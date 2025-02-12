package com.hwadee.springboot.dao;

import com.hwadee.springboot.entity.VolunteerServiceRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VolunteerServiceRecordDao {
    /**
     * 插入志愿者服务记录
     *
     * @param volunteerServiceRecord 志愿者服务记录
     * @return 影响行数
     */
    int insert(VolunteerServiceRecord volunteerServiceRecord);

    /**
     * 根据志愿者ID和活动ID查询志愿者服务记录
     *
     * @param volunteerId 志愿者ID
     * @param activityId 活动ID
     * @return 志愿者服务记录
     */
    VolunteerServiceRecord findByVolunteerIdAndActivityId(@Param("volunteerId") String volunteerId, @Param("activityId") int activityId);

    /**
     * 更新志愿者服务记录的状态
     *
     * @param volunteerId 志愿者ID
     * @param activityId 活动ID
     * @param state 状态
     * @return 影响行数
     */
    int updateState(@Param("volunteerId") String volunteerId, @Param("activityId") int activityId, @Param("state") int state);

    /**
     * 根据志愿者ID查询相关志愿活动记录
     *
     * @param volunteerId 志愿者ID
     * @return 志愿服务记录列表
     */
    List<VolunteerServiceRecord> queryAllRecordsByVolunteerId(@Param("volunteerId") String volunteerId);

    /**
     * 根据志愿者ID查询志愿服务时长总和
     *
     * @param volunteerId 志愿者ID
     * @return 志愿服务时长总和
     */
    Integer queryTotalServiceHours(@Param("volunteerId") String volunteerId);

    /**
     * 根据志愿者ID查询不同状态的志愿活动数量
     *
     * @param volunteerId 志愿者ID
     * @param state 志愿活动状态
     * @return 不同状态的志愿活动数量
     */
    Integer queryActivityCountByState(@Param("volunteerId") String volunteerId, @Param("state") Integer state);


    /**
     * 检查志愿者是否已加入某个志愿活动
     *
     * @param volunteerId 志愿者ID
     * @param activityId 志愿活动ID
     * @return 活动记录数量
     */
    int checkVolunteerActivityStatus(@Param("volunteerId") String volunteerId, @Param("activityId") Integer activityId);

    /**
     * 根据志愿活动ID查询加入志愿活动的志愿者ID和总人数
     *
     * @param activityId 志愿活动ID
     * @return 志愿服务记录列表
     */
    List<VolunteerServiceRecord> getVolunteerIdByActivityId(@Param("activityId") Long activityId);

    /**
     * 根据志愿活动ID查询加入志愿活动的志愿者ID和人数统计
     *
     * @param activityId 志愿活动ID
     * @return 志愿服务记录和人数统计
     */
    List<VolunteerServiceRecord> getVolunteerIdNumByActivityId(@Param("activityId") Long activityId);

//    /**
//     * 根据志愿活动ID查询加入志愿活动的志愿者信息
//     *
//     * @param activityId 志愿活动ID
//     * @return 志愿服务记录列表
//     */
//    List<VolunteerServiceRecord> getVolunteerIdByActivityId(@Param("activityId") int activityId);
//
//    /**
//     * 根据志愿活动ID查询加入志愿活动的志愿者人数统计
//     *
//     * @param activityId 志愿活动ID
//     * @return 志愿服务记录和人数统计
//     */
//    Map<String, Integer> getVolunteerIdNumByActivityId(@Param("activityId") int activityId);

    /**
     * 更新志愿者与志愿活动的服务记录
     *
     * @param volunteerServiceRecord 更新的志愿者与志愿活动的服务记录
     * @return 影响的行数
     */
    int updateVolunteerServiceRecord(@Param("volunteerServiceRecord") VolunteerServiceRecord volunteerServiceRecord);


    /**
     * 根据志愿者ID和志愿活动ID查询对应的志愿时长
     *
     * @param volunteerId 志愿者ID
     * @param activityId 志愿活动ID
     * @return 志愿时长
     */
    Integer getVolunteerServiceHoursPerActivity(@Param("volunteerId") String volunteerId, @Param("activityId") int activityId);

    /**
     * 删除志愿者申请的志愿活动记录
     *
     * @param volunteerId 志愿者ID
     * @param activityId 志愿活动ID
     * @return 影响的行数
     */
    int deleteApplyForActivity(@Param("volunteerId") String volunteerId, @Param("activityId") int activityId);


    /**
     * 根据志愿者ID和志愿活动ID查询活动记录
     *
     * @param volunteerId 志愿者ID
     * @param activityId 志愿活动ID
     * @return 志愿服务记录
     */
    VolunteerServiceRecord getServiceRecord(@Param("volunteerId") String volunteerId, @Param("activityId") int activityId);





    /**
     * 根据团队ID计算团队中所有志愿者的总志愿时长
     *
     * @param teamId 团队ID
     * @return 总志愿时长
     */
    Integer getTeamTotalTime(@Param("teamId") String teamId);


    /**
     * 根据团队ID获取志愿活动中已加入的志愿者记录
     *
     * @param teamId 团队ID
     * @return 志愿者服务记录列表
     */
    List<VolunteerServiceRecord> getVolunteerActivityByTeamId(@Param("teamId") String teamId);











    /**
     * 根据志愿者ID查询志愿者近五年志愿时长分布
     *
     * @param volunteerId 志愿者ID
     * @return 志愿者近五年志愿时长分布记录
     */
    List<VolunteerServiceRecord> getServiceHourStatistics(@Param("volunteerId") String volunteerId);








    /**
     * 获取志愿者的总志愿时长
     *
     * @param volunteerId 志愿者ID
     * @return 总志愿时长
     */
    Integer getTotalServiceHoursByVolunteerId(@Param("volunteerId") String volunteerId);

    /**
     * 获取所有志愿者的志愿时长
     *
     * @return 志愿时长列表
     */
    List<Integer> getAllVolunteerServiceHours();

    /**
     * 获取所有注册的志愿者人数
     *
     * @return 注册的志愿者人数
     */
    int getTotalVolunteerCount();


    /**
     * 检查志愿者申请志愿活动情况
     *
     * @param volunteerId 志愿者ID
     * @param activityId 活动ID
     * @return 志愿者活动记录
     */
    VolunteerServiceRecord checkVolunteerActivity(@Param("volunteerId") String volunteerId, @Param("activityId") int activityId);

}




