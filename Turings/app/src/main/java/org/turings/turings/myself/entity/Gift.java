package org.turings.turings.myself.entity;

public class Gift {
    private int id;
    private String gitfName;
    private String giftAddr;
    private String giftPhone;
    private int giftId;

    public Gift(int id, String gitfName, String giftAddr, int giftId) {
        this.id = id;
        this.gitfName = gitfName;
        this.giftAddr= giftAddr;
        this.giftId = giftId;
    }

    public Gift() {
        super();
    }

    @Override
    public String toString() {
        return "Gift{" +
                "id=" + id +
                ", gitfName='" + gitfName + '\'' +
                ", giftNum=" + giftAddr +
                ", giftId=" + giftId +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGitfName() {
        return gitfName;
    }

    public void setGitfName(String gitfName) {
        this.gitfName = gitfName;
    }

    public String getGiftAddr() {
        return giftAddr;
    }

    public void setGiftAddr(String giftAddr) {
        this.giftAddr = giftAddr;
    }

    public int getGiftId() {
        return giftId;
    }

    public void setGiftId(int giftId) {
        this.giftId = giftId;
    }
}
