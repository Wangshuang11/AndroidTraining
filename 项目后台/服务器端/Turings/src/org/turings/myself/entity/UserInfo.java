package org.turings.myself.entity;

/**
 * use: 没有password的user实体�?
 * Attributes: id,name,motto,time
 * 
 * 
 * @author 大媛�?
 *
 */
public class UserInfo {

	private int id;
	private String name;
	private String motto;
	private int time;
	private String avatar;
	
	public UserInfo() {
		super();
	}
	
	public UserInfo(int id, String name, String motto, int time, String avatar) {
		super();
		this.id = id;
		this.name = name;
		this.motto = motto;
		this.time = time;
		this.avatar = avatar;
	}
	
	
	
	
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
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
	public String getMotto() {
		return motto;
	}
	public void setMotto(String motto) {
		this.motto = motto;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	
	
	
}
