package org.turings.turings.myself.entity;

public class User {
    private int uId;
    private String uTel;
    private String uName;
    private String uPwd;
    private String uMotto;
    private String uAvatar;
    private int uTime;
    private int uScore;

    public User(int uId, String uTel, String uName, String uPwd, String uMotto, String uAvatar, int uTime, int uScore) {
        this.uId = uId;
        this.uTel = uTel;
        this.uName = uName;
        this.uPwd = uPwd;
        this.uMotto = uMotto;
        this.uAvatar = uAvatar;
        this.uTime = uTime;
        this.uScore = uScore;
    }

    public User() {
        super();
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public String getuTel() {
        return uTel;
    }

    public void setuTel(String uTel) {
        this.uTel = uTel;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuPwd() {
        return uPwd;
    }

    public void setuPwd(String uPwd) {
        this.uPwd = uPwd;
    }

    public String getuMotto() {
        return uMotto;
    }

    public void setuMotto(String uMotto) {
        this.uMotto = uMotto;
    }

    public String getuAvatar() {
        return uAvatar;
    }

    public void setuAvatar(String uAvatar) {
        this.uAvatar = uAvatar;
    }

    public int getuTime() {
        return uTime;
    }

    public void setuTime(int uTime) {
        this.uTime = uTime;
    }

    public int getuScore() {
        return uScore;
    }

    public void setuScore(int uScore) {
        this.uScore = uScore;
    }
}
