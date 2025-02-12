package com.hwadee.springboot.controller;

import com.hwadee.springboot.entity.Result;
import com.hwadee.springboot.service.VolunteerService;
import com.hwadee.springboot.entity.VolunteerServiceRecord;
import com.hwadee.springboot.service.VolunteerServiceRecordService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("volunteerServiceRecord")
public class VolunteerServiceRecordController {
    @Resource
    private VolunteerServiceRecordService volunteerServiceRecordService;

    @PostMapping("/joinActivity")
    public Result joinActivity(@RequestBody Map<String, String> request) {
        Result res = new Result();
        try {
            String volunteerId = request.get("volunteerId");
            int activityId = Integer.parseInt(request.get("activityId"));

            String resultMessage = volunteerServiceRecordService.handleJoinActivity(volunteerId, activityId);
            if (resultMessage.contains("Successfully")) {
                res.setCode(1);
            } else if (resultMessage.contains("Already approved")) {
                res.setCode(2);
            } else if (resultMessage.contains("Application resubmitted")) {
                res.setCode(3);
            } else {
                res.setCode(0);
            }
            res.setResult(resultMessage);
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error joining the activity: " + e.getMessage());
        }
        return res;
    }


    @PostMapping("/getAllVolunteerActivitiesByVolunteerId")
    public Result getAllVolunteerActivitiesByVolunteerId(@RequestBody Map<String, String> request) {
        Result res = new Result();
        try {
            String volunteerId = request.get("volunteerId");
            List<VolunteerServiceRecord> records = volunteerServiceRecordService.getAllRecordsByVolunteerId(volunteerId);
            Integer totalServiceHours = volunteerServiceRecordService.getTotalServiceHours(volunteerId);
            Integer num1 = volunteerServiceRecordService.getActivityCountByState(volunteerId, 1);
            Integer num0 = volunteerServiceRecordService.getActivityCountByState(volunteerId, 0);
            Integer num2 = volunteerServiceRecordService.getActivityCountByState(volunteerId, 2);

            res.setCode(1);
            res.setResult(new Object() {
                public List<VolunteerServiceRecord> getRecords() {
                    return records;
                }

                public Integer getTotalServiceHours() {
                    return totalServiceHours;
                }

                public Integer getNum1() {
                    return num1;
                }

                public Integer getNum0() {
                    return num0;
                }

                public Integer getNum2() {
                    return num2;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error retrieving records: " + e.getMessage());
        }
        return res;
    }




    @PostMapping("/checkVolunteerActivityStatus")
    public Result checkVolunteerActivityStatus(@RequestBody Map<String, Object> request) {
        Result res = new Result();
        try {
            String volunteerId = (String) request.get("volunteerId");
            Integer activityId = (Integer) request.get("activityId");
            int count = volunteerServiceRecordService.checkVolunteerActivityStatus(volunteerId, activityId);
            if (count > 0) {
                res.setCode(1);
                res.setResult("已加入");
            } else {
                res.setCode(0);
                res.setResult("未加入");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error checking volunteer activity status: " + e.getMessage());
        }
        return res;
    }

    @PostMapping("/getVolunteerIdByActivityId")
    public Result getVolunteerIdByActivityId(@RequestBody Map<String, Long> data) {
        Result res = new Result();
        try {
            Long activityId = data.get("activityId");
            Map<String, Object> result = volunteerServiceRecordService.getVolunteerIdByActivityId(activityId);
            res.setCode(1);
            res.setResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error fetching volunteer IDs by activity ID: " + e.getMessage());
        }
        return res;
    }

//    @PostMapping("/getVolunteerIdByActivityId")
//    public Result getVolunteerIdByActivityId(@RequestBody Map<String, Integer> data) {
//        Result res = new Result();
//        try {
//            int activityId = data.get("activityId");
//            Map<String, Object> result = volunteerServiceRecordService.getVolunteerIdByActivityId(activityId);
//            res.setCode(1);
//            res.setResult(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//            res.setCode(500);
//            res.setResult("Error fetching volunteer IDs by activity ID: " + e.getMessage());
//        }
//        return res;
//    }

    @PostMapping("/updateVolunteerServiceRecord")
    public Result updateVolunteerServiceRecord(@RequestBody VolunteerServiceRecord volunteerServiceRecord) {
        Result res = new Result();
        try {
            boolean success = volunteerServiceRecordService.updateVolunteerServiceRecord(volunteerServiceRecord);
            if (success) {
                res.setCode(1);
                res.setResult("Volunteer service record updated successfully");
            } else {
                res.setCode(0);
                res.setResult("Failed to update volunteer service record");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error updating volunteer service record: " + e.getMessage());
        }
        return res;
    }






    @PostMapping("/getVolunteerServiceHoursPerActivity")
    public Result getVolunteerServiceHoursPerActivity(@RequestBody Map<String, Object> data) {

        // 打印传入的JSON数据
//        System.out.println("黄sir111111111111111111111111111111111111111111111111111111111111111111111111111111111111111 :" + data);
        Result res = new Result();
        try {
            String volunteerId = (String) data.get("volunteerId");
            int activityId = (Integer) data.get("activityId");
            Integer serviceHours = volunteerServiceRecordService.getVolunteerServiceHoursPerActivity(volunteerId, activityId);
            res.setCode(1);
            res.setResult(serviceHours);
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error fetching volunteer service hours: " + e.getMessage());
        }
        return res;
    }

    @PostMapping("/deleteApplyForActivity")
    public Result deleteApplyForActivity(@RequestBody Map<String, Object> data) {
        Result res = new Result();
        try {
            String volunteerId = (String) data.get("volunteerId");
            int activityId = (Integer) data.get("activityId");
            boolean success = volunteerServiceRecordService.deleteApplyForActivity(volunteerId, activityId);
            if (success) {
                res.setCode(1);
                res.setResult("Volunteer application for activity deleted successfully");
            } else {
                res.setCode(0);
                res.setResult("Failed to delete volunteer application for activity");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error deleting volunteer application for activity: " + e.getMessage());
        }
        return res;
    }

    @PostMapping("/getServiceRecord")
    public Result getServiceRecord(@RequestBody Map<String, Object> data) {
        // 打印传入的JSON数据
        System.out.println("黄sir111111111111111111111111111111111111111111111111111111111111111111111111111111111111111 :" + data);
        Result res = new Result();
        try {
            String volunteerId = (String) data.get("volunteerId");
            int activityId = (Integer) data.get("activityId");
            VolunteerServiceRecord record = volunteerServiceRecordService.getServiceRecord(volunteerId, activityId);
            res.setCode(1);
            res.setResult(record);
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error fetching service record: " + e.getMessage());
        }
        return res;
    }


    @PostMapping("/getTeamTotalTime")
    public Result getTeamTotalTime(@RequestBody Map<String, String> data) {
        Result res = new Result();
        try {
            String teamId = data.get("teamId");
            Integer totalTime = volunteerServiceRecordService.getTeamTotalTime(teamId);
            res.setCode(1);
            res.setResult(totalTime);
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error fetching total volunteer time by team ID: " + e.getMessage());
        }
        return res;
    }



    /**
     * 根据团队ID获取该团队发布的所有志愿活动中已加入的志愿者ID，并按活动分组
     *
     * @param data 请求参数，包含团队ID
     * @return 包含分组后的志愿者服务记录的结果
     */
    @PostMapping("/getVolunteerActivityByTeamId")
    public Result getVolunteerActivityByTeamId(@RequestBody Map<String, String> data) {
        Result res = new Result();
        try {
            String teamId = data.get("teamId");
            Map<String, Object> result = volunteerServiceRecordService.getVolunteerActivityByTeamId(teamId);
            res.setCode(1);
            res.setResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error fetching volunteer activities by team ID: " + e.getMessage());
        }
        return res;
    }







    /**
     * 根据志愿者ID查询该志愿者近五年志愿时长分布
     *
     * @param data 包含志愿者ID的请求数据
     * @return 志愿时长的数组
     */
    @PostMapping("/getServiceHourStatistics")
    public Result getServiceHourStatistics(@RequestBody Map<String, String> data) {
        Result res = new Result();
        try {
            String volunteerId = data.get("volunteerId");
            Map<String, Object> result = volunteerServiceRecordService.getServiceHourStatistics(volunteerId);
            res.setCode(1);
            res.setResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error fetching service hour statistics: " + e.getMessage());
        }
        return res;
    }












    /**
     * 根据志愿者ID查询该志愿者的总志愿时长超过了百分之多少的志愿者
     *
     * @param data 请求数据，包括志愿者ID
     * @return 总志愿时长和百分比
     */
    @PostMapping("/getPercentOfVolunteerServiceHours")
    public Result getPercentOfVolunteerServiceHours(@RequestBody Map<String, String> data) {
        Result res = new Result();
        try {
            String volunteerId = data.get("volunteerId");
            Map<String, Object> result = volunteerServiceRecordService.getPercentOfVolunteerServiceHours(volunteerId);
            res.setCode(1);
            res.setResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error calculating percent of volunteer service hours: " + e.getMessage());
        }
        return res;
    }





    /**
     * 检查志愿者申请志愿活动情况
     *
     * @param data 包含志愿者ID和活动ID
     * @return 志愿者活动记录状态
     */
    @PostMapping("/checkVolunteerActivity")
    public Result checkVolunteerActivity(@RequestBody Map<String, Object> data) {
        Result res = new Result();
        try {
            String volunteerId = (String) data.get("volunteerId");
            int activityId = (int) data.get("activityId");
            VolunteerServiceRecord record = volunteerServiceRecordService.checkVolunteerActivity(volunteerId, activityId);

            if (record == null) {
                res.setCode(1);
                res.setResult(3); // 未申请
            } else {
                res.setCode(1);
                res.setResult(record.getState());
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error checking volunteer activity: " + e.getMessage());
        }
        return res;
    }

}
