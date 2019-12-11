package org.turings.myself.entity;

public class Myself {
	private int id;
	private String tel;
	private String name;
	private String pwd;
	private String motto;
	private String avatar;
	private int time;
	private int score;
	private int fancount;
	private int concount;
	private int achcount;
	
	public Myself() {
		super();
	}
	public Myself(int id, String tel, String name, String pwd, String motto, String avatar, int time, int score,
			int fancount, int concount, int achcount) {
		super();
		this.id = id;
		this.tel = tel;
		this.name = name;
		this.pwd = pwd;
		this.motto = motto;
		this.avatar = avatar;
		this.time = time;
		this.score = score;
		this.fancount = fancount;
		this.concount = concount;
		this.achcount = achcount;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getMotto() {
		return motto;
	}
	public void setMotto(String motto) {
		this.motto = motto;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getFancount() {
		return fancount;
	}
	public void setFancount(int fancount) {
		this.fancount = fancount;
	}
	public int getConcount() {
		return concount;
	}
	public void setConcount(int concount) {
		this.concount = concount;
	}
	public int getAchcount() {
		return achcount;
	}
	public void setAchcount(int achcount) {
		this.achcount = achcount;
	}
	
	
	
}
