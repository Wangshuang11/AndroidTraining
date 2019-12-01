package org.turings.login.entity;

public class User {
	private int uId;
	private String uTel;//11位
	private String uName;
	private String uPwd;
	private String uMotto;
	private String uAvatar;
	private int uTime;//时长
	private int uScore;//积分
	

	public User() {
		
	}

	@Override
	public String toString() {
		return "user [uId=" + uId + ", uTel=" + uTel + ", uName=" + uName + ", uPwd=" + uPwd + ", uMotto=" + uMotto
				+ ", uAvatar=" + uAvatar + ", uTime=" + uTime + ", uScore=" + uScore + "]";
	}

	public int getuId() {
		return uId;
	}


	public void setuId(int uId) {
		this.uId = uId;
	}


	public String getuTel() {
		return uTel;
	}


	public void setuTel(String uTel) {
		this.uTel = uTel;
	}


	public String getuName() {
		return uName;
	}


	public void setuName(String uName) {
		this.uName = uName;
	}


	public String getuPwd() {
		return uPwd;
	}


	public void setuPwd(String uPwd) {
		this.uPwd = uPwd;
	}


	public String getuMotto() {
		return uMotto;
	}


	public void setuMotto(String uMotto) {
		this.uMotto = uMotto;
	}


	public String getuAvatar() {
		return uAvatar;
	}


	public void setuAvatar(String uAvatar) {
		this.uAvatar = uAvatar;
	}


	public int getuTime() {
		return uTime;
	}


	public void setuTime(int uTime) {
		this.uTime = uTime;
	}


	public int getuScore() {
		return uScore;
	}


	public void setuScore(int uScore) {
		this.uScore = uScore;
	}

}
