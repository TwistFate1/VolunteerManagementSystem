//package com.hwadee.springboot.service;
//
//import com.hwadee.springboot.entity.VolunteerTeam;
//
//import java.util.List;
//
//public interface VolunteerTeamService {
//    /**
//     * 分页查询所有志愿团队
//     *
//     * @param page 当前页数
//     * @param limit 每页显示的数据条数
//     * @param name 志愿团队名称（用于模糊搜索）
//     * @return 志愿团队列表
//     */
//    List<VolunteerTeam> getAllTeams(int page, int limit, String name);
//
//    /**
//     * 查询符合条件的总数
//     *
//     * @param name 志愿团队名称（用于模糊搜索）
//     * @return 符合条件的总数
//     */
//    int count(String name);
//}

package com.hwadee.springboot.service;

import com.hwadee.springboot.entity.VolunteerTeam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface VolunteerTeamService {
    /**
     * 分页查询所有志愿团队
     *
     * @param page 当前页数
     * @param limit 每页显示的数据条数
     * @param name 志愿团队名称（用于模糊搜索）
     * @return 志愿团队列表
     */
    List<VolunteerTeam> getAllTeams(int page, int limit, String name);

    /**
     * 查询符合条件的总数
     *
     * @param name 志愿团队名称（用于模糊搜索）
     * @return 符合条件的总数
     */
    int count(String name);

    /**
     * 根据团队ID查询团队信息
     *
     * @param teamId 团队ID
     * @return 团队信息
     */
    VolunteerTeam getTeamById(String teamId);








    /**
     * 志愿团队登录
     *
     * @param teamId 团队ID
     * @param password 团队密码
     * @return 团队信息
     */
    VolunteerTeam login(String teamId, String password);



    /**
     * 注册志愿团队
     *
     * @param volunteerTeam 志愿团队信息
     * @return 注册结果
     */
    VolunteerTeam insertVolunteerTeam(VolunteerTeam volunteerTeam);

    /**
     * 根据团队ID查询志愿团队信息
     *
     * @param teamId 团队ID
     * @return 志愿团队信息
     */
    VolunteerTeam findByTeamId(String teamId);





    /**
     * 更新志愿团队信息
     *
     * @param volunteerTeam 志愿团队信息
     * @return 受影响的行数
     */
    boolean updateTeamInfo(VolunteerTeam volunteerTeam);









    /**
     * 上传志愿团队头像
     *
     * @param teamId 志愿者ID
     * @param file 头像文件
     * @return 影响的行数
     */
    int uploadVolunteerTeamAvatar(String teamId, MultipartFile file);


    /**
     * 根据志愿团队ID获取头像文件
     *
     * @param teamId 志愿者ID
     * @return 头像文件
     */
    File getVolunteerTeamAvatar(String teamId);

    /**
     * 获取所有志愿团队的数量
     *
     * @return 志愿团队数量
     */
    int getTeamNumber();
}
