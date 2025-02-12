package com.hwadee.springboot.controller;

import com.hwadee.springboot.entity.Membership;
import com.hwadee.springboot.entity.Result;
import com.hwadee.springboot.service.MembershipService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("membership")
public class MembershipController {
    @Resource
    private MembershipService membershipService;

    @PostMapping("/joinTeam")
    public Result joinTeam(@RequestBody Map<String, String> request) {
        Result res = new Result();
        try {
            String volunteerId = request.get("volunteerId");
            String teamId = request.get("teamId");

            String resultMessage = membershipService.handleJoinTeam(volunteerId, teamId);
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
            res.setResult("Error joining the team: " + e.getMessage());
        }
        return res;
    }



    @PostMapping("/getAllTeamByVolunteerId")
    public Result getAllTeamByVolunteerId(@RequestBody Map<String, String> request) {
        Result res = new Result();
        try {
            String volunteerId = request.get("volunteerId");
            List<Membership> teams = membershipService.getAllTeamsByVolunteerId(volunteerId);
            Integer num1 = membershipService.getTeamCountByState(volunteerId, 1);
            Integer num0 = membershipService.getTeamCountByState(volunteerId, 0);
            Integer num2 = membershipService.getTeamCountByState(volunteerId, 2);

            res.setCode(1);
            res.setResult(new Object() {
                public List<Membership> getTeams() {
                    return teams;
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
            res.setResult("Error retrieving teams: " + e.getMessage());
        }
        return res;
    }


    @PostMapping("/checkVolunteerTeamStatus")
    public Result checkVolunteerTeamStatus(@RequestBody Map<String, String> request) {
        Result res = new Result();
        try {
            String volunteerId = request.get("volunteerId");
            String teamId = request.get("teamId");
            int count = membershipService.checkVolunteerTeamStatus(volunteerId, teamId);
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
            res.setResult("Error checking volunteer team status: " + e.getMessage());
        }
        return res;
    }


    @PostMapping("/getVolunteerByTeamId")
    public Result getVolunteerByTeamId(@RequestBody Map<String, String> data) {
        Result res = new Result();
        try {
            String teamId = data.get("teamId");
            Map<String, Object> result = membershipService.getVolunteerByTeamId(teamId);
            res.setCode(1);
            res.setResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error fetching volunteers by team ID: " + e.getMessage());
        }
        return res;
    }


    @PostMapping("/updateMembership")
    public Result updateMembership(@RequestBody Membership membership) {
        Result res = new Result();
        try {
            boolean success = membershipService.updateMembership(membership);
            if (success) {
                res.setCode(1);
                res.setResult("Membership updated successfully");
            } else {
                res.setCode(0);
                res.setResult("Failed to update membership");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error updating membership: " + e.getMessage());
        }
        return res;
    }







    @PostMapping("/deleteApplyForTeam")
    public Result deleteApplyForTeam(@RequestBody Map<String, String> data) {
        Result res = new Result();
        try {
            String volunteerId = data.get("volunteerId");
            String teamId = data.get("teamId");
            boolean success = membershipService.deleteApplyForTeam(volunteerId, teamId);
            if (success) {
                res.setCode(1);
                res.setResult("Successfully deleted the application for joining the team");
            } else {
                res.setCode(0);
                res.setResult("Failed to delete the application for joining the team");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error deleting the application for joining the team: " + e.getMessage());
        }
        return res;
    }

    @PostMapping("/getMembership")
    public Result getMembership(@RequestBody Map<String, String> data) {
        Result res = new Result();
        try {
            String volunteerId = data.get("volunteerId");
            String teamId = data.get("teamId");
            Membership membership = membershipService.getMembership(volunteerId, teamId);
            if (membership != null) {
                res.setCode(1);
                res.setResult(membership.getState());
            } else {
                res.setCode(0);
                res.setResult("No membership found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
            res.setResult("Error fetching membership: " + e.getMessage());
        }
        return res;
    }




    /**
     * 检查志愿者申请志愿团队情况
     *
     * @param data 包含志愿者ID和团队ID
     * @return 志愿者团队记录状态
     */
    @PostMapping("/checkVolunteerTeam")
    public Result checkVolunteerTeam(@RequestBody Map<String, Object> data) {
        Result res = new Result();
        try {
            String volunteerId = (String) data.get("volunteerId");
            String teamId = (String) data.get("teamId");
            Membership record = membershipService.checkVolunteerTeam(volunteerId, teamId);

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
            res.setResult("Error checking volunteer team: " + e.getMessage());
        }
        return res;
    }
}
