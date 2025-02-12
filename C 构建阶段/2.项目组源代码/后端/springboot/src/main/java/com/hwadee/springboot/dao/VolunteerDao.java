package com.hwadee.springboot.dao;

import com.hwadee.springboot.entity.Volunteer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VolunteerDao {
    /**
     * 分页查询所有志愿者
     *
     * @param offset 偏移量
     * @param limit 每页显示的数据条数
     * @param name 志愿者名称（用于模糊搜索）
     * @return 志愿者列表
     */
    List<Volunteer> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit, @Param("name") String name);

    /**
     * 查询符合条件的总数
     *
     * @param name 志愿者名称（用于模糊搜索）
     * @return 符合条件的总数
     */
    int count(@Param("name") String name);








//    登录
    /**
     * 根据志愿者ID查询志愿者信息
     *
     * @param volunteerId 志愿者ID
     * @return 志愿者信息
     */
    Volunteer queryById(@Param("volunteerId") String volunteerId);

    /**
     * 根据志愿者ID和密码查询志愿者信息
     *
     * @param volunteerId 志愿者ID
     * @param password 志愿者密码
     * @return 志愿者信息
     */
    Volunteer login(@Param("volunteerId") String volunteerId, @Param("password") String password);

    /**
     * 插入志愿者信息
     *
     * @param volunteer 志愿者信息
     * @return 影响行数
     */
    int insert(Volunteer volunteer);

    /**
     * 根据志愿者ID查询志愿者信息
     *
     * @param volunteerId 志愿者ID
     * @return 志愿者信息
     */
    Volunteer findByVolunteerId(@Param("volunteerId") String volunteerId);
    /**
     * 根据志愿者ID查询志愿者及其所属团队信息
     *
     * @param volunteerId 志愿者ID
     * @return 志愿者及其所属团队信息
     */
    Volunteer getVolunteerWithTeams(@Param("volunteerId") String volunteerId);

    /**
     * 修改志愿者个人信息
     *
     * @param volunteer 志愿者实例
     * @return 影响行数
     */
    int updateVolunteerInfo(@Param("volunteer") Volunteer volunteer);

    /**
     * 根据志愿活动ID查询参加该活动的志愿者信息
     *
     * @param activityId 志愿活动ID
     * @return 志愿者列表
     */
    List<Volunteer> getVolunteerDetailByActivityId(@Param("activityId") int activityId);

    /**
     * 根据团队ID查询参加该团队活动的志愿者信息
     *
     * @param teamId 志愿团队ID
     * @return 志愿者列表
     */
    List<Volunteer> getVolunteerDetailByTeamId(@Param("teamId") String teamId);






    /**
     * 获取所有志愿者的数量
     *
     * @return 志愿者数量
     */
    int getVolunteerNumber();
}
