package com.hwadee.springboot.service;

import com.hwadee.springboot.entity.Admin;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface AdminService {
    /**
     * 管理员登录
     *
     * @param adminId 管理员ID
     * @param password 管理员密码
     * @return 管理员信息
     */
    Admin login(String adminId, String password);

    /**
     * 注册管理员
     *
     * @param admin 管理员信息
     * @return 注册结果
     */
    Admin insertAdmin(Admin admin);

    /**
     * 根据管理员ID查询管理员信息
     *
     * @param adminId 管理员ID
     * @return 管理员信息
     */
    Admin findByAdminId(String adminId);



    /**
     * 根据管理员ID更新管理员信息
     *
     * @param admin 管理员信息
     * @return 影响的行数
     */
    int updateAdminInfo(Admin admin);




    /**
     * 上传管理员头像
     *
     * @param adminId 志愿者ID
     * @param file 头像文件
     * @return 影响的行数
     */
    int uploadAdminAvatar(String adminId, MultipartFile file);


    /**
     * 根据管理员ID获取头像文件
     *
     * @param adminId 志愿者ID
     * @return 头像文件
     */
    File getAdminAvatar(String adminId);

    /**
     * 获取所有管理员信息
     *
     * @return 管理员信息列表
     */
    List<Admin> getAllAdmin();
}
