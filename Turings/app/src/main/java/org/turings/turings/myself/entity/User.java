package org.turings.turings.myself.entity;

public class User {
    private int id;
    private String tel;
    private String name;
    private String pwd;
    private String motto;
    private String avatar;
    private int time;
    private int score;
    private int fancount;
    private int concount;
    private int achcount;

    public User(int id, String tel, String name, String pwd, String motto, String avatar, int time, int score,int fancount,int concount,int achcount) {
        this.id=id;
        this.tel=tel;
        this.name=name;
        this.pwd=pwd;
        this.motto =motto;
        this.avatar = avatar;
        this.time = time;
        this.score = score;
        this.concount=concount;
        this.fancount=fancount;
        this.achcount=achcount;
    }

    public User() {
        super();
    }
    public int getFancount() {
        return fancount;
    }

    public void setFancount(int fancount) {
        this.fancount = fancount;
    }

    public int getConcount() {
        return concount;
    }

    public void setConcount(int concount) {
        this.concount = concount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto =motto;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public int getAchcount() {
        return achcount;
    }

    public void setAchcount(int achcount) {
        this.achcount = achcount;
    }
}
