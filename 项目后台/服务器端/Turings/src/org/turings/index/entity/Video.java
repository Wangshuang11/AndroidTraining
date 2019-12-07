package org.turings.index.entity;



public class Video {
	private String id;
	private String videoUrl;
	private String course;
	private String branch;
	private int sharedCounts;
	private int storedCounts;
	private int viewCounts;
	public int getViewCounts() {
		return viewCounts;
	}
	public void setViewCounts(int viewCounts) {
		this.viewCounts = viewCounts;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public int getSharedCounts() {
		return sharedCounts;
	}
	public void setSharedCounts(int sharedCounts) {
		this.sharedCounts = sharedCounts;
	}
	public int getStoredCounts() {
		return storedCounts;
	}
	public void setStoredCounts(int storedCounts) {
		this.storedCounts = storedCounts;
	}
	

}
