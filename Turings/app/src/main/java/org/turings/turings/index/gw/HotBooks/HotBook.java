package org.turings.turings.index.gw.HotBooks;

public class HotBook {
    private String name;
    private int imageId;

    public HotBook(String name, int imageId){
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

