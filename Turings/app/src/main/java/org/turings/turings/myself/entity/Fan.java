package org.turings.turings.myself.entity;

public class Fan {
    private int id;
    private String avatar;
    private String name;
    private String motto;
    private int time;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private int count;

    public Fan() {
        super();
    }
    public Fan(int id,String avatar, String name, String motto,int count,int time) {
        this.id=id;
        this.avatar = avatar;
        this.name = name;
        this.motto = motto;
        this.count=count;
        this.time=time;
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

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
