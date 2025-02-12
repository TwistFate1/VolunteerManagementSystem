package com.hwadee.springboot.controller;

import com.hwadee.springboot.entity.Result;
import com.hwadee.springboot.entity.VolunteerTeam;
import com.hwadee.springboot.service.VolunteerTeamService;
import org.springframework.dao.DataAccessException;
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

@RestController
@RequestMapping("volunteerTeam")
public class VolunteerTeamController {
    @Resource
    private VolunteerTeamService volunteerTeamService;

    @PostMapping("/getAllTeams")
    public Result getAllTeams(@RequestBody Map<String, Object> request) {
        Result res = new Result();
        try {
            int page = (int) request.getOrDefault("page", 1);
            int limit = (int) request.getOrDefault("limit", 4);
            String name = (String) request.getOrDefault("name", "");
            System.out.println("黄话反对和地方法规咯111111111111111111111111111111111111111111111111111111:" + limit);

            List<VolunteerTeam> teamList = volunteerTeamService.getAllTeams(page, limit, name);
            System.out.println("黄话反对和地方法规咯1111111111111111111111111111111111111111111111111111111111111:" + teamList);
            int total = volunteerTeamService.count(name);

            res.setCode(1);
            res.setResult(new Object() {
                public List<VolunteerTeam> getTeamList() {
                    return teamList;
                }

                public int getTotal() {
                    return total;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(0);
            res.setResult("Error retrieving volunteer teams");
        }
        return res;
    }

    @PostMapping("/getTeamById")
    public Result getTeamById(@RequestBody Map<String, String> request) {
        Result res = new Result();
        try {
            String teamId = request.get("teamId");
            VolunteerTeam volunteerTeam = volunteerTeamService.getTeamById(teamId);
            res.setCode(1);
            res.setResult(volunteerTeam);
            return res;
        } catch (DataAccessException e) {
            e.printStackTrace();
            res.setCode(0);
            res.setResult("false"+e.getMessage());
            return res;
        }


    }






    @PostMapping("/volunteerTeamLogin")
    public Result volunteerTeamLogin(@RequestBody Map<String, String> request) {
        Result res = new Result();
        try {
            String teamId = request.get("teamId");
            String password = request.get("password");
            VolunteerTeam volunteerTeam = volunteerTeamService.login(teamId, password);
            if (volunteerTeam != null) {
                res.setCode(1);
                res.setResult(volunteerTeam);
            } else {
                res.setCode(0);
                res.setResult("Invalid team ID or password");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error logging in");
        }
        return res;
    }

    @PostMapping("/insertVolunteerTeam")
    public Result insertVolunteerTeam(@RequestBody VolunteerTeam teamForm) {
        Result res = new Result();
        try {
            VolunteerTeam registeredTeam = volunteerTeamService.insertVolunteerTeam(teamForm);
            if (registeredTeam != null) {
                res.setCode(1);
                res.setResult(registeredTeam);
            } else {
                res.setCode(0);
                res.setResult("Team ID already exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error registering team: " + e.getMessage());
        }
        return res;
    }

    @PostMapping("/updateTeamInfo")
    public Result updateTeamInfo(@RequestBody VolunteerTeam volunteerTeam) {
        Result res = new Result();
        try {
            boolean success = volunteerTeamService.updateTeamInfo(volunteerTeam);
            if (success) {
                res.setCode(1);
                res.setResult("Successfully updated team information");
            } else {
                res.setCode(0);
                res.setResult("Failed to update team information");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error updating team information: " + e.getMessage());
        }
        return res;
    }









    //上传头像

    @PostMapping("/uploadVolunteerTeamAvatar")
    public Result uploadVolunteerTeamAvatar(@RequestParam("avatar") MultipartFile file, @RequestParam("teamId") String teamId) {
        Result res = new Result();
        try {
            int updateCount = volunteerTeamService.uploadVolunteerTeamAvatar(teamId, file);
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




    @PostMapping("/getVolunteerTeamAvatar")
    public ResponseEntity<byte[]> getVolunteerTeamAvatar(@RequestBody Map<String, String> data) {
        try {
            String teamId = data.get("teamId");
            File avatarFile = volunteerTeamService.getVolunteerTeamAvatar(teamId);
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
     * 查询所有志愿团队的数量
     *
     * @return 志愿团队数量
     */
    @PostMapping("/getTeamNumber")
    public Result getTeamNumber() {
        Result res = new Result();
        try {
            int teamCount = volunteerTeamService.getTeamNumber();
            res.setCode(1);
            res.setResult(teamCount);
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error fetching team number: " + e.getMessage());
        }
        return res;
    }



}
