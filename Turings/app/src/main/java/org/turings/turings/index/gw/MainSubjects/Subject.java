package org.turings.turings.index.gw.MainSubjects;

public class Subject {
    private String name;
    private int imageId;

    public Subject(String name, int imageId){
        this.name = name;
        this.imageId = imageId;

    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}