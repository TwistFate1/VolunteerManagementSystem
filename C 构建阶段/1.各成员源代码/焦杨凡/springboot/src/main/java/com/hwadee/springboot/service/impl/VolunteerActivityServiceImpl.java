package com.hwadee.springboot.service.impl;

import com.hwadee.springboot.dao.VolunteerActivityDao;
import com.hwadee.springboot.entity.VolunteerActivity;
import com.hwadee.springboot.entity.VolunteerServiceRecord;
import com.hwadee.springboot.service.VolunteerActivityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("volunteerActivityService")
public class VolunteerActivityServiceImpl implements VolunteerActivityService {
    @Resource
    private VolunteerActivityDao volunteerActivityDao;

    @Override
    public List<VolunteerActivity> getAllActivities(int page, int limit, String name,String location,String sort) {
        int offset = (page - 1) * limit;
        return volunteerActivityDao.queryAllByLimit(offset, limit, name,location,sort);
    }

    @Override
    public int count(String name,String location,String sort) {
        return volunteerActivityDao.count(name,location,sort);
    }


    @Override
    public List<VolunteerActivity> getActivityOfTeam(String teamId) {
        return volunteerActivityDao.getActivityOfTeam(teamId);
    }

    @Override
    public boolean insertActivity(VolunteerActivity volunteerActivity) {
        volunteerActivity.setState(0); // 设置默认状态为0
        volunteerActivity.setUrl(""); // 设置默认URL为空
        return volunteerActivityDao.insertActivity(volunteerActivity) > 0;
    }

    @Override
    public boolean updateActivity(VolunteerActivity volunteerActivity) {
        if (volunteerActivity.getState() != null && (volunteerActivity.getState() == 0 || volunteerActivity.getState() == 2)) {
            volunteerActivity.setState(0);
        }
        return volunteerActivityDao.updateActivity(volunteerActivity) > 0;
    }

    @Override
    public boolean deleteActivity(Long activityId) {
        return volunteerActivityDao.deleteActivity(activityId) > 0;
    }

    @Override
    public VolunteerActivity getActivityByActivityId(int activityId) {
        return volunteerActivityDao.getActivityByActivityId(activityId);
    }



    @Override
    public int getActivityNumByTeamId(String teamId) {
        return volunteerActivityDao.getActivityNumByTeamId(teamId);
    }


    /**
     * 获取所有志愿活动的数量
     *
     * @return 志愿活动数量
     */
    @Override
    public int getActivityNumber() {
        return volunteerActivityDao.getActivityNumber();
    }


    /**
     * 获取所有志愿活动的内容
     *
     * @return 所有志愿活动的列表
     */
    @Override
    public List<VolunteerActivity> getAllActivity() {
        return volunteerActivityDao.getAllActivity();
    }



    /**
     * 更新志愿活动状态
     *
     * @param activityId 志愿活动ID
     * @param state 状态
     * @return 影响的行数
     */
    @Override
    public int updateActivityState(int activityId, int state) {
        return volunteerActivityDao.updateActivityState(activityId, state);
    }






//    @Override
//    public List<Map<String, Object>> getVolunteerHoursPerDayForYear(int year, String volunteerId) {
//        return volunteerActivityDao.getVolunteerHoursPerDayForYear(year, volunteerId);
//    }

    @Override
    public List<Map<String, Object>> getVolunteerHoursPerDayForYear(int year, String volunteerId) {
        List<Map<String, Object>> dbResults = volunteerActivityDao.getVolunteerHoursPerDayForYear(year, volunteerId);

        Map<String, Integer> dateToHoursMap = new HashMap<>();
        for (Map<String, Object> entry : dbResults) {
            dateToHoursMap.put((String) entry.get("date"), ((Number) entry.get("time")).intValue());
        }

        List<Map<String, Object>> result = new ArrayList<>();
        Calendar calendar = new GregorianCalendar(year, Calendar.JANUARY, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        while (calendar.get(Calendar.YEAR) == year) {
            String date = sdf.format(calendar.getTime());
            int time = dateToHoursMap.getOrDefault(date, 0);

            Map<String, Object> dayRecord = new HashMap<>();
            dayRecord.put("date", date);
            dayRecord.put("time", time);

            result.add(dayRecord);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        return result;
    }

}
