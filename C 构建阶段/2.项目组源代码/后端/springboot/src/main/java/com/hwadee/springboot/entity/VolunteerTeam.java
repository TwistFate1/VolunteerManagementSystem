//package com.hwadee.springboot.entity;
//
//import java.io.Serializable;
//import java.util.List;
//
///**
// * (VolunteerTeam)实体类
// */
//public class VolunteerTeam implements Serializable {
//    private static final long serialVersionUID = 1L;
//
//    private String teamId;
//    private String password;
//    private String name;
//    private String leader;
//    private String contact;
//    private Integer memberCount; // 新添加的字段，用于表示志愿团队人数
//
//    private List<String> members;
//
//    @Override
//    public String toString() {
//        return "VolunteerTeam{" +
//                "teamId='" + teamId + '\'' +
//                ", password='" + password + '\'' +
//                ", name='" + name + '\'' +
//                ", leader='" + leader + '\'' +
//                ", contact='" + contact + '\'' +
//                ", memberCount=" + memberCount +
//                ", members=" + members +
//                '}';
//    }
//
//    public List<String> getMembers() {
//        return members;
//    }
//
//    public void setMembers(List<String> members) {
//        this.members = members;
//    }
//
//    public VolunteerTeam(String teamId, String password, String name, String leader, String contact, Integer memberCount, List<String> members) {
//        this.teamId = teamId;
//        this.password = password;
//        this.name = name;
//        this.leader = leader;
//        this.contact = contact;
//        this.memberCount = memberCount;
//        this.members = members;
//    }
//
//    public VolunteerTeam() {
//    }
//
//    public VolunteerTeam(String teamId, String password, String name, String leader, String contact, Integer memberCount) {
//        this.teamId = teamId;
//        this.password = password;
//        this.name = name;
//        this.leader = leader;
//        this.contact = contact;
//        this.memberCount = memberCount;
//
//    }
//
//    public String getTeamId() {
//        return teamId;
//    }
//
//    public void setTeamId(String teamId) {
//        this.teamId = teamId;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getLeader() {
//        return leader;
//    }
//
//    public void setLeader(String leader) {
//        this.leader = leader;
//    }
//
//    public String getContact() {
//        return contact;
//    }
//
//    public void setContact(String contact) {
//        this.contact = contact;
//    }
//
//    public Integer getMemberCount() {
//        return memberCount;
//    }
//
//    public void setMemberCount(Integer memberCount) {
//        this.memberCount = memberCount;
//    }
//}


package com.hwadee.springboot.entity;

import java.io.Serializable;
import java.util.List;

/**
 * (VolunteerTeam)实体类
 */
public class VolunteerTeam implements Serializable {
    private static final long serialVersionUID = 1L;

    private String teamId;
    private String password;
    private String name;
    private String leader;
    private String contact;
    private Integer memberCount; // 表示志愿团队人数
    private List<String> members; // 表示团队的志愿者名字
    private String introduction;
    private String url;
    private String avatar;

    private Integer max;

    public String getMemberNames() {
        return memberNames;
    }

    public void setMemberNames(String memberNames) {
        this.memberNames = memberNames;
    }

    private String memberNames; // 临时保存逗号分隔的成员名称

    @Override
    public String toString() {
        return "VolunteerTeam{" +
                "teamId='" + teamId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", leader='" + leader + '\'' +
                ", contact='" + contact + '\'' +
                ", memberCount=" + memberCount +
                ", members=" + members +
                ", introduction='" + introduction + '\'' +
                ", url='" + url + '\'' +
                ", avatar='" + avatar + '\'' +
                ", max=" + max +
                '}';
    }

    public VolunteerTeam(String teamId, String password, String name, String leader, String contact, Integer memberCount, List<String> members, String introduction, String url, String avatar, Integer max) {
        this.teamId = teamId;
        this.password = password;
        this.name = name;
        this.leader = leader;
        this.contact = contact;
        this.memberCount = memberCount;
        this.members = members;
        this.introduction = introduction;
        this.url = url;
        this.avatar = avatar;
        this.max = max;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public VolunteerTeam(String teamId, String password, String name, String leader, String contact, Integer memberCount, List<String> members, String introduction, String url, String avatar) {
        this.teamId = teamId;
        this.password = password;
        this.name = name;
        this.leader = leader;
        this.contact = contact;
        this.memberCount = memberCount;
        this.members = members;
        this.introduction = introduction;
        this.url = url;
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public VolunteerTeam() {
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public VolunteerTeam(String teamId, String password, String name, String leader, String contact, Integer memberCount, List<String> members, String introduction, String url) {
        this.teamId = teamId;
        this.password = password;
        this.name = name;
        this.leader = leader;
        this.contact = contact;
        this.memberCount = memberCount;
        this.members = members;
        this.introduction = introduction;
        this.url = url;
    }
}
