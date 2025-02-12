package com.hwadee.springboot.dao;

import com.hwadee.springboot.entity.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminDao {
    /**
     * 根据管理员ID和密码查询管理员信息
     *
     * @param adminId 管理员ID
     * @param password 管理员密码
     * @return 管理员信息
     */
    Admin login(@Param("adminId") String adminId, @Param("password") String password);

    /**
     * 插入管理员信息
     *
     * @param admin 管理员信息
     * @return 影响行数
     */
    int insert(Admin admin);

    /**
     * 根据管理员ID查询管理员信息
     *
     * @param adminId 管理员ID
     * @return 管理员信息
     */
    Admin findByAdminId(@Param("adminId") String adminId);


    /**
     * 根据管理员ID更新管理员信息
     *
     * @param admin 管理员信息
     * @return 影响的行数
     */
    int updateAdminInfo(@Param("admin") Admin admin);

    /**
     * 获取所有管理员信息
     *
     * @return 管理员信息列表
     */
    List<Admin> getAllAdmin();
}
