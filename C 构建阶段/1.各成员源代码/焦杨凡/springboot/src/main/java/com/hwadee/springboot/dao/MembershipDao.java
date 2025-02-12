package com.hwadee.springboot.dao;

import com.hwadee.springboot.entity.Membership;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MembershipDao {
    /**
     * 插入志愿者与志愿团队的关系记录
     *
     * @param membership 关系记录
     * @return 影响行数
     */
    int insert(Membership membership);

    /**
     * 根据志愿者ID和团队ID查询关系记录
     *
     * @param volunteerId 志愿者ID
     * @param teamId 团队ID
     * @return 关系记录
     */
    Membership findByVolunteerIdAndTeamId(@Param("volunteerId") String volunteerId, @Param("teamId") String teamId);

    /**
     * 更新关系记录的状态
     *
     * @param volunteerId 志愿者ID
     * @param teamId 团队ID
     * @param state 状态
     * @return 影响行数
     */
    int updateState(@Param("volunteerId") String volunteerId, @Param("teamId") String teamId, @Param("state") int state);


    /**
     * 根据志愿者ID查询相关志愿团队记录
     *
     * @param volunteerId 志愿者ID
     * @return 志愿团队记录列表
     */
    List<Membership> queryAllTeamsByVolunteerId(@Param("volunteerId") String volunteerId);

    /**
     * 根据志愿者ID查询不同状态的志愿团队数量
     *
     * @param volunteerId 志愿者ID
     * @param state 志愿团队状态
     * @return 不同状态的志愿团队数量
     */
    Integer queryTeamCountByState(@Param("volunteerId") String volunteerId, @Param("state") Integer state);

    /**
     * 检查志愿者是否已加入某个志愿团队
     *
     * @param volunteerId 志愿者ID
     * @param teamId 志愿团队ID
     * @return 团队关系记录数量
     */
    int checkVolunteerTeamStatus(@Param("volunteerId") String volunteerId, @Param("teamId") String teamId);


    /**
     * 根据志愿团队ID查询加入志愿团队的志愿者信息
     *
     * @param teamId 志愿团队ID
     * @return 志愿团队成员列表
     */
    List<Membership> getVolunteerByTeamId(@Param("teamId") String teamId);

    /**
     * 根据志愿团队ID查询加入志愿团队的志愿者人数统计
     *
     * @param teamId 志愿团队ID
     * @return 志愿团队成员人数统计
     */
    Map<String, Integer> getVolunteerNumByTeamId(@Param("teamId") String teamId);

    /**
     * 更新志愿者与志愿团队的关系状态
     *
     * @param membership 更新的志愿者与志愿团队的关系
     * @return 影响的行数
     */
    int updateMembership(@Param("membership") Membership membership);


    /**
     * 删除志愿者申请的志愿团队记录
     *
     * @param volunteerId 志愿者ID
     * @param teamId 志愿团队ID
     * @return 影响的行数
     */
    int deleteApplyForTeam(@Param("volunteerId") String volunteerId, @Param("teamId") String teamId);

    /**
     * 根据志愿者ID和团队ID查询组织关系
     *
     * @param volunteerId 志愿者ID
     * @param teamId 志愿团队ID
     * @return 组织关系记录
     */
    Membership getMembership(@Param("volunteerId") String volunteerId, @Param("teamId") String teamId);



    /**
     * 检查志愿者申请志愿团队情况
     *
     * @param volunteerId 志愿者ID
     * @param teamId 团队ID
     * @return 志愿者团队记录
     */
    Membership checkVolunteerTeam(@Param("volunteerId") String volunteerId, @Param("teamId") String teamId);

}
