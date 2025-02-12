//package com.hwadee.springboot.dao;
//
//import com.hwadee.springboot.entity.VolunteerTeam;
//import org.apache.ibatis.annotations.Param;
//
//import java.util.List;
//
//public interface VolunteerTeamDao {
//    /**
//     * 分页查询所有志愿团队
//     *
//     * @param offset 偏移量
//     * @param limit 每页显示的数据条数
//     * @param name 志愿团队名称（用于模糊搜索）
//     * @return 志愿团队列表
//     */
//    List<VolunteerTeam> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit, @Param("name") String name);
//
//    /**
//     * 查询符合条件的总数
//     *
//     * @param name 志愿团队名称（用于模糊搜索）
//     * @return 符合条件的总数
//     */
//    int count(@Param("name") String name);
//}

package com.hwadee.springboot.dao;

import com.hwadee.springboot.entity.VolunteerTeam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VolunteerTeamDao {
    /**
     * 分页查询所有志愿团队
     *
     * @param offset 偏移量
     * @param limit 每页显示的数据条数
     * @param name 志愿团队名称（用于模糊搜索）
     * @return 志愿团队列表
     */
    List<VolunteerTeam> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit, @Param("name") String name);

    /**
     * 查询符合条件的总数
     *
     * @param name 志愿团队名称（用于模糊搜索）
     * @return 符合条件的总数
     */
    int count(@Param("name") String name);

    /**
     * 根据团队ID查询团队信息
     *
     * @param teamId 团队ID
     * @return 团队信息
     */
    VolunteerTeam queryById(@Param("teamId") String teamId);



    //登录
    VolunteerTeam login(@Param("teamId") String teamId, @Param("password") String password);
    /**
     * 插入志愿团队信息
     *
     * @param volunteerTeam 志愿团队信息
     * @return 影响行数
     */
    int insert(VolunteerTeam volunteerTeam);

    /**
     * 根据团队ID查询志愿团队信息
     *
     * @param teamId 团队ID
     * @return 志愿团队信息
     */
    VolunteerTeam findByTeamId(@Param("teamId") String teamId);

    /**
     * 更新志愿团队信息
     *
     * @param volunteerTeam 志愿团队信息
     * @return 受影响的行数
     */
    int updateTeamInfo(@Param("volunteerTeam") VolunteerTeam volunteerTeam);


    /**
     * 获取所有志愿团队的数量
     *
     * @return 志愿团队数量
     */
    int getTeamNumber();

}
