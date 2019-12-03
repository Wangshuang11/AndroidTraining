package org.turings.mistaken.entity;

import java.util.Date;

public class SubjectMsg {

	private int id;//id
    private String subject;//ѧ��
    private String tag;//��ǩ
    private String type;//���ͣ�ѡ���⣬����⣬���⣩
    private Date time;//��������
    private String titleImg;//ͼƬ����
    private String optionA;//Aѡ��
    private String optionB;//Bѡ��
    private String optionC;//Cѡ��
    private String optionD;//Dѡ��
    private String answer;//�����������
    private int uId;//�û�id
	public SubjectMsg() {
		super();
	}
	public SubjectMsg(int id, String subject, String tag, String type, Date time, String titleImg, String optionA,
			String optionB, String optionC, String optionD, String answer, int uId) {
		super();
		this.id = id;
		this.subject = subject;
		this.tag = tag;
		this.type = type;
		this.time = time;
		this.titleImg = titleImg;
		this.optionA = optionA;
		this.optionB = optionB;
		this.optionC = optionC;
		this.optionD = optionD;
		this.answer = answer;
		this.uId = uId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getTitleImg() {
		return titleImg;
	}
	public void setTitleImg(String titleImg) {
		this.titleImg = titleImg;
	}
	public String getOptionA() {
		return optionA;
	}
	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}
	public String getOptionB() {
		return optionB;
	}
	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}
	public String getOptionC() {
		return optionC;
	}
	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}
	public String getOptionD() {
		return optionD;
	}
	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public int getuId() {
		return uId;
	}
	public void setuId(int uId) {
		this.uId = uId;
	}
	@Override
	public String toString() {
		return "SubjectMsg [id=" + id + ", subject=" + subject + ", tag=" + tag + ", type=" + type + ", time=" + time
				+ ", titleImg=" + titleImg + ", optionA=" + optionA + ", optionB=" + optionB + ", optionC=" + optionC
				+ ", optionD=" + optionD + ", answer=" + answer + ", uId=" + uId + "]";
	}
    
}
