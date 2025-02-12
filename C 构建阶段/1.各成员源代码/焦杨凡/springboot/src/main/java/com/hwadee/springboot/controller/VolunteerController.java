package com.hwadee.springboot.controller;

import com.hwadee.springboot.entity.Result;
import com.hwadee.springboot.entity.Volunteer;
import com.hwadee.springboot.service.VolunteerService;
import org.springframework.core.io.UrlResource;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("volunteer")
public class VolunteerController {
    @Resource
    private VolunteerService volunteerService;

//    @GetMapping("/getAllVolunteers")
//    public Result getAllVolunteers(@RequestParam(defaultValue = "1") int page,
//                                   @RequestParam(defaultValue = "4") int limit,
//                                   @RequestParam(defaultValue = "") String name) {
//        Result res = new Result();
//        try {
//            List<Volunteer> volunteerList = volunteerService.getAllVolunteers(page, limit, name);
//            int total = volunteerService.count(name);
//
//            res.setCode(1);
//            res.setResult(new Object() {
//                public List<Volunteer> getVolunteerList() {
//                    return volunteerList;
//                }
//
//                public int getTotal() {
//                    return total;
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//            res.setCode(0);
//            res.setResult("Error retrieving volunteers");
//        }
//        return res;
//    }
//}

    @PostMapping("/getAllVolunteers")
    public Result getAllVolunteers(@RequestBody Map<String, Object> request) {
        Result res = new Result();
        try {
            int page = (int) request.getOrDefault("page", 1);
            int limit = (int) request.getOrDefault("limit", 4);
            String name = (String) request.getOrDefault("name", "");

            List<Volunteer> volunteerList = volunteerService.getAllVolunteers(page, limit, name);
            int total = volunteerService.count(name);

            res.setCode(1);
            res.setResult(new Object() {
                public List<Volunteer> getVolunteerList() {
                    return volunteerList;
                }

                public int getTotal() {
                    return total;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(0);
            res.setResult("Error retrieving volunteers");
        }
        return res;
    }






    //登录
    @PostMapping("/volunteerLogin")
    public Result volunteerLogin(@RequestBody Map<String, String> request) {
        Result res = new Result();
        try {
            String volunteerId = request.get("volunteer_id");
            String password = request.get("password");
            Volunteer volunteer = volunteerService.login(volunteerId, password);
            if (volunteer != null) {
                res.setCode(1);
                res.setResult(volunteer);
            } else {
                res.setCode(0);
                res.setResult("Invalid volunteer ID or password");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error logging in");
        }
        return res;
    }

    @PostMapping("/insertVolunteer")
    public Result insertVolunteer(@RequestBody Volunteer volunteerForm) {
        Result res = new Result();
        try {
            Volunteer registeredVolunteer = volunteerService.insertVolunteer(volunteerForm);
            if (registeredVolunteer != null) {
                res.setCode(1);
                res.setResult(registeredVolunteer);
            } else {
                res.setCode(0);
                res.setResult("Volunteer ID already exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error registering volunteer: " + e.getMessage());
        }
        return res;
    }

    @PostMapping("/getVolunteerById")
    public Result getVolunteerById(@RequestBody Map<String, String> request) {
        Result res = new Result();
        try {
            String volunteerId = request.get("volunteerId");
            Volunteer volunteer = volunteerService.getVolunteerWithTeams(volunteerId);
            if (volunteer != null) {
                res.setCode(1);
                res.setResult(volunteer);
            } else {
                res.setCode(0);
                res.setResult("Volunteer not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error retrieving volunteer: " + e.getMessage());
        }
        return res;
    }


    @PostMapping("/updateVolunteerInfo")
    public Result updateVolunteerInfo(@RequestBody Volunteer volunteer) {
        Result res = new Result();
        try {
            boolean success = volunteerService.updateVolunteerInfo(volunteer);
            if (success) {
                res.setCode(1);
                res.setResult("Volunteer information updated successfully");
            } else {
                res.setCode(0);
                res.setResult("Failed to update volunteer information");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error updating volunteer information: " + e.getMessage());
        }
        return res;
    }

//    @PostMapping("/getVolunteerDetailByActivityId")
//    public Result getVolunteerDetailByActivityId(@RequestBody Map<String, Integer> data) {
//        Result res = new Result();
//        try {
//            int activityId = data.get("activityId");
//            List<Volunteer> result = volunteerService.getVolunteerDetailByActivityId(activityId);
//            res.setCode(1);
//            res.setResult(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//            res.setCode(500);
//            res.setResult("Error fetching volunteer details by activity ID: " + e.getMessage());
//        }
//        return res;
//    }
@PostMapping("/getVolunteerDetailByActivityId")
public Result getVolunteerDetailByActivityId(@RequestBody Map<String, Integer> data) {
    Result res = new Result();
    try {
        int activityId = data.get("activityId");
        List<Volunteer> result = volunteerService.getVolunteerDetailByActivityId(activityId);

        // Adding the "add" field to each volunteer
        for (Volunteer volunteer : result) {
            volunteer.setAdd(true);
        }

        res.setCode(1);
        res.setResult(result);
    } catch (Exception e) {
        e.printStackTrace();
        res.setCode(500);
        res.setResult("Error fetching volunteer details by activity ID: " + e.getMessage());
    }
    return res;
}






    @PostMapping("/getVolunteerDetailByTeamId")
    public Result getVolunteerDetailByTeamId(@RequestBody Map<String, String> data) {
        Result res = new Result();
        try {
            String teamId = data.get("teamId");
            List<Volunteer> result = volunteerService.getVolunteerDetailByTeamId(teamId);
            res.setCode(1);
            res.setResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error fetching volunteer details by team ID: " + e.getMessage());
        }
        return res;
    }
























//上传头像

    @PostMapping("/uploadVolunteerAvatar")
    public Result uploadVolunteerAvatar(@RequestParam("avatar") MultipartFile file, @RequestParam("volunteerId") String volunteerId) {
        Result res = new Result();
        try {
            int updateCount = volunteerService.uploadVolunteerAvatar(volunteerId, file);
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




    @PostMapping("/getVolunteerAvatar")
    public ResponseEntity<byte[]> getVolunteerAvatar(@RequestBody Map<String, String> data) {
        System.out.println("黄sir111111111111111111111111111111111111111111111111111111111111111111111111111111111111111 :" + data);
        try {
            String volunteerId = data.get("volunteerId");
            File avatarFile = volunteerService.getVolunteerAvatar(volunteerId);
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








    @PostMapping("/getVolunteerNumber")
    public Result getVolunteerNumber() {
        Result res = new Result();
        try {
            int volunteerCount = volunteerService.getVolunteerNumber();
            res.setCode(1);
            res.setResult(volunteerCount);
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error fetching volunteer number: " + e.getMessage());
        }
        return res;
    }
}