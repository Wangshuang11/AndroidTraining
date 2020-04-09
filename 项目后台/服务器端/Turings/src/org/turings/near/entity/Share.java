package org.turings.near.entity;

public class Share {
	private int id;
	private String userName;
	private String shareTitle;
	private String shareContent;
	private String background;
	
	public Share() {
		
	}
	public Share(int id, String userName,String shareTitle, String shareContent, String background) {
		super();
		this.id = id;
		this.userName = userName;
		this.shareTitle = shareTitle;
		this.shareContent = shareContent;
		this.background = background;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getShareTitle() {
		return shareTitle;
	}
	public void setShareTitle(String shareTitle) {
		this.shareTitle = shareTitle;
	}
	public String getShareContent() {
		return shareContent;
	}
	public void setShareContent(String shareContent) {
		this.shareContent = shareContent;
	}
	public String getBackground() {
		return background;
	}
	public void setBackground(String background) {
		this.background = background;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
