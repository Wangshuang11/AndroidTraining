package org.turings.turings.index.entity;

public class Comment2 {
    private String img;
    private String name;
    private String content;
    private String datetv;

    public Comment2() {
    }

    public Comment2(String img, String name, String content, String datetv) {
        this.img = img;
        this.name = name;
        this.content = content;
        this.datetv = datetv;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDatetv() {
        return datetv;
    }

    public void setDatetv(String datetv) {
        this.datetv = datetv;
    }
}
