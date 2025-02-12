package com.hwadee.springboot.service.impl;

import com.hwadee.springboot.dao.MembershipDao;
import com.hwadee.springboot.entity.Membership;
import com.hwadee.springboot.service.MembershipService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("membershipService")
public class MembershipServiceImpl implements MembershipService {
    @Resource
    private MembershipDao membershipDao;

    @Override
    public boolean joinTeam(Membership membership) {
        return membershipDao.insert(membership) > 0;
    }

    @Override
    public String handleJoinTeam(String volunteerId, String teamId) {
        Membership existingMembership = membershipDao.findByVolunteerIdAndTeamId(volunteerId, teamId);
        if (existingMembership == null) {
            Membership newMembership = new Membership();
            newMembership.setVolunteerId(volunteerId);
            newMembership.setTeamId(teamId);
            newMembership.setState(0);  // 初始状态为0
            if (membershipDao.insert(newMembership) > 0) {
                return "Successfully applied to join the team";
            } else {
                return "Failed to apply to join the team";
            }
        } else if (existingMembership.getState() == 1) {
            return "Already approved to join the team";
        } else if (existingMembership.getState() == 2) {
            if (membershipDao.updateState(volunteerId, teamId, 0) > 0) {
                return "Application resubmitted for approval";
            } else {
                return "Failed to resubmit application for approval";
            }
        } else {
            return "Already applied to join the team";
        }
    }



    @Override
    public List<Membership> getAllTeamsByVolunteerId(String volunteerId) {
        return membershipDao.queryAllTeamsByVolunteerId(volunteerId);
    }

    @Override
    public Integer getTeamCountByState(String volunteerId, Integer state) {
        return membershipDao.queryTeamCountByState(volunteerId, state);
    }


    @Override
    public int checkVolunteerTeamStatus(String volunteerId, String teamId) {
        return membershipDao.checkVolunteerTeamStatus(volunteerId, teamId);
    }


    @Override
    public Map<String, Object> getVolunteerByTeamId(String teamId) {
        List<Membership> members = membershipDao.getVolunteerByTeamId(teamId);
        Map<String, Integer> counts = membershipDao.getVolunteerNumByTeamId(teamId);
        Map<String, Object> result = new HashMap<>();
        result.put("members", members);
        result.putAll(counts);
        return result;
    }


    @Override
    public boolean updateMembership(Membership membership) {
        return membershipDao.updateMembership(membership) > 0;
    }





    @Override
    public boolean deleteApplyForTeam(String volunteerId, String teamId) {
        return membershipDao.deleteApplyForTeam(volunteerId, teamId) > 0;
    }

    @Override
    public Membership getMembership(String volunteerId, String teamId) {
        return membershipDao.getMembership(volunteerId, teamId);
    }




    @Override
    public Membership checkVolunteerTeam(String volunteerId, String teamId) {
        return membershipDao.checkVolunteerTeam(volunteerId, teamId);
    }

}
