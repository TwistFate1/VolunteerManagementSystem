package com.hwadee.springboot.controller;

import com.hwadee.springboot.entity.Result;
import com.hwadee.springboot.entity.VolunteerActivity;
import com.hwadee.springboot.service.VolunteerActivityService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("volunteerActivity")
public class VolunteerActivityController {
    @Resource
    private VolunteerActivityService volunteerActivityService;

//    @GetMapping("/getAllActivities")
//    public Result getAllActivities(@RequestParam(defaultValue = "1") int page,
//                                   @RequestParam(defaultValue = "4") int limit,
//                                   @RequestParam(defaultValue = "") String name) {
//        Result res = new Result();
//        try {
//            List<VolunteerActivity> activities = volunteerActivityService.getAllActivities(page, limit, name);
//            int total = volunteerActivityService.count(name);
//
//            res.setCode(1);
//            res.setResult(new Object() {
//                public List<VolunteerActivity> getActivities() {
//                    return activities;
//                }
//
//                public int getTotal() {
//                    return total;
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//            res.setCode(0);
//            res.setResult("Error retrieving volunteer activities");
//        }
//        return res;
//    }
//}



//    如果加入成功，code 为 1；如果已审核通过，code 为 2；如果重新提交申请，code 为 3；如果失败，code 为 0 并返回错误信息。

    @PostMapping("/getAllActivities")
    public Result getAllActivities(@RequestBody Map<String, Object> request) {
        Result res = new Result();
        try {
            int page = (int) request.getOrDefault("page", 1);
            int limit = (int) request.getOrDefault("limit", 4);
            String name = (String) request.getOrDefault("name", "");
            String location = (String) request.getOrDefault("location", "");
            String sort = (String) request.getOrDefault("sort", "");

            List<VolunteerActivity> activities = volunteerActivityService.getAllActivities(page, limit, name,location,sort);
            int total = volunteerActivityService.count(name,location,sort);

            res.setCode(1);
            res.setResult(new Object() {
                public List<VolunteerActivity> getActivities() {
                    return activities;
                }

                public int getTotal() {
                    return total;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(0);
            res.setResult("Error retrieving volunteer activities");
        }
        return res;
    }



    @PostMapping("/getActivityOfTeam")
    public Result getActivityOfTeam(@RequestBody Map<String, String> request) {
        Result res = new Result();
        try {
            String teamId = request.get("teamId");
            List<VolunteerActivity> activities = volunteerActivityService.getActivityOfTeam(teamId);
            res.setCode(1);
            res.setResult(activities);
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error fetching activities: " + e.getMessage());
        }
        return res;
    }





    @PostMapping("/insertActivity")
    public Result insertActivity(@RequestBody VolunteerActivity volunteerActivity) {
        Result res = new Result();
        try {
            boolean success = volunteerActivityService.insertActivity(volunteerActivity);
            if (success) {
                res.setCode(1);
                res.setResult(volunteerActivity);
            } else {
                res.setCode(0);
                res.setResult("Failed to insert activity");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error inserting activity: " + e.getMessage());
        }
        return res;
    }

    @PostMapping("/updateActivity")
    public Result updateActivity(@RequestBody VolunteerActivity volunteerActivity) {
        Result res = new Result();
        try {
            boolean success = volunteerActivityService.updateActivity(volunteerActivity);
            if (success) {
                res.setCode(1);
                res.setResult(volunteerActivity);
            } else {
                res.setCode(0);
                res.setResult("Failed to update activity");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error updating activity: " + e.getMessage());
        }
        return res;
    }


    @PostMapping("/deleteActivity")
    public Result deleteActivity(@RequestBody Map<String, Long> data) {
        Result res = new Result();
        try {
            Long activityId = data.get("activityId");
            boolean success = volunteerActivityService.deleteActivity(activityId);
            if (success) {
                res.setCode(1);
                res.setResult("Activity deleted successfully");
            } else {
                res.setCode(0);
                res.setResult("Failed to delete activity");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error deleting activity: " + e.getMessage());
        }
        return res;
    }


//    @PostMapping("/getActivityByActivityId")
//    public Result getActivityByActivityId(@RequestBody Map<String, Integer> data) {
//        Result res = new Result();
//        try {
//            int activityId = data.get("activityId");
//            VolunteerActivity volunteerActivity = volunteerActivityService.getActivityByActivityId(activityId);
//            res.setCode(1);
//            res.setResult(volunteerActivity);
//        } catch (Exception e) {
//            e.printStackTrace();
//            res.setCode(500);
//            res.setResult("Error fetching activity by ID: " + e.getMessage());
//        }
//        return res;
//    }

    @PostMapping("/getActivityByActivityId")
    public Result getActivityByActivityId(@RequestBody Map<String, Integer> data) {
        // 打印传入的 JSON 数据
        System.out.println("Received data: " + data);

        Result res = new Result();
        try {
            Integer activityId = data.get("activityId");

            // 检查activityId是否为空
            if (activityId != null) {
                VolunteerActivity volunteerActivity = volunteerActivityService.getActivityByActivityId(activityId);
                res.setCode(1);
                res.setResult(volunteerActivity);
            } else {
                res.setCode(400);
                res.setResult("Error: 'activityId' is missing or null.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error fetching activity by ID: " + e.getMessage());
        }
        return res;
    }


    @PostMapping("/getActivityNumByTeamId")
    public Result getActivityNumByTeamId(@RequestBody Map<String, String> data) {
        Result res = new Result();
        try {
            String teamId = data.get("teamId");
            int activityNum = volunteerActivityService.getActivityNumByTeamId(teamId);
            res.setCode(1);
            res.setResult(activityNum);
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error fetching activity number by team ID: " + e.getMessage());
        }
        return res;
    }



    @PostMapping("/getActivityNumber")
    public Result getActivityNumber() {
        Result res = new Result();
        try {
            int activityCount = volunteerActivityService.getActivityNumber();
            res.setCode(1);
            res.setResult(activityCount);
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error fetching activity number: " + e.getMessage());
        }
        return res;
    }


    /**
     * 获取所有志愿活动的内容
     *
     * @return 所有志愿活动的内容
     */
    @PostMapping("/getAllActivity")
    public Result getAllActivity() {
        Result res = new Result();
        try {
            List<VolunteerActivity> activities = volunteerActivityService.getAllActivity();
            res.setCode(1);
            res.setResult(activities);
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error fetching all activities: " + e.getMessage());
        }
        return res;
    }



    /**
     * 更新志愿活动状态
     *
     * @param data 包含activityId和state的映射
     * @return 更新结果
     */
    @PostMapping("/updateActivityState")
    public Result updateActivityState(@RequestBody Map<String, Integer> data) {
        Result res = new Result();
        try {
            int activityId = data.get("activityId");
            int state = data.get("state");
            int result = volunteerActivityService.updateActivityState(activityId, state);
            if (result > 0) {
                res.setCode(1);
                res.setResult("Update successful");
            } else {
                res.setCode(0);
                res.setResult("Update failed");
            }
        } catch (Exception e) {
            res.setCode(500);
            res.setResult("Error updating activity state: " + e.getMessage());
        }
        return res;
    }






    /**
     * 获取志愿者某一年每天的志愿时长
     *
     * @param data 包含年份和志愿者ID
     * @return 志愿者每天的志愿时长
     */
    @PostMapping("/getHoursPerDayForYear")
    public Result getHoursPerDayForYear(@RequestBody Map<String, Object> data) {
        Result res = new Result();
        try {
            int year = (int) data.get("year");
            String volunteerId = (String) data.get("volunteerId");
            List<Map<String, Object>> result = volunteerActivityService.getVolunteerHoursPerDayForYear(year, volunteerId);
            res.setCode(1);
            res.setResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error fetching volunteer hours per day for year: " + e.getMessage());
        }
        return res;
    }
}