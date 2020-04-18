package org.turings.turings.index.entity;

public class Story {
    private int id;
    private String title;
    private String smallimg;
    private String bigimg;
    private String name;
    private String starnum;
    private String content;
    public Story() {
    }
    public Story(int id, String title, String smallimg, String bigimg, String name, String starnum, String content) {
        this.id = id;
        this.title = title;
        this.smallimg = smallimg;
        this.bigimg = bigimg;
        this.name = name;
        this.starnum = starnum;
        this.content = content;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSmallimg() {
        return smallimg;
    }

    public void setSmallimg(String smallimg) {
        this.smallimg = smallimg;
    }

    public String getBigimg() {
        return bigimg;
    }

    public void setBigimg(String bigimg) {
        this.bigimg = bigimg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStarnum() {
        return starnum;
    }

    public void setStarnum(String starnum) {
        this.starnum = starnum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
