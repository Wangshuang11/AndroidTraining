package org.turings.turings.myself.entity;

public class Achieve {
    private int id;
    private String avatar;
    private String name;
    private String detail;

    public Achieve() {
        super();
    }
    public Achieve(int id, String avatar, String name, String detail) {
        this.id = id;
        this.avatar = avatar;
        this.name = name;
        this.detail = detail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
