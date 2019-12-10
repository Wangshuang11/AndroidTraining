package org.turings.turings.myself.entity;


public class Course{
	
	private int id;
	private String image;
	private String title;
	private String person;
	private String data;
	private int views;
	
	public Course() {
		super();
	}
	public Course(int id, String image, String title, String person, String data, int views) {
		super();
		this.id = id;
		this.image = image;
		this.title = title;
		this.person = person;
		this.data = data;
		this.views = views;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	

	
}
