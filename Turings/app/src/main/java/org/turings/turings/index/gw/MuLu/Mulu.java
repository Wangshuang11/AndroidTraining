package org.turings.turings.index.gw.MuLu;

public class Mulu {
    private int photoId;
    private String name;

    public Mulu(){

    }

    public Mulu(int photoId, String name) {
        this.photoId = photoId;
        this.name = name;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
