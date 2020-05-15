package org.turings.turings.index.entity;


public class Comment2 {
    private int id;
    private String icon;
    private String name;
    private String djIcon;
    private String djName;
    private String content;
    private String num;
    private String time;

    public Comment2() {
    }

    public Comment2(int id, String icon, String name, String djIcon, String djName, String content, String num, String time) {
        this.id = id;
        this.icon = icon;
        this.name = name;
        this.djIcon = djIcon;
        this.djName = djName;
        this.content = content;
        this.num = num;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDjIcon() {
        return djIcon;
    }

    public void setDjIcon(String djIcon) {
        this.djIcon = djIcon;
    }

    public String getDjName() {
        return djName;
    }

    public void setDjName(String djName) {
        this.djName = djName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
