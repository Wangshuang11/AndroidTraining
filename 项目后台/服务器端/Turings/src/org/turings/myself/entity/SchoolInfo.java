package org.turings.myself.entity;

public class SchoolInfo {
	private int id;
	private String name;
	private String flag;
	private String img;
	private String url;
	private String src;
	
	public SchoolInfo() {
		super();
	}
	public SchoolInfo(int id, String name, String flag, String img, String url, String src) {
		super();
		this.id = id;
		this.name = name;
		this.flag = flag;
		this.img = img;
		this.url = url;
		this.src = src;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	
	
}
