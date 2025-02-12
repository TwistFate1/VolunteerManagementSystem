package com.hwadee.springboot.service.impl;

import com.hwadee.springboot.dao.VolunteerDao;
import com.hwadee.springboot.entity.Volunteer;
import com.hwadee.springboot.service.VolunteerService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service("volunteerService")
public class VolunteerServiceImpl implements VolunteerService {
    @Resource
    private VolunteerDao volunteerDao;

    @Override
    public List<Volunteer> getAllVolunteers(int page, int limit, String name) {
        int offset = (page - 1) * limit;
        return volunteerDao.queryAllByLimit(offset, limit, name);
    }

    @Override
    public int count(String name) {
        return volunteerDao.count(name);
    }







    //登录
    @Override
    public Volunteer queryById(String volunteerId) {
        return volunteerDao.queryById(volunteerId);
    }

    @Override
    public Volunteer login(String volunteerId, String password) {
        return volunteerDao.login(volunteerId, password);
    }

    @Override
    public Volunteer insertVolunteer(Volunteer volunteer) {
        if (volunteerDao.findByVolunteerId(volunteer.getVolunteerId()) == null) {
            if (volunteerDao.insert(volunteer) > 0) {
                return volunteer;
            }
        }
        return null;  // 志愿者ID已存在，注册失败
    }

    @Override
    public Volunteer findByVolunteerId(String volunteerId) {
        return volunteerDao.findByVolunteerId(volunteerId);
    }

    @Override
    public Volunteer getVolunteerWithTeams(String volunteerId) {
        return volunteerDao.getVolunteerWithTeams(volunteerId);
    }



    @Override
    public boolean updateVolunteerInfo(Volunteer volunteer) {
        return volunteerDao.updateVolunteerInfo(volunteer) > 0;
    }

    @Override
    public List<Volunteer> getVolunteerDetailByActivityId(int activityId) {
        return volunteerDao.getVolunteerDetailByActivityId(activityId);
    }

    @Override
    public List<Volunteer> getVolunteerDetailByTeamId(String teamId) {
        return volunteerDao.getVolunteerDetailByTeamId(teamId);
    }























    //上传图片

//    @Override
//    public int uploadVolunteerAvatar(String volunteerId, MultipartFile file) {
//        if (file.isEmpty()) {
//            return 0;
//        }
//
//        // 文件保存路径
//        String filePath = "E:\\shixi\\avatar\\volunteer\\" + volunteerId;
//        File dest = new File(filePath);
//
//        // 检查父目录是否存在，不存在则创建
//        if (!dest.getParentFile().exists()) {
//            if (!dest.getParentFile().mkdirs()) {
//                System.err.println("Failed to create directories!");
//                // 处理创建目录失败的情况，可以抛出异常或其他处理方式
//            }
//        }
//
//
//        try {
//            // 保存文件
//            file.transferTo(dest);
//            // 更新数据库中的头像路径
//            Volunteer volunteer = new Volunteer();
//            volunteer.setVolunteerId(volunteerId);
//            volunteer.setAvatar(filePath);
//            return volunteerDao.updateVolunteerInfo(volunteer);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return 0;
//        }
//    }



    @Override
    public int uploadVolunteerAvatar(String volunteerId, MultipartFile file) {
        if (file.isEmpty()) {
            return 0;
        }

        // 文件保存路径
        String filePath = "E:\\shixi\\avatar\\volunteer\\" + volunteerId + ".png";
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
            Volunteer volunteer = new Volunteer();
            volunteer.setVolunteerId(volunteerId);
            volunteer.setAvatar(filePath);
            return volunteerDao.updateVolunteerInfo(volunteer);
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }


    @Override
    public File getVolunteerAvatar(String volunteerId) {
        Volunteer volunteer = volunteerDao.findByVolunteerId(volunteerId);
        if (volunteer != null && volunteer.getAvatar() != null) {
            return new File(volunteer.getAvatar());
        }
        return null;
    }






    /**
     * 获取所有志愿者的数量
     *
     * @return 志愿者数量
     */
    @Override
    public int getVolunteerNumber() {
        return volunteerDao.getVolunteerNumber();
    }





}
