package com.hwadee.springboot.controller;

import com.hwadee.springboot.entity.Admin;
import com.hwadee.springboot.entity.Result;
import com.hwadee.springboot.service.AdminService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("admin")
public class AdminController {
    @Resource
    private AdminService adminService;

    @PostMapping("/adminLogin")
    public Result adminLogin(@RequestBody Map<String, String> request) {
        Result res = new Result();
        try {
            String adminId = request.get("adminId");
            String password = request.get("password");
            Admin admin = adminService.login(adminId, password);
            if (admin != null) {
                res.setCode(1);
                res.setResult(admin);
            } else {
                res.setCode(0);
                res.setResult("Invalid admin ID or password");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error logging in");
        }
        return res;
    }


    @PostMapping("/insertAdmin")
    public Result insertAdmin(@RequestBody Admin adminForm) {
        Result res = new Result();
        try {
            Admin registeredAdmin = adminService.insertAdmin(adminForm);
            if (registeredAdmin != null) {
                res.setCode(1);
                res.setResult(registeredAdmin);
            } else {
                res.setCode(0);
                res.setResult("Admin ID already exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error registering admin: " + e.getMessage());
        }
        return res;
    }


    @PostMapping("/getAdminById")
    public Result findByAdminId(@RequestBody Map<String, String> data) {
        Result res = new Result();
        try {
            String adminId = data.get("adminId");
            Admin admin = adminService.findByAdminId(adminId);
            res.setCode(1);
            res.setResult(admin);
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error fetching admin information by ID: " + e.getMessage());
        }
        return res;
    }


    @PostMapping("/updateAdminInfo")
    public Result updateAdminInfo(@RequestBody Admin admin) {
        Result res = new Result();
        try {
            int updateCount = adminService.updateAdminInfo(admin);
            if (updateCount > 0) {
                res.setCode(1);
                res.setResult("Admin information updated successfully");
            } else {
                res.setCode(0);
                res.setResult("Failed to update admin information");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error updating admin information: " + e.getMessage());
        }
        return res;
    }












    //上传头像

    @PostMapping("/uploadAdminAvatar")
    public Result uploadAdminAvatar(@RequestParam("avatar") MultipartFile file, @RequestParam("adminId") String adminId) {
        Result res = new Result();
        try {
            int updateCount = adminService.uploadAdminAvatar(adminId, file);
            if (updateCount > 0) {
                res.setCode(1);
                res.setResult("Avatar uploaded successfully");
            } else {
                res.setCode(0);
                res.setResult("Failed to upload avatar");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error uploading avatar: " + e.getMessage());
        }
        return res;
    }




    @PostMapping("/getAdminAvatar")
    public ResponseEntity<byte[]> getAdminIdAvatar(@RequestBody Map<String, String> data) {
        try {
            String adminId = data.get("adminId");
            File avatarFile = adminService.getAdminAvatar(adminId);
            if (avatarFile != null && avatarFile.exists()) {
                Path path = Paths.get(avatarFile.getAbsolutePath());
                byte[] fileContent = Files.readAllBytes(path);

                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + avatarFile.getName() + "\"");

                return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 获取所有管理员信息
     *
     * @return 管理员信息列表
     */
    @PostMapping("/getAllAdmin")
    public Result getAllAdmin() {
        Result res = new Result();
        try {
            List<Admin> result = adminService.getAllAdmin();
            res.setCode(1);
            res.setResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error fetching all admins: " + e.getMessage());
        }
        return res;
    }
}
