package org.turings.turings.near.entity;

public class Information {
    private int id;
    private String userName;
    private String portrait;
    private double totalTime;
    private double currentTime;
    private String university;
    private String motto;

    public Information() {

    }

    public Information(int id, String userName,String portrait, double totalTime, double currentTime, String university, String motto) {
        super();
        this.id = id;
        this.userName = userName;
        this.portrait = portrait;
        this.totalTime = totalTime;
        this.currentTime = currentTime;
        this.university = university;
        this.motto = motto;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public double getTotalTime() {
        return totalTime;
    }
    public void setTotalTime(double totalTime) {
        this.totalTime = totalTime;
    }
    public double getCurrentTime() {
        return currentTime;
    }
    public void setCurrentTime(double currentTime) {
        this.currentTime = currentTime;
    }
    public String getUniversity() {
        return university;
    }
    public void setUniversity(String university) {
        this.university = university;
    }
    public String getMotto() {
        return motto;
    }
    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

}
