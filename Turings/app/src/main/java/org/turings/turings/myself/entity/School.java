package org.turings.turings.myself.entity;

public class School {
    private int id;
    private String img;
    private String name;
    private String flag;
    private String url;
    private String src;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public School() {
        super();
    }
    public School(int id, String name, String flag, String img, String url,String src) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.flag = flag;
        this.url=url;
        this.src=src;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
