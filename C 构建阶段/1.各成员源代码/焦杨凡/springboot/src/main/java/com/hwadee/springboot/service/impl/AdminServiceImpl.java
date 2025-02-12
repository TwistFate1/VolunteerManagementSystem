package com.hwadee.springboot.service.impl;

import com.hwadee.springboot.dao.AdminDao;
import com.hwadee.springboot.entity.Admin;
import com.hwadee.springboot.entity.VolunteerTeam;
import com.hwadee.springboot.service.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service("adminService")
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminDao adminDao;

    @Override
    public Admin login(String adminId, String password) {
        return adminDao.login(adminId, password);
    }

    @Override
    public Admin insertAdmin(Admin admin) {
        if (adminDao.findByAdminId(admin.getAdminId()) == null) {
//            admin.setStatus(1);  // 设置初始状态为1
            if (adminDao.insert(admin) > 0) {
                return admin;
            }
        }
        return null;  // 管理员ID已存在，注册失败
    }

    @Override
    public Admin findByAdminId(String adminId) {
        return adminDao.findByAdminId(adminId);
    }



    @Override
    public int updateAdminInfo(Admin admin) {
        return adminDao.updateAdminInfo(admin);
    }









    @Override
    public int uploadAdminAvatar(String adminId, MultipartFile file) {
        if (file.isEmpty()) {
            return 0;
        }

        // 文件保存路径
        String filePath = "E:\\shixi\\avatar\\admin\\" + adminId + ".png";
        File dest = new File(filePath);

        // 检查父目录是否存在，不存在则创建
        // 检查父目录是否存在，不存在则创建
        if (!dest.getParentFile().exists()) {
            if (!dest.getParentFile().mkdirs()) {
                System.err.println("Failed to create directories!");
                // 处理创建目录失败的情况，可以抛出异常或其他处理方式
            }
        }

        try {
            // 将上传的文件转换为 BufferedImage
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if (bufferedImage == null) {
                throw new IOException("Failed to read uploaded file as image");
            }

            // 将 BufferedImage 写入到指定路径
            ImageIO.write(bufferedImage, "png", dest);

            // 更新数据库中的头像路径
            Admin admin = new Admin();
            admin.setAdminId(adminId);
            admin.setAvatar(filePath);
            return adminDao.updateAdminInfo(admin);
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }


    @Override
    public File getAdminAvatar(String adminId) {
        Admin admin = adminDao.findByAdminId(adminId);
        if (admin != null && admin.getAvatar() != null) {
            return new File(admin.getAvatar());
        }
        return null;
    }


    @Override
    public List<Admin> getAllAdmin() {
        return adminDao.getAllAdmin();
    }
}
