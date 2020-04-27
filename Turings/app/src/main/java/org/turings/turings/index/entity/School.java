package org.turings.turings.index.entity;

public class School {
    private String name;
    private String img;
    private String url;
    private String src;
    public School() {
    }

    public School(String name, String img, String url, String src) {
        this.name = name;
        this.img = img;
        this.url = url;
        this.src = src;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
