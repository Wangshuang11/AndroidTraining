package org.turings.mistaken;


import java.io.Serializable;
import java.util.Date;

public class SubjectMsg implements Serializable{
    private int id;//id
    private String subject;//学科
    private String tag;//标签
    private String type;//题型（选择题，填空题，大题）
    private Date time;//存题日期
    private String titleImg;//图片名称
    private String optionA;//A选项
    private String optionB;//B选项
    private String optionC;//C选项
    private String optionD;//D选项
    private String answer;//大题或填空题答案
    private int uId;//用户id

    public SubjectMsg() {
    }

    public SubjectMsg(int id, String subject, String tag, String type, Date time, String titleImg, String optionA, String optionB, String optionC, String optionD, String answer, int uId) {
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
        return "SubjectMsg{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", tag='" + tag + '\'' +
                ", type='" + type + '\'' +
                ", time=" + time +
                ", titleImg='" + titleImg + '\'' +
                ", optionA='" + optionA + '\'' +
                ", optionB='" + optionB + '\'' +
                ", optionC='" + optionC + '\'' +
                ", optionD='" + optionD + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
