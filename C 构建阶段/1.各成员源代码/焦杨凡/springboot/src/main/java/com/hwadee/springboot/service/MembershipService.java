package com.hwadee.springboot.service;

import com.hwadee.springboot.entity.Membership;

import java.util.List;
import java.util.Map;

public interface MembershipService {
    /**
     * 插入志愿者与志愿团队的关系记录
     *
     * @param membership 关系记录
     * @return 插入结果
     */
    boolean joinTeam(Membership membership);

    /**
     * 根据志愿者ID和团队ID处理志愿者加入团队的逻辑
     *
     * @param volunteerId 志愿者ID
     * @param teamId 团队ID
     * @return 处理结果
     */
    String handleJoinTeam(String volunteerId, String teamId);


    /**
     * 根据志愿者ID查询相关志愿团队记录
     *
     * @param volunteerId 志愿者ID
     * @return 志愿团队记录列表
     */
    List<Membership> getAllTeamsByVolunteerId(String volunteerId);

    /**
     * 根据志愿者ID查询不同状态的志愿团队数量
     *
     * @param volunteerId 志愿者ID
     * @param state 志愿团队状态
     * @return 不同状态的志愿团队数量
     */
    Integer getTeamCountByState(String volunteerId, Integer state);

    /**
     * 检查志愿者是否已加入某个志愿团队
     *
     * @param volunteerId 志愿者ID
     * @param teamId 志愿团队ID
     * @return 团队关系记录数量
     */
    int checkVolunteerTeamStatus(String volunteerId, String teamId);

    /**
     * 根据志愿团队ID查询加入志愿团队的志愿者信息
     *
     * @param teamId 志愿团队ID
     * @return 志愿团队成员列表和统计信息
     */
    Map<String, Object> getVolunteerByTeamId(String teamId);

    /**
     * 更新志愿者与志愿团队的关系状态
     *
     * @param membership 更新的志愿者与志愿团队的关系
     * @return 更新结果
     */
    boolean updateMembership(Membership membership);

    /**
     * 删除志愿者申请的志愿团队记录
     *
     * @param volunteerId 志愿者ID
     * @param teamId 志愿团队ID
     * @return 删除结果
     */
    boolean deleteApplyForTeam(String volunteerId, String teamId);

    /**
     * 根据志愿者ID和团队ID查询组织关系
     *
     * @param volunteerId 志愿者ID
     * @param teamId 志愿团队ID
     * @return 组织关系记录
     */
    Membership getMembership(String volunteerId, String teamId);


    /**
     * 检查志愿者申请志愿团队情况
     *
     * @param volunteerId 志愿者ID
     * @param teamId 团队ID
     * @return 志愿者团队记录状态
     */
    Membership checkVolunteerTeam(String volunteerId, String teamId);
}
