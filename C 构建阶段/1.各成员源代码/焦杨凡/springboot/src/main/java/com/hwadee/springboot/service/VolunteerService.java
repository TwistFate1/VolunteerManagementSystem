package com.hwadee.springboot.service;

import com.hwadee.springboot.entity.Volunteer;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface VolunteerService {
    /**
     * 分页查询所有志愿者
     *
     * @param page 当前页数
     * @param limit 每页显示的数据条数
     * @param name 志愿者名称（用于模糊搜索）
     * @return 志愿者列表
     */
    List<Volunteer> getAllVolunteers(int page, int limit, String name);

    /**
     * 查询符合条件的总数
     *
     * @param name 志愿者名称（用于模糊搜索）
     * @return 符合条件的总数
     */
    int count(String name);






    //登录
    /**
     * 根据志愿者ID查询志愿者信息
     *
     * @param volunteerId 志愿者ID
     * @return 志愿者信息
     */
    Volunteer queryById(String volunteerId);

    /**
     * 志愿者登录
     *
     * @param volunteerId 志愿者ID
     * @param password 志愿者密码
     * @return 志愿者信息
     */

    Volunteer login(String volunteerId, String password);
    /**
     * 注册志愿者
     *
     * @param volunteer 志愿者信息
     * @return 注册结果
     */
    Volunteer insertVolunteer(Volunteer volunteer);

    /**
     * 根据志愿者ID查询志愿者信息
     *
     * @param volunteerId 志愿者ID
     * @return 志愿者信息
     */
    Volunteer findByVolunteerId(String volunteerId);


    Volunteer getVolunteerWithTeams(String volunteerId);



    /**
     * 修改志愿者个人信息
     *
     * @param volunteer 志愿者实例
     * @return 是否成功
     */
    boolean updateVolunteerInfo(Volunteer volunteer);


    /**
     * 根据志愿活动ID查询参加该活动的志愿者信息
     *
     * @param activityId 志愿活动ID
     * @return 志愿者列表
     */
    List<Volunteer> getVolunteerDetailByActivityId(int activityId);

    /**
     * 根据团队ID查询参加该团队活动的志愿者信息
     *
     * @param teamId 志愿团队ID
     * @return 志愿者列表
     */
    List<Volunteer> getVolunteerDetailByTeamId(String teamId);













    /**
     * 上传志愿者头像
     *
     * @param volunteerId 志愿者ID
     * @param file 头像文件
     * @return 影响的行数
     */
    int uploadVolunteerAvatar(String volunteerId, MultipartFile file);


    /**
     * 根据志愿者ID获取头像文件
     *
     * @param volunteerId 志愿者ID
     * @return 头像文件
     */
    File getVolunteerAvatar(String volunteerId);



    /**
     * 获取所有志愿者的数量
     *
     * @return 志愿者数量
     */
    int getVolunteerNumber();

}
