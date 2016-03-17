package se.rymdo.studentfiket;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentfiketShift extends Object {
    private int sID;
    private Date startTime;
    private Date endTime;
    private int color;
    private List<StudentfiketUser> users;
    private StudentfiketOrg organization;
    private boolean close;

    public StudentfiketShift() {
        this.sID = 0;
        this.startTime = null;
        this.endTime = null;
        this.color = 0;
        this.users = new ArrayList();
        this.organization = null;
        this.close = false;
    }

    public int getsID() {
        return sID;
    }

    public void setsID(int sID) {
        this.sID = sID;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public List<StudentfiketUser> getUsers() {
        return users;
    }

    public void setUsers(List<StudentfiketUser> users) {
        this.users = users;
    }

    public void addUsers(StudentfiketUser user) {
        this.users.add(user);
    }

    public StudentfiketOrg getOrganization() {
        return organization;
    }

    public void setOrganization(StudentfiketOrg organization) {
        this.organization = organization;
    }

    public boolean isClose() {
        return close;
    }

    public void setClose(boolean close) {
        this.close = close;
    }
}
