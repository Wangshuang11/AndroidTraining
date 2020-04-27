package org.turings.turings.index.gw.GrideBooks;

public class GrideBook {
    /**
     * 名字
     */
    private String name;
    /**
     * 图片id
     */
    private String imageId;

    public GrideBook(String name, String imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public String getImageId() {
        return imageId;
    }
}