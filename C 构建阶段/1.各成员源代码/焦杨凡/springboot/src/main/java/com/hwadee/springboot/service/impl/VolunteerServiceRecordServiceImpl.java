package com.hwadee.springboot.service.impl;

import com.hwadee.springboot.dao.VolunteerServiceRecordDao;
import com.hwadee.springboot.entity.VolunteerServiceRecord;
import com.hwadee.springboot.service.VolunteerServiceRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service("volunteerServiceRecordService")
public class VolunteerServiceRecordServiceImpl implements VolunteerServiceRecordService {
    @Resource
    private VolunteerServiceRecordDao volunteerServiceRecordDao;

    @Override
    public boolean joinActivity(VolunteerServiceRecord volunteerServiceRecord) {
        return volunteerServiceRecordDao.insert(volunteerServiceRecord) > 0;
    }

    @Override
    public String handleJoinActivity(String volunteerId, int activityId) {
        VolunteerServiceRecord existingRecord = volunteerServiceRecordDao.findByVolunteerIdAndActivityId(volunteerId, activityId);
        if (existingRecord == null) {
            VolunteerServiceRecord newRecord = new VolunteerServiceRecord();
            newRecord.setVolunteerId(volunteerId);
            newRecord.setActivityId(activityId);
            newRecord.setServiceHours(0);
            newRecord.setState(0);  // 初始状态为0
            if (volunteerServiceRecordDao.insert(newRecord) > 0) {
                return "Successfully applied to join the activity";
            } else {
                return "Failed to apply to join the activity";
            }
        } else if (existingRecord.getState() == 1) {
            return "Already approved to join the activity";
        } else if (existingRecord.getState() == 2) {
            if (volunteerServiceRecordDao.updateState(volunteerId, activityId, 0) > 0) {
                return "Application resubmitted for approval";
            } else {
                return "Failed to resubmit application for approval";
            }
        } else {
            return "Already applied to join the activity";
        }
    }






    @Override
    public List<VolunteerServiceRecord> getAllRecordsByVolunteerId(String volunteerId) {
        return volunteerServiceRecordDao.queryAllRecordsByVolunteerId(volunteerId);
    }

    @Override
    public Integer getTotalServiceHours(String volunteerId) {
        return volunteerServiceRecordDao.queryTotalServiceHours(volunteerId);
    }

    @Override
    public Integer getActivityCountByState(String volunteerId, Integer state) {
        return volunteerServiceRecordDao.queryActivityCountByState(volunteerId, state);
    }



    @Override
    public int checkVolunteerActivityStatus(String volunteerId, Integer activityId) {
        return volunteerServiceRecordDao.checkVolunteerActivityStatus(volunteerId, activityId);
    }


    @Override
    public Map<String, Object> getVolunteerIdByActivityId(Long activityId) {
        List<VolunteerServiceRecord> records = volunteerServiceRecordDao.getVolunteerIdByActivityId(activityId);
        Map<String, Object> counts = (Map<String, Object>) volunteerServiceRecordDao.getVolunteerIdNumByActivityId(activityId).get(0);
        Map<String, Object> result = new HashMap<>();
        result.put("records", records);
        result.putAll(counts);
        return result;
    }


//    @Override
//    public Map<String, Object> getVolunteerIdByActivityId(int activityId) {
//        List<VolunteerServiceRecord> records = volunteerServiceRecordDao.getVolunteerIdByActivityId(activityId);
//        Map<String, Integer> counts = volunteerServiceRecordDao.getVolunteerIdNumByActivityId(activityId);
//        Map<String, Object> result = new HashMap<>();
//        result.put("records", records);
//        result.putAll(counts);
//        return result;
//    }

    @Override
    public boolean updateVolunteerServiceRecord(VolunteerServiceRecord volunteerServiceRecord) {
        return volunteerServiceRecordDao.updateVolunteerServiceRecord(volunteerServiceRecord) > 0;
    }



    @Override
    public Integer getVolunteerServiceHoursPerActivity(String volunteerId, int activityId) {
        return volunteerServiceRecordDao.getVolunteerServiceHoursPerActivity(volunteerId, activityId);
    }

    @Override
    public boolean deleteApplyForActivity(String volunteerId, int activityId) {
        return volunteerServiceRecordDao.deleteApplyForActivity(volunteerId, activityId) > 0;
    }

    @Override
    public VolunteerServiceRecord getServiceRecord(String volunteerId, int activityId) {
        return volunteerServiceRecordDao.getServiceRecord(volunteerId, activityId);
    }

    @Override
    public Integer getTeamTotalTime(String teamId) {
        return volunteerServiceRecordDao.getTeamTotalTime(teamId);
    }


//    @Override
//    public Map<String, Object> getVolunteerActivityByTeamId(String teamId) {
//        List<VolunteerServiceRecord> records = volunteerServiceRecordDao.getVolunteerActivityByTeamId(teamId);
//        Map<Integer, List<String>> activityMap = new HashMap<>();
//
//        for (VolunteerServiceRecord record : records) {
//            Integer activityId = record.getActivityId();
//            String volunteerId = record.getVolunteerId();
//
//            // Ensure we don't add null volunteer IDs
//            if (volunteerId != null) {
//                activityMap.computeIfAbsent(activityId, k -> new ArrayList<>()).add(volunteerId);
//            } else {
//                // Initialize the list for the activity ID if it doesn't exist
//                activityMap.computeIfAbsent(activityId, k -> new ArrayList<>());
//            }
//        }
//
//        List<Map<String, Object>> activities = new ArrayList<>();
//        for (Map.Entry<Integer, List<String>> entry : activityMap.entrySet()) {
//            Map<String, Object> activity = new HashMap<>();
//            activity.put("activityId", entry.getKey());
//            activity.put("volunteerId", entry.getValue());
//            activities.add(activity);
//        }
//
//        Map<String, Object> result = new HashMap<>();
//        result.put("activities", activities);
//        return result;
//    }
@Override
public Map<String, Object> getVolunteerActivityByTeamId(String teamId) {
    List<VolunteerServiceRecord> records = volunteerServiceRecordDao.getVolunteerActivityByTeamId(teamId);
    List<Map<String, Object>> resultList = new ArrayList<>();

    // Collect activity IDs to ensure we add empty lists for activities without volunteers
    Set<Integer> activityIds = new HashSet<>();
    for (VolunteerServiceRecord record : records) {
        activityIds.add(record.getActivityId());
    }

    // Add records with actual volunteer IDs
    for (VolunteerServiceRecord record : records) {
        Integer activityId = record.getActivityId();
        String volunteerId = record.getVolunteerId();

        if (volunteerId != null) {
            Map<String, Object> activity = new HashMap<>();
            activity.put("activityId", activityId);
            activity.put("volunteerId", volunteerId);
            resultList.add(activity);
        }
    }

    // Add activities with empty volunteer lists
    for (Integer activityId : activityIds) {
        boolean hasVolunteers = false;
        for (Map<String, Object> activity : resultList) {
            if (activity.get("activityId").equals(activityId)) {
                hasVolunteers = true;
                break;
            }
        }
        if (!hasVolunteers) {
            Map<String, Object> emptyActivity = new HashMap<>();
            emptyActivity.put("activityId", activityId);
            emptyActivity.put("volunteerId", new ArrayList<>());
            resultList.add(emptyActivity);
        }
    }

    Map<String, Object> result = new HashMap<>();
    result.put("result", resultList);
    return result;
}


















    /**
     * 获取志愿者近五年志愿时长分布
     *
     * @param volunteerId 志愿者ID
     * @return 志愿者近五年志愿时长分布记录
     */
    @Override
    public Map<String, Object> getServiceHourStatistics(String volunteerId) {
        List<VolunteerServiceRecord> records = volunteerServiceRecordDao.getServiceHourStatistics(volunteerId);
        Map<String, int[]> volunteerHours = new HashMap<>();

        LocalDate now = LocalDate.now();
        int currentYear = now.getYear();
        for (int year = currentYear - 4; year <= currentYear; year++) {
            volunteerHours.put(String.valueOf(year), new int[12]);
        }

        for (VolunteerServiceRecord record : records) {
            Date startDate = record.getStartDate();
            LocalDate localDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int year = localDate.getYear();
            int month = localDate.getMonthValue() - 1;
            Integer serviceHours = record.getServiceHours();

            if (volunteerHours.containsKey(String.valueOf(year))) {
                volunteerHours.get(String.valueOf(year))[month] += (serviceHours != null ? serviceHours : 0);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("volunteerId", volunteerId);
        result.put("volunteerHours", volunteerHours);
        return result;
    }











    @Override
    public Map<String, Object> getPercentOfVolunteerServiceHours(String volunteerId) {
        Integer volunteerTotalHours = volunteerServiceRecordDao.getTotalServiceHoursByVolunteerId(volunteerId);
        if (volunteerTotalHours == null) {
            volunteerTotalHours = 0;
        }
        final int totalHours = volunteerTotalHours; // 将 volunteerTotalHours 赋值给一个 final 变量

        List<Integer> allVolunteerHours = volunteerServiceRecordDao.getAllVolunteerServiceHours();
        long countLessThanVolunteer = allVolunteerHours.stream()
                .filter(hours -> hours < totalHours)
                .count();

        int totalVolunteerCount = volunteerServiceRecordDao.getTotalVolunteerCount();
        double percent = (((double) countLessThanVolunteer+1) / totalVolunteerCount) * 100;

        Map<String, Object> result = new HashMap<>();
        result.put("time", volunteerTotalHours);
        result.put("percent", percent);

        return result;
    }



    @Override
    public VolunteerServiceRecord checkVolunteerActivity(String volunteerId, int activityId) {
        return volunteerServiceRecordDao.checkVolunteerActivity(volunteerId, activityId);
    }


}
