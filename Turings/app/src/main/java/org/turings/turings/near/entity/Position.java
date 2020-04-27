package org.turings.turings.near.entity;

public class Position {
    private int id;
    private String userName;
    private String portrait;
    private double lat;
    private double lng;
    private int type;

    public Position(int id, String userName, String portrait, double lat, double lng, int type) {
        this.id = id;
        this.userName = userName;
        this.portrait = portrait;
        this.lat = lat;
        this.lng = lng;
        this.type = type;
    }

    public Position(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
