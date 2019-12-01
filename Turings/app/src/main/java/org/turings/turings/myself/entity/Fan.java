package org.turings.turings.myself.entity;

public class Fan {
    private int id;
    private String img;
    private String name;
    private String motto;

    public Fan() {
        super();
    }
    public Fan(int id,String img, String name, String motto) {
        this.id=id;
        this.img = img;
        this.name = name;
        this.motto = motto;
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
}
