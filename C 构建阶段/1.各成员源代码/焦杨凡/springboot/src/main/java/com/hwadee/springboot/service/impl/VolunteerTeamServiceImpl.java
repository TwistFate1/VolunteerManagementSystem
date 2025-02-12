//package com.hwadee.springboot.service.impl;
//
//import com.hwadee.springboot.dao.VolunteerTeamDao;
//import com.hwadee.springboot.entity.VolunteerTeam;
//import com.hwadee.springboot.service.VolunteerTeamService;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.List;
//
//@Service("volunteerTeamService")
//public class VolunteerTeamServiceImpl implements VolunteerTeamService {
//    @Resource
//    private VolunteerTeamDao volunteerTeamDao;
//
//    @Override
//    public List<VolunteerTeam> getAllTeams(int page, int limit, String name) {
//        int offset = (page - 1) * limit;
//        return volunteerTeamDao.queryAllByLimit(offset, limit, name);
//    }
//
//    @Override
//    public int count(String name) {
//        return volunteerTeamDao.count(name);
//    }
//}

package com.hwadee.springboot.service.impl;

import com.hwadee.springboot.dao.VolunteerTeamDao;
import com.hwadee.springboot.entity.Volunteer;
import com.hwadee.springboot.entity.VolunteerTeam;
import com.hwadee.springboot.service.VolunteerTeamService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service("volunteerTeamService")
public class VolunteerTeamServiceImpl implements VolunteerTeamService {
    @Resource
    private VolunteerTeamDao volunteerTeamDao;

    @Override
    public List<VolunteerTeam> getAllTeams(int page, int limit, String name) {
        int offset = (page - 1) * limit;
        System.out.println("黄话反对和地方法规咯limit:" + limit+"offset："+offset);
        return volunteerTeamDao.queryAllByLimit(offset, limit, name);

    }

    @Override
    public int count(String name) {
        return volunteerTeamDao.count(name);
    }

    @Override
    public VolunteerTeam getTeamById(String teamId) {
        return volunteerTeamDao.queryById(teamId);
    }





    @Override
    public VolunteerTeam login(String teamId, String password) {
        return volunteerTeamDao.login(teamId, password);
    }


    @Override
    public VolunteerTeam insertVolunteerTeam(VolunteerTeam volunteerTeam) {
        if (volunteerTeamDao.findByTeamId(volunteerTeam.getTeamId()) == null) {
            if (volunteerTeamDao.insert(volunteerTeam) > 0) {
                return volunteerTeam;
            }
        }
        return null;  // 团队ID已存在，注册失败
    }

    @Override
    public VolunteerTeam findByTeamId(String teamId) {
        return volunteerTeamDao.findByTeamId(teamId);
    }



    @Override
    public boolean updateTeamInfo(VolunteerTeam volunteerTeam) {
        return volunteerTeamDao.updateTeamInfo(volunteerTeam) > 0;
    }
















    @Override
    public int uploadVolunteerTeamAvatar(String teamId, MultipartFile file) {
        if (file.isEmpty()) {
            return 0;
        }

        // 文件保存路径
        String filePath = "E:\\shixi\\avatar\\team\\" + teamId + ".png";
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
            VolunteerTeam volunteerTeam = new VolunteerTeam();
            volunteerTeam.setTeamId(teamId);
            volunteerTeam.setAvatar(filePath);
            return volunteerTeamDao.updateTeamInfo(volunteerTeam);
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }


    @Override
    public File getVolunteerTeamAvatar(String volunteerId) {
        VolunteerTeam volunteerTeam = volunteerTeamDao.findByTeamId(volunteerId);
        if (volunteerTeam != null && volunteerTeam.getAvatar() != null) {
            return new File(volunteerTeam.getAvatar());
        }
        return null;
    }


    /**
     * 获取所有志愿团队的数量
     *
     * @return 志愿团队数量
     */
    @Override
    public int getTeamNumber() {
        return volunteerTeamDao.getTeamNumber();
    }
}
